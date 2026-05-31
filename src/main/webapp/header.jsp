<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Магазин</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<nav>
    <a href="${pageContext.request.contextPath}/catalog">📦 Каталог</a>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <span>👤 ${sessionScope.user.username}</span>
            <a href="${pageContext.request.contextPath}/cart">🛒 Корзина</a>
            <a href="${pageContext.request.contextPath}/orders">📜 Заказы</a>
            <a href="${pageContext.request.contextPath}/logout">🚪 Выход</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">🔑 Вход</a>
            <a href="${pageContext.request.contextPath}/register">📝 Регистрация</a>
        </c:otherwise>
    </c:choose>
</nav>
<div class="container">