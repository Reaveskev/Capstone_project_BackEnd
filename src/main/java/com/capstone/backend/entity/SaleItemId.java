package com.capstone.backend.entity;

import java.io.Serializable;
import java.util.Objects;

public class SaleItemId implements Serializable {
    private Integer saleId;
    private Integer lineNo;

    public SaleItemId() {}

    public SaleItemId(Integer saleId, Integer lineNo) {
        this.saleId = saleId;
        this.lineNo = lineNo;
    }

    public Integer getSaleId() { return saleId; }
    public void setSaleId(Integer saleId) { this.saleId = saleId; }
    public Integer getLineNo() { return lineNo; }
    public void setLineNo(Integer lineNo) { this.lineNo = lineNo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SaleItemId)) return false;
        SaleItemId that = (SaleItemId) o;
        return Objects.equals(saleId, that.saleId) && Objects.equals(lineNo, that.lineNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(saleId, lineNo);
    }
}