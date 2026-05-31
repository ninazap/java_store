package com.shop.dao;

import com.shop.beans.User;
import com.shop.utils.DBConnection;
import java.sql.*;

public class UserDAO {
    public boolean register(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { return false; }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }
}
