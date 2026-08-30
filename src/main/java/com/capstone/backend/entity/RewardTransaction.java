package com.capstone.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rewardtransactions")
public class RewardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rewardtxnid")
    private Integer rewardTxnId;

    @Column(name = "customerid", nullable = false)
    private Integer customerId;

    @Column(name = "saleid")
    private Integer saleId;

    @Column(name = "pointschange", nullable = false)
    private Integer pointsChange;

    @Column(name = "transactiondate", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "type", nullable = false)
    private String type;

    public RewardTransaction() {}

    public Integer getRewardTxnId() { return rewardTxnId; }
    public void setRewardTxnId(Integer rewardTxnId) { this.rewardTxnId = rewardTxnId; }
    
    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }
    
    public Integer getSaleId() { return saleId; }
    public void setSaleId(Integer saleId) { this.saleId = saleId; }
    
    public Integer getPointsChange() { return pointsChange; }
    public void setPointsChange(Integer pointsChange) { this.pointsChange = pointsChange; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
