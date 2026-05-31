<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
<div class="product-detail">
    <img src="${product.imageUrl}" alt="${product.name}">
    <h2>${product.name}</h2>
    <p>${product.description}</p>
    <p class="price"><fmt:formatNumber value="${product.price}" type="currency" currencyCode="RUB"/></p>
    <form action="${pageContext.request.contextPath}/cart" method="post">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="productId" value="${product.id}">
        <input type="number" name="quantity" value="1" min="1">
        <button type="submit">Добавить в корзину</button>
    </form>
</div>
<jsp:include page="footer.jsp"/>