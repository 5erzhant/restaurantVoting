<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Форма ресторана</title>
</head>
<jsp:useBean id="restaurant" type="ru.project.model.Restaurant" scope="request"/>
<form method="post" action="restaurants">
    <input type="hidden" name="id" value="${restaurant.id}">
    <dl>
        <dt>Название</dt>
        <dd><input type="text" value="${restaurant.name}" name="name" required></dd>
    </dl>
    <dl>
        <dt>Блюдо</dt>
        <dd><input type="text" name="description" required></dd>
        <dt>Цена</dt>
        <dd><input type="number" name="price" required></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>
</html>
