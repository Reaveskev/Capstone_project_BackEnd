package com.capstone.backend.dto;

import java.math.BigDecimal;

public class SaleItemResponse {
	
    private String sku;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal lineTotal;

    public SaleItemResponse(String sku, String productName, Integer quantity, BigDecimal unitPrice, BigDecimal lineTotal) {
        this.sku = sku;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.lineTotal = lineTotal;
    }

    public String getSku() { return sku; }
    public String getProductName() { return productName; }
    public Integer getQuantity() { return quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getLineTotal() { return lineTotal; }
}
