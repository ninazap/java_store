package com.shop.dao;

import com.shop.beans.CartItem;
import com.shop.beans.Product;
import com.shop.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {
    public boolean addToCart(int userId, int productId, int quantity) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> list = new ArrayList<>();
        String sql = "SELECT c.id, c.user_id, c.product_id, c.quantity, p.name, p.price, p.image_url " +
                "FROM cart c JOIN products p ON c.product_id = p.id WHERE c.user_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CartItem item = new CartItem(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("product_id"),
                        rs.getInt("quantity")
                );
                // Временный объект Product только для отображения в JSP
                Product p = new Product();
                p.setName(rs.getString("name"));
                p.setPrice(rs.getBigDecimal("price"));
                p.setImageUrl(rs.getString("image_url"));
                item.setProduct(p);
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean removeFromCart(int userId, int productId) {
        String sql = "DELETE FROM cart WHERE user_id = ? AND product_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void clearCart(int userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}