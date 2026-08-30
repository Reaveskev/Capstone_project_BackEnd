package com.capstone.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class SaleResponse {
	
    private Integer saleId;
    private Integer customerId;
    private String customerName;
    private LocalDateTime saleDate;
    private BigDecimal totalAmount;
    private List<SaleItemResponse> items;

    public SaleResponse(Integer saleId, Integer customerId, String customerName,
                         LocalDateTime saleDate, BigDecimal totalAmount, List<SaleItemResponse> items) {
        this.saleId = saleId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Integer getSaleId() { return saleId; }
    public Integer getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public LocalDateTime getSaleDate() { return saleDate; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public List<SaleItemResponse> getItems() { return items; }
}
