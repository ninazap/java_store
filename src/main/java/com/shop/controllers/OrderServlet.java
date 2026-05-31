package com.shop.controllers;

import com.shop.beans.CartItem;
import com.shop.beans.Order;
import com.shop.beans.User;
import com.shop.dao.CartDAO;
import com.shop.dao.OrderDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet({"/order", "/orders"})
public class OrderServlet extends HttpServlet {
    private final OrderDAO orderDAO = new OrderDAO();
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        if ("/orders".equals(req.getServletPath())) {
            List<Order> orders = orderDAO.getUserOrders(user.getId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/orders.jsp").forward(req, resp);
        } else {
            List<CartItem> cart = cartDAO.getCartItems(user.getId());
            if (cart.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart");
                return;
            }
            boolean success = orderDAO.createOrder(user.getId(), cart);
            resp.sendRedirect(success ? req.getContextPath() + "/orders" : req.getContextPath() + "/cart?error=create_failed");
        }
    }
}