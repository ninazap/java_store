<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="header.jsp"/>
<h2>История заказов</h2>
<c:choose>
    <c:when test="${empty orders}">
        <p>Заказов пока нет</p>
    </c:when>
    <c:otherwise>
        <table>
            <tr><th>№</th><th>Дата</th><th>Сумма</th><th>Статус</th></tr>
            <c:forEach var="ord" items="${orders}">
                <tr>
                    <td>${ord.id}</td>
                    <td><fmt:formatDate value="${ord.createdAt}" type="both" pattern="dd.MM.yyyy HH:mm"/></td>
                    <td><fmt:formatNumber value="${ord.totalAmount}" type="currency" currencyCode="RUB"/></td>
                    <td>${ord.status}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>
<jsp:include page="footer.jsp"/>