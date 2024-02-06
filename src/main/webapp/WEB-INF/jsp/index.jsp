<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Выполните вход</title>
</head>
<body>

<form method="post" action="user/set">
    <select name="id">
        <option value="100000">User</option>
        <option value="100001">Admin</option>
    </select>
    <button type="submit">Авторизоваться</button>
</form>

<a href="user/new">Зарегистрироваться</a>
</body>
</html>
