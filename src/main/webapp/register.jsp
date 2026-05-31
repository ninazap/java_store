<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"/>
<h2>Регистрация</h2>
<c:if test="${not empty error}"><p class="error">${error}</p></c:if>
<form action="${pageContext.request.contextPath}/register" method="post">
    <input type="hidden" name="action" value="register">
    <input type="text" name="username" placeholder="Логин" required>
    <input type="password" name="password" placeholder="Пароль" required>
    <input type="email" name="email" placeholder="Email" required>
    <button type="submit">Зарегистрироваться</button>
</form>
<jsp:include page="footer.jsp"/>