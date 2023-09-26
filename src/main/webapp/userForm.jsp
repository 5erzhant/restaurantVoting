<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Регистрация</title>
</head>
<body>
<jsp:useBean id="user" type="ru.project.model.User" scope="request"/>
<form method="post" action="users">
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
</form>
</body>
</html>
