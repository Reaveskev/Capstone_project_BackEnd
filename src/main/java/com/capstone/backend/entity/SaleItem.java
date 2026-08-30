package com.capstone.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "saleitems")
@IdClass(SaleItemId.class)
public class SaleItem {

    @Id
    @Column(name = "saleid")
    private Integer saleId;

    @Id
    @Column(name = "lineno")
    private Integer lineNo;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unitprice", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "sku", nullable = false)
    private String sku;

    public SaleItem() {}

    public Integer getSaleId() { return saleId; }
    public void setSaleId(Integer saleId) { this.saleId = saleId; }
    
    public Integer getLineNo() { return lineNo; }
    public void setLineNo(Integer lineNo) { this.lineNo = lineNo; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}
