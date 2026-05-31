package com.shop.controllers;

import com.shop.beans.User;
import com.shop.dao.UserDAO;
import com.shop.utils.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet({"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if ("/logout".equals(path)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/catalog");
            return;
        }
        if ("/login".equals(path)) req.getRequestDispatcher("/login.jsp").forward(req, resp);
        else if ("/register".equals(path)) req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("login".equals(action)) {
            User user = userDAO.findByUsername(req.getParameter("username"));
            if (user != null && PasswordUtil.check(req.getParameter("password"), user.getPassword())) {
                req.getSession().setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/catalog");
            } else {
                req.setAttribute("error", "Неверный логин или пароль");
                doGet(req, resp);
            }
        } else if ("register".equals(action)) {
            String hashedPassword = PasswordUtil.hash(req.getParameter("password"));
            User user = new User(0, req.getParameter("username"), hashedPassword, req.getParameter("email"), "USER");
            if (userDAO.register(user)) {
                resp.sendRedirect(req.getContextPath() + "/login");
            } else {
                req.setAttribute("error", "Ошибка регистрации (логин/email уже заняты)");
                doGet(req, resp);
            }
        }
    }
}
