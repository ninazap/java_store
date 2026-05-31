package com.shop.dao;
import com.shop.beans.Product;
import com.shop.utils.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    public List<Product> findAll() { return getByCategory(null); }

    public List<Product> getByCategory(Integer categoryId) {
        List<Product> list = new ArrayList<>();
        String sql = categoryId == null ? "SELECT * FROM products" : "SELECT * FROM products WHERE category_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            if (categoryId != null) ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getBigDecimal("price"), rs.getInt("category_id"), rs.getString("image_url")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Product findById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getBigDecimal("price"), rs.getInt("category_id"), rs.getString("image_url"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}