package com.shop.beans;
import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private int id, userId;
    private Date createdAt;
    private BigDecimal totalAmount;
    private String status;

    public Order() {}
    public Order(int id, int userId, Date createdAt, BigDecimal totalAmount, String status) {
        this.id = id; this.userId = userId; this.createdAt = createdAt; this.totalAmount = totalAmount; this.status = status;
    }
    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; } public void setUserId(int userId) { this.userId = userId; }
    public Date getCreatedAt() { return createdAt; } public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public BigDecimal getTotalAmount() { return totalAmount; } public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; } public void setStatus(String status) { this.status = status; }
}