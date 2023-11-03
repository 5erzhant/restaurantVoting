package ru.project.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Restaurant;
import ru.project.service.RestaurantService;
import ru.project.web.SecurityUtil;

import java.util.List;

import static ru.project.util.ValidationUtil.assureIdConsistent;
import static ru.project.util.ValidationUtil.checkNew;

@Controller
public class RestaurantController {
    private static final Logger log = LoggerFactory.getLogger(Restaurant.class);
    private final RestaurantService service;

    @Autowired
    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    public Restaurant create(Restaurant restaurant) {
        int userId = SecurityUtil.authUserId();
        checkNew(restaurant);
        log.info("create restaurant {} for user {}", restaurant, userId);
        return service.create(restaurant, userId);
    }

    public void update(Restaurant restaurant, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(restaurant, id);
        log.info("update restaurant {} for user {}", restaurant, userId);
        service.update(restaurant, userId);
    }

    public Restaurant get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get restaurant {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete restaurant {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return service.getAll();
    }

    public List<Restaurant> getUserRestaurants() {
        int userId = SecurityUtil.authUserId();
        log.info("get restaurants for user {}", userId);
        return service.getUserRestaurants(userId);
    }
}
