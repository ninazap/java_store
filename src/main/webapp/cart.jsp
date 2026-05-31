<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
<h2>Корзина</h2>
<c:choose>
    <c:when test="${empty cartItems}">
        <p>Корзина пуста</p>
        <a href="${pageContext.request.contextPath}/catalog">Перейти в каталог</a>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>Товар</th><th>Цена</th><th>Кол-во</th><th>Сумма</th><th></th></tr>
            <c:set var="total" value="0"/>
            <c:forEach var="item" items="${cartItems}">
                <tr>
                    <td>${item.product.name}</td>
                    <td><fmt:formatNumber value="${item.product.price}" type="currency" currencyCode="RUB"/></td>
                    <td>${item.quantity}</td>
                    <td><fmt:formatNumber value="${item.product.price * item.quantity}" type="currency" currencyCode="RUB"/></td>
                    <td><a href="${pageContext.request.contextPath}/cart?action=remove&productId=${item.productId}">Удалить</a></td>
                </tr>
                <c:set var="total" value="${total + item.product.price * item.quantity}"/>
            </c:forEach>
            <tr><td colspan="3" style="text-align:right"><strong>Итого:</strong></td><td><fmt:formatNumber value="${total}" type="currency" currencyCode="RUB"/></td></tr>
        </table>
        <form action="${pageContext.request.contextPath}/order" method="get">
            <button type="submit" class="btn-checkout">Оформить заказ</button>
        </form>
    </c:otherwise>
</c:choose>
<jsp:include page="footer.jsp"/>