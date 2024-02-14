<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Редактор</title>
    <base href="${pageContext.request.contextPath}/"/>
</head>
<body>
<jsp:useBean id="user" type="ru.project.model.User" scope="request"/>
<form method="post" action="user">
    <input type="hidden" name="id" value="${user.id}">
    <input type="hidden" name="registered" value="${user.registered}">
    <dl>
        <dt>Имя</dt>
        <dd><input type="text" value="${user.name}" name="name" required></dd>
    </dl>
    <dl>
        <dt>Email</dt>
        <dd><input type="text" value="${user.email}" name="email" required></dd>
    </dl>
    <dl>
        <dt>Пароль</dt>
        <dd><input type="text" value="${user.password}" name="password" required></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>
<c:if test="${user.id != null}">
<h2>
    <a href="user/history">История голосований</a>
</h2>
</c:if>
</body>
</html>
