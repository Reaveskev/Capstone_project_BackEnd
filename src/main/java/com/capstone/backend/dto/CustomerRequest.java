package com.capstone.backend.dto;


public class CustomerRequest {
	
    private String name;
    private String email;
    private String phone;
    private Integer rewardPointsBalance;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public Integer getRewardPointsBalance() { return rewardPointsBalance; }
    public void setRewardPointsBalance(Integer rewardPointsBalance) { this.rewardPointsBalance = rewardPointsBalance; }
}