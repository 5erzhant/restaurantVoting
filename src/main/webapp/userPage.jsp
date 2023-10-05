<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница пользователя</title>
</head>
<body>
<jsp:useBean id="user" type="ru.project.model.User" scope="request"/>
<h2>Здравствуйте, ${user.name}</h2>
<a href="users?action=update">Редактировать профиль</a><br>
<a href="users?action=delete">Удалить профиль</a><br>
<a href="restaurants?action=create">Создать ресторан</a><br>
<a href="restaurants?action=all">Выбрать ресторан</a><br>
</body>
</html>
