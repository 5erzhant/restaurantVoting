package ru.project.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Meal;
import ru.project.service.MealService;

import java.util.List;

import static ru.project.util.ValidationUtil.assureIdConsistent;
import static ru.project.util.ValidationUtil.checkNew;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(Meal.class);
    MealService service;

    @Autowired
    public MealController(MealService service) {
        this.service = service;
    }

    public Meal create(Meal meal, int restaurantId) {
        checkNew(meal);
        log.info("create meal {} for restaurant {}", meal, restaurantId);
        return service.create(meal, restaurantId);

    }

    public void update(Meal meal, int id, int restaurantId) {
        assureIdConsistent(meal, id);
        log.info("update meal {} for restaurant {}", meal, restaurantId);
        service.update(meal, restaurantId);
    }

    public Meal get(int mealId, int restaurantId) {
        log.info("get meal {} for restaurant {}", mealId, restaurantId);
        return service.get(mealId, restaurantId);
    }

    public List<Meal> getAll(int restaurantId) {
        log.info("get meals for restaurant {}", restaurantId);
        return service.getAll(restaurantId);
    }
}
