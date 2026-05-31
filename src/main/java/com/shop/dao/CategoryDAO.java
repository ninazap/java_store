package com.shop.dao;
import com.shop.beans.Category;
import com.shop.utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public List<Category> findAll() {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Statement st = DBConnection.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getString("description")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}