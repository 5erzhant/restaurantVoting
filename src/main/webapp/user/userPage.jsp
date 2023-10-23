<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<br/>
<c:if test="${user.restaurants.size()!=0}">
    <table>
        <thead>
        <tr>Ваши рестораны</tr>
        </thead>
        <tbody>
        <c:forEach var="restaurant" items="${user.restaurants}">
            <jsp:useBean id="restaurant" type="ru.project.model.Restaurant"/>
            <tr>
                <td>${restaurant.name}</td>
                <td><a href="restaurants?action=update&id=${restaurant.id}">Редактировать</a></td>
                <td><a href="restaurants?action=delete&id=${restaurant.id}">Удалить</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
