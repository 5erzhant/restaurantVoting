package ru.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.project.model.Meal;
import ru.project.util.Util;
import ru.project.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.project.RestaurantTestData.RESTAURANT_ID;
import static ru.project.RestaurantTestData.restaurant1;
import static ru.project.MealTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal created = service.create(getNew(), RESTAURANT_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, RESTAURANT_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        updated.setRestaurant(restaurant1);
        service.update(updated, RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, RESTAURANT_ID), getUpdated());
    }

    @Test
    public void get() {
        Meal actual = service.get(MEAL_ID, RESTAURANT_ID);
        MEAL_MATCHER.assertMatch(actual, meal1);
    }

    @Test
    public void getRestaurantMeals() {
        MEAL_MATCHER.assertMatch(service.getRestaurantMeals(RESTAURANT_ID), restaurantMeals);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, RESTAURANT_ID + 1));
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, RESTAURANT_ID + 1));
        MEAL_MATCHER.assertMatch(service.get(MEAL_ID, RESTAURANT_ID), meal1);
    }

    @Test
    public void getCurrentMeals() {
        MEAL_MATCHER.assertMatch(Util.getFilteredMeals(service.getRestaurantMeals(RESTAURANT_ID), Meal::isCurrent),
                currentRestaurantMeals);
    }
}