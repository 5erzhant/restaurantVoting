package ru.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.project.RestaurantTestData;
import ru.project.model.Restaurant;
import ru.project.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.RestaurantTestData.*;
import static ru.project.UserTestData.*;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    RestaurantService service;

    @Test
    public void create() {
        Restaurant created = service.create(RestaurantTestData.getNew(), ADMIN_ID);
        int newId = created.id();
        Restaurant newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId, ADMIN_ID), newRestaurant);

    }

    @Test
    public void update() {
        Restaurant updated = RestaurantTestData.getUpdated();
        service.update(updated, ADMIN_ID);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID, ADMIN_ID), RestaurantTestData.getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(restaurant1, USER_ID));
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID, ADMIN_ID), restaurant1);
    }

    @Test
    public void get() {
        Restaurant actual = service.get(RESTAURANT_ID, ADMIN_ID);
        RESTAURANT_MATCHER.assertMatch(actual, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID, USER_ID));
    }

    @Test
    void delete() {
        service.delete(RESTAURANT_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID, ADMIN_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(RESTAURANT_ID, USER_ID));
    }

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(service.getAll(), restaurants);
    }
}