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
        <c:forEach var="meal" items="${restaurant.mealList}">
            <jsp:useBean id="meal" type="ru.project.model.Meal"/>
            <input type="hidden" name="mealId_${meal.id}" value="${meal.id}">
            <dt>Блюдо</dt>
            <dd><input type="text" name="description_${meal.id}" value="${meal.description}" required></dd>
            <dt>Цена</dt>
            <dd><input type="number" name="price_${meal.id}" value="${meal.price}" required></dd>
        </c:forEach>
    </dl>
    <dl>
        <dt>Блюдо</dt>
        <dd><input type="text" name="description"></dd>
        <dt>Цена</dt>
        <dd><input type="number" name="price"></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>
</html>
