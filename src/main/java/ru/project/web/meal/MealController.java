package ru.project.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Meal;
import ru.project.repository.MealRepository;

import java.util.List;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(Meal.class);
    MealRepository repository;

    @Autowired
    public MealController(MealRepository repository) {
        this.repository = repository;
    }

    public void create(Meal meal, int restaurantId) {
        log.info("create meal {}", meal);
        repository.save(meal, restaurantId);

    }

    public void update(Meal meal, int restaurantId) {
        log.info("update meal {}", meal);
        repository.save(meal, restaurantId);
    }

    public Meal get(int mealId, int restaurantId) {
        log.info("get meal {}", mealId);
        return repository.get(mealId, restaurantId);
    }

    public List<Meal> getAll(int restaurantId) {
        log.info("get restaurant {} meal", restaurantId);
        return repository.getAll(restaurantId);
    }
}
