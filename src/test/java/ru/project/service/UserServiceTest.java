package ru.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.project.RestaurantTestData;
import ru.project.model.User;
import ru.project.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.project.RestaurantTestData.RESTAURANT_MATCHER;
import static ru.project.RestaurantTestData.admin1Restaurants;
import static ru.project.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int id = created.getId();
        User newUser = getNew();
        newUser.setId(id);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(id), newUser);
    }

    @Test
    void get() {
        User newUser = service.get(USER_ID);
        USER_MATCHER.assertMatch(newUser, user);
    }

    @Test
    void getWithRestaurants() {
        User newUser = service.getWithRestaurants(ADMIN_ID);
        admin.setRestaurants(admin1Restaurants);
        USER_MATCHER.assertMatch(newUser, admin);
        RESTAURANT_MATCHER.assertMatch(newUser.getRestaurants(), RestaurantTestData.admin1Restaurants);
    }

    @Test
    void getWithRestaurantsNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithRestaurants(NOT_FOUND));
    }

    @Test
    void delete() {
        service.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_ID));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER_ID), getUpdated());
    }

    @Test
    void duplicateMailCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new User(1, "Duplicate", "user@mail.ru", "newPass")));
    }

    @Test
    void deletedNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }
}