package ru.project.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Restaurant;
import ru.project.repository.RestaurantRepository;
import ru.project.web.SecurityUtil;

import java.util.List;

@Controller
public class RestaurantController {
    private static final Logger log = LoggerFactory.getLogger(Restaurant.class);
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        return repository.save(restaurant, SecurityUtil.authUserId());
    }

    public void update(Restaurant restaurant) {
        log.info("update restaurant {}", restaurant);
        repository.save(restaurant, SecurityUtil.authUserId());
    }

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return repository.get(SecurityUtil.authUserId(), id);
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        repository.delete(SecurityUtil.authUserId(), id);
    }

    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return repository.getAll();
    }

    public List<Restaurant> getRestaurants(int userId) {
        log.info("get user's {} restaurants", userId);
        return repository.getRestaurants(userId);
    }
}
