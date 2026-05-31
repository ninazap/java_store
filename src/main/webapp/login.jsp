<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<h2>Вход</h2>
<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
<form action="${pageContext.request.contextPath}/login" method="post">
    <input type="hidden" name="action" value="login">
    <input type="text" name="username" placeholder="Логин" required>
    <input type="password" name="password" placeholder="Пароль" required>
    <button type="submit">Войти</button>
</form>
<jsp:include page="footer.jsp"/>