<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница пользователя</title>
</head>
<body>
<jsp:useBean id="user" type="ru.project.model.User" scope="request"/>
 <h2>Здравствуйте, ${user.name}</h2>
<a href="users?action=update&id=${user.id}">Редактировать профиль</a><br>
<a href="users?action=delete&id=${user.id}">Удалить профиль</a><br>
<a href="users?action=createRestauraunt&id=${user.id}">Создать ресторан</a><br>

</body>
</html>
