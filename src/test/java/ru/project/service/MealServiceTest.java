package ru.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.project.model.Meal;
import ru.project.util.Util;
import ru.project.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.MealTestData.*;
import static ru.project.RestaurantTestData.RESTAURANT_ID;
import static ru.project.RestaurantTestData.restaurant1;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService service;

    @Test
    void create() {
        Meal created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, RESTAURANT_ID), newMeal);
    }

    @Test
    void update() {
        Meal updated = getUpdated();
        updated.setRestaurant(restaurant1);
        service.update(updated, RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, RESTAURANT_ID), getUpdated());
    }

    @Test
    void get() {
        Meal actual = service.get(MEAL_ID, RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(actual, meal1);
    }

    @Test
    void getRestaurantMeals() {
        MEAL_MATCHER.assertMatch(service.getRestaurantMeals(RESTAURANT_ID), restaurantMeals);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, RESTAURANT_ID + 1));
    }

    @Test
    void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, RESTAURANT_ID + 1));
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, RESTAURANT_ID), meal1);
    }

    @Test
    void getCurrentMeals() {
        MEAL_MATCHER.assertMatch(Util.getFilteredMeals(service.getRestaurantMeals(RESTAURANT_ID), Meal::isCurrent),
                currentRestaurantMeals);
    }
}