package com.shop.beans;
import java.math.BigDecimal;
public class Product {
    private int id, categoryId; private String name, description, imageUrl; private BigDecimal price;
    public Product() {}
    public Product(int id, String name, String description, BigDecimal price, int categoryId, String imageUrl) {
        this.id = id; this.name = name; this.description = description; this.price = price; this.categoryId = categoryId; this.imageUrl = imageUrl;
    }
    public int getId() { return id; } public void setId(int id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; } public void setPrice(BigDecimal price) { this.price = price; }
    public int getCategoryId() { return categoryId; } public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getImageUrl() { return imageUrl; } public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}