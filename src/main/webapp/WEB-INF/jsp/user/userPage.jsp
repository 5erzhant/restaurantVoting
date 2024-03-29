<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница пользователя</title>
</head>
<body>
<jsp:useBean id="user" type="ru.project.model.User" scope="request"/>
<h2>Здравствуйте, ${user.name}</h2>
<a href="update">Редактировать профиль</a><br>
<a href="delete">Удалить профиль</a><br>
<a href="restaurant/new">Создать ресторан</a><br>
<a href="restaurant/all">Выбрать ресторан</a><br>
<br/>
<c:if test="${user.restaurants != null && user.restaurants.size() != 0}">
    <table>
        <thead>
        <tr>Ваши рестораны</tr>
        </thead>
        <tbody>
        <c:forEach var="restaurant" items="${user.restaurants}">
            <jsp:useBean id="restaurant" type="ru.project.model.Restaurant"/>
            <tr>
                <td>${restaurant.name}</td>
                <td><a href="restaurant/update/${restaurant.id}">Редактировать</a></td>
                <td><a href="restaurant/delete/${restaurant.id}">Удалить</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
