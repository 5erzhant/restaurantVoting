<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Голосование</title>
    <base href="${pageContext.request.contextPath}/"/>
    <link rel="stylesheet" href="resources/css/style.css">
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
    <jsp:useBean id="restaurants" scope="request" type="java.util.List"/>
    <c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" type="ru.project.model.Restaurant"/>
        <tr>
            <td>${restaurant.name}</td>
            <td>
                <select name="select">
                    <c:forEach items="${restaurant.meals}" var="meal">
                        <c:if test="${meal.current==true}">
                            <option>${meal.description} - ${meal.price} рублей</option>
                        </c:if>
                    </c:forEach>
                </select>
            </td>
            <td>
                <a href="/user/restaurant/vote/${restaurant.id}">голосовать</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
