package com.capstone.backend.controller;


import com.capstone.backend.dto.ProductRequest;
import com.capstone.backend.entity.Product;
import com.capstone.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<?> getProductBySku(@PathVariable String sku) {
        return productRepository.findById(sku)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            return ResponseEntity.badRequest().body("A product with this SKU already exists");
        }

        Product product = new Product();
        product.setSku(request.getSku());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStockLevel(request.getStockLevel() != null ? request.getStockLevel() : 0);
        product.setCategory(request.getCategory());
        product.setLowStockThreshold(
                request.getLowStockThreshold() != null ? request.getLowStockThreshold() : 20
        );

        Product saved = productRepository.save(product);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{sku}")
    public ResponseEntity<?> updateProduct(@PathVariable String sku, @RequestBody ProductRequest request) {
        return productRepository.findById(sku)
                .<ResponseEntity<?>>map(product -> {
                    product.setProductName(request.getProductName());
                    product.setPrice(request.getPrice());
                    if (request.getStockLevel() != null) {
                        product.setStockLevel(request.getStockLevel());
                    }
                    product.setCategory(request.getCategory());
                    if (request.getLowStockThreshold() != null) {
                        product.setLowStockThreshold(request.getLowStockThreshold());
                    }
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<?> deleteProduct(@PathVariable String sku) {
        if (!productRepository.existsById(sku)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(sku);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
