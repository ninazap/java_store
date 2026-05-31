package com.shop.controllers;

import com.shop.beans.Product;
import com.shop.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr != null) {
            Product product = productDAO.findById(Integer.parseInt(idStr));
            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("/product.jsp").forward(req, resp);
                return;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/catalog");
    }
}