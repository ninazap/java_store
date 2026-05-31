package com.shop.dao;
import com.shop.beans.*;
import com.shop.utils.DBConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    public boolean createOrder(int userId, List<CartItem> cartItems) {
        String insertOrder = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'PENDING')";
        String insertItem = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        try {
            conn.setAutoCommit(false);
            BigDecimal total = cartItems.stream()
                    .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            try (PreparedStatement psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                psOrder.setInt(1, userId); psOrder.setBigDecimal(2, total);
                psOrder.executeUpdate();
                ResultSet keys = psOrder.getGeneratedKeys();
                if (keys.next()) {
                    int orderId = keys.getInt(1);
                    try (PreparedStatement psItem = conn.prepareStatement(insertItem)) {
                        for (CartItem item : cartItems) {
                            psItem.setInt(1, orderId);
                            psItem.setInt(2, item.getProductId());
                            psItem.setInt(3, item.getQuantity());
                            psItem.setBigDecimal(4, item.getProduct().getPrice());
                            psItem.addBatch();
                        }
                        psItem.executeBatch();
                    }
                }
            }
            conn.commit();
            new CartDAO().clearCart(userId);
            return true;
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Order> getUserOrders(int userId) {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(rs.getInt("id"), rs.getInt("user_id"),
                        new java.util.Date(rs.getTimestamp("created_at").getTime()),
                        rs.getBigDecimal("total_amount"), rs.getString("status")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT oi.*, p.name, p.image_url FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE oi.order_id = ?";
        try (PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem item = new OrderItem(rs.getInt("id"), rs.getInt("order_id"), rs.getInt("product_id"), rs.getInt("quantity"), rs.getBigDecimal("price"));
                Product p = new Product(); p.setName(rs.getString("name")); p.setImageUrl(rs.getString("image_url"));
                item.setProduct(p);
                list.add(item);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
}