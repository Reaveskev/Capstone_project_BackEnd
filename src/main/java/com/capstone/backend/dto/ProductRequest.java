package com.capstone.backend.dto;

import java.math.BigDecimal;

public class ProductRequest {
	
    private String sku;
    private String productName;
    private BigDecimal price;
    private Integer stockLevel;
    private String category;
    private Integer lowStockThreshold;

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    
    public Integer getStockLevel() { return stockLevel; }
    public void setStockLevel(Integer stockLevel) { this.stockLevel = stockLevel; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public Integer getLowStockThreshold() { return lowStockThreshold; }
    public void setLowStockThreshold(Integer lowStockThreshold) { this.lowStockThreshold = lowStockThreshold; }
}