package com.capstone.backend.controller;

import com.capstone.backend.dto.*;
import com.capstone.backend.entity.*;
import com.capstone.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    @Autowired private SaleRepository saleRepository;
    @Autowired private SaleItemRepository saleItemRepository;
    @Autowired private CustomerRepository customerRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private RewardTransactionRepository rewardTransactionRepository;

    private static final BigDecimal POINTS_VALUE = new BigDecimal("0.01"); // $0.01 per point

    @GetMapping
    public List<SaleResponse> getAllSales() {
        List<Sale> sales = saleRepository.findAll();
        List<SaleResponse> responses = new ArrayList<>();
        for (Sale sale : sales) {
            responses.add(buildSaleResponse(sale));
        }
        return responses;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Integer id) {
        return saleRepository.findById(id)
                .<ResponseEntity<?>>map(sale -> ResponseEntity.ok(buildSaleResponse(sale)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> createSale(@RequestBody CreateSaleRequest request, Authentication authentication) {
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body("A sale must have at least one item");
        }

        String username = authentication.getName();
        User currentUser = userRepository.findByUsername(username).orElse(null);
        if (currentUser == null) {
            return ResponseEntity.status(401).body("Authenticated user not found");
        }

        // Resolve customer (needed for reward point validation/deduction)
        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
            if (customer == null) {
                return ResponseEntity.badRequest().body("Unknown customer");
            }
        }

        // Validate stock and compute subtotal
        BigDecimal subtotal = BigDecimal.ZERO;
        List<Product> productsToUpdate = new ArrayList<>();

        for (SaleItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getSku()).orElse(null);
            if (product == null) {
                return ResponseEntity.badRequest().body("Unknown SKU: " + item.getSku());
            }
            if (product.getStockLevel() < item.getQuantity()) {
                return ResponseEntity.badRequest().body("Insufficient stock for " + item.getSku());
            }
            subtotal = subtotal.add(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            product.setStockLevel(product.getStockLevel() - item.getQuantity());
            productsToUpdate.add(product);
        }

        // Validate and apply reward point redemption
        int pointsRedeemed = request.getPointsRedeemed() != null ? request.getPointsRedeemed() : 0;
        BigDecimal discount = BigDecimal.ZERO;

        if (pointsRedeemed > 0) {
            if (customer == null) {
                return ResponseEntity.badRequest().body("Cannot redeem points without a customer");
            }
            if (pointsRedeemed > customer.getRewardPointsBalance()) {
                return ResponseEntity.badRequest().body("Insufficient reward points balance");
            }
            discount = POINTS_VALUE.multiply(BigDecimal.valueOf(pointsRedeemed));
            if (discount.compareTo(subtotal) > 0) {
                return ResponseEntity.badRequest().body("Redemption exceeds sale subtotal");
            }
        }

        BigDecimal total = subtotal.subtract(discount);

        Sale sale = new Sale();
        sale.setSaleDate(LocalDateTime.now());
        sale.setTotalAmount(total);
        sale.setCustomerId(request.getCustomerId());
        sale.setUserId(currentUser.getUserId());
        Sale savedSale = saleRepository.save(sale);

        int lineNo = 1;
        for (SaleItemRequest item : request.getItems()) {
            SaleItem saleItem = new SaleItem();
            saleItem.setSaleId(savedSale.getSaleId());
            saleItem.setLineNo(lineNo++);
            saleItem.setSku(item.getSku());
            saleItem.setQuantity(item.getQuantity());
            saleItem.setUnitPrice(item.getUnitPrice());
            saleItemRepository.save(saleItem);
        }

        productRepository.saveAll(productsToUpdate);

        // Deduct points and log the reward transaction
        if (pointsRedeemed > 0 && customer != null) {
            customer.setRewardPointsBalance(customer.getRewardPointsBalance() - pointsRedeemed);
            customerRepository.save(customer);

            RewardTransaction rewardTxn = new RewardTransaction();
            rewardTxn.setCustomerId(customer.getCustomerId());
            rewardTxn.setSaleId(savedSale.getSaleId());
            rewardTxn.setPointsChange(-pointsRedeemed);
            rewardTxn.setTransactionDate(LocalDateTime.now());
            rewardTxn.setType("REDEEM");
            rewardTransactionRepository.save(rewardTxn);
        }

        return ResponseEntity.ok(buildSaleResponse(savedSale));
    }

    private SaleResponse buildSaleResponse(Sale sale) {
        String customerName = "Unknown";
        if (sale.getCustomerId() != null) {
            Customer customer = customerRepository.findById(sale.getCustomerId()).orElse(null);
            if (customer != null) customerName = customer.getName();
        }

        List<SaleItem> items = saleItemRepository.findBySaleId(sale.getSaleId());
        List<SaleItemResponse> itemResponses = new ArrayList<>();

        for (SaleItem item : items) {
            Product product = productRepository.findById(item.getSku()).orElse(null);
            String productName = product != null ? product.getProductName() : item.getSku();
            BigDecimal lineTotal = item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            itemResponses.add(new SaleItemResponse(
                    item.getSku(), productName, item.getQuantity(), item.getUnitPrice(), lineTotal
            ));
        }

        return new SaleResponse(
                sale.getSaleId(), sale.getCustomerId(), customerName,
                sale.getSaleDate(), sale.getTotalAmount(), itemResponses
        );
    }
}