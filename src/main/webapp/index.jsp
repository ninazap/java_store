<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%
    if (session.getAttribute("user") != null) {
        response.sendRedirect(request.getContextPath() + "/catalog");
    } else {
        response.sendRedirect(request.getContextPath() + "/login");
    }
%>