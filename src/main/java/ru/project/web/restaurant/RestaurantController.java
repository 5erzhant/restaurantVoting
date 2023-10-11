package ru.project.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Restaurant;
import ru.project.repository.RestaurantRepository;
import ru.project.web.SecurityUtil;

@Controller
public class RestaurantController {
    private final RestaurantRepository repository;

    @Autowired
    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant create(Restaurant restaurant) {
        return repository.save(restaurant, SecurityUtil.authUserId());
    }

    public Restaurant get(int id) {
        return repository.get(SecurityUtil.authUserId(), id);
    }

}
