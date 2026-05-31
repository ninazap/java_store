package com.shop.controllers;

import com.shop.beans.CartItem;
import com.shop.beans.User;
import com.shop.dao.CartDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final CartDAO cartDAO = new CartDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        if ("remove".equals(req.getParameter("action")) && req.getParameter("productId") != null) {
            cartDAO.removeFromCart(user.getId(), Integer.parseInt(req.getParameter("productId")));
        }

        List<CartItem> items = cartDAO.getCartItems(user.getId());
        req.setAttribute("cartItems", items);
        req.getRequestDispatcher("/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        if ("add".equals(req.getParameter("action")) && req.getParameter("productId") != null) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int quantity = (req.getParameter("quantity") != null) ? Integer.parseInt(req.getParameter("quantity")) : 1;
            cartDAO.addToCart(user.getId(), productId, quantity);
        }
        resp.sendRedirect(req.getContextPath() + "/cart");
    }
}