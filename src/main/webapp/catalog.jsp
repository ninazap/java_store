<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
<h1>Каталог товаров</h1>
<div class="filters">
    <a href="${pageContext.request.contextPath}/catalog" class="${empty selectedCategory ? 'active' : ''}">Все</a>
    <c:forEach var="cat" items="${categories}">
        <a href="${pageContext.request.contextPath}/catalog?categoryId=${cat.id}" class="${selectedCategory == cat.id ? 'active' : ''}">${cat.name}</a>
    </c:forEach>
</div>
<div class="products-grid">
    <c:forEach var="prod" items="${products}">
        <div class="product-card">
            <img src="${prod.imageUrl}" alt="${prod.name}">
            <h3>${prod.name}</h3>
            <p>${prod.description}</p>
            <p class="price"><fmt:formatNumber value="${prod.price}" type="currency" currencyCode="RUB"/></p>
            <a href="${pageContext.request.contextPath}/product?id=${prod.id}">Подробнее</a>
            <form action="${pageContext.request.contextPath}/cart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="${prod.id}">
                <input type="number" name="quantity" value="1" min="1" style="width:60px">
                <button type="submit">В корзину</button>
            </form>
        </div>
    </c:forEach>
</div>
<jsp:include page="footer.jsp"/>