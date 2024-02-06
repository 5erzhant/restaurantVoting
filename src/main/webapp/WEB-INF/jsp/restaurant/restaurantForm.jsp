<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Форма ресторана</title>
    <base href="${pageContext.request.contextPath}/"/>
</head>
<jsp:useBean id="restaurant" type="ru.project.model.Restaurant" scope="request"/>
<form method="post" action="user/restaurant">
    <input type="hidden" name="id" value="${restaurant.id}">
    <dl>
        <dt>Название</dt>
        <dd><input type="text" value="${restaurant.name}" name="name" required></dd>
    </dl>
    <dl>
        <c:if test="${restaurant.id != null}">
            <jsp:useBean id="currentMeals" scope="request" type="java.util.List"/>
            <c:forEach var="currentMeal" items="${currentMeals}">
                <jsp:useBean id="currentMeal" type="ru.project.model.Meal"/>
                <input type="hidden" name="mealId_${currentMeal.id}" value="${currentMeal.id}">
                <dt>Блюдо</dt>
                <dd><input type="text" name="description_${currentMeal.id}" value="${currentMeal.description}" required>
                </dd>
                <dt>Цена</dt>
                <dd><input type="number" name="price_${currentMeal.id}" value="${currentMeal.price}" required></dd>
                <input type="checkbox" id="check" name="check_${currentMeal.id}">
                <label for="check">Убрать из меню</label>
            </c:forEach>

            <jsp:useBean id="otherMeals" scope="request" type="java.util.List"/>
            <c:if test="${otherMeals.size()!=0}">
                <h2>Выбрать из имеющейся</h2>
                <dl>
                    <dt>Блюдо</dt>
                    <dd>
                        <select name="otherMeal" size="1">
                            <option disabled selected></option>
                            <c:forEach var="otherMeal" items="${otherMeals}">
                                <jsp:useBean id="otherMeal" type="ru.project.model.Meal"/>
                                <option value="${otherMeal.id}">${otherMeal.description}</option>
                            </c:forEach>
                        </select>
                    </dd>
                </dl>
            </c:if>
        </c:if>
    </dl>
    <h2>Создать новую еду</h2>
    <dl>
        <dt>Блюдо</dt>
        <dd><input type="text" name="description"></dd>
        <dt>Цена</dt>
        <dd><input type="number" name="price"></dd>
    </dl>
    <button type="submit">Сохранить</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>
<c:if test="${restaurant.id != null}">
    <h2>
        <a href="restaurants?action=history&id=${restaurant.id}">История голосований</a>
    </h2>
</c:if>
</html>
