package com.shop.controllers;

import com.shop.dao.CategoryDAO;
import com.shop.dao.ProductDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List categories = categoryDAO.findAll();
        String catIdStr = req.getParameter("categoryId");
        List products = (catIdStr != null && !catIdStr.isEmpty())
                ? productDAO.getByCategory(Integer.parseInt(catIdStr))
                : productDAO.findAll();
        req.setAttribute("categories", categories);
        req.setAttribute("products", products);
        req.setAttribute("selectedCategory", catIdStr);
        req.getRequestDispatcher("/catalog.jsp").forward(req, resp);
    }
}