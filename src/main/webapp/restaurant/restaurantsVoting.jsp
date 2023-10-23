<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Голосование</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<table class="table">
    <thead>
    <tr>
        <td>Название</td>
        <td>Меню</td>
        <td></td>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="restaurants" scope="request" type="java.util.Set"/>
    <c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" type="ru.project.model.Restaurant"/>
        <tr>
            <td>${restaurant.name}</td>
            <td>
                <select name="select">
                    <c:forEach items="${restaurant.mealList}" var="meal">
                        <c:if test="${meal.current==true}">
                            <option>${meal.description} - ${meal.price} рублей</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
            <td>
                <a href="restaurants?action=vote&id=${restaurant.id}">голосовать</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
