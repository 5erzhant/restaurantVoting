package ru.project.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Meal;
import ru.project.repository.MealRepository;

@Controller
public class MealController {
    private static final Logger log = LoggerFactory.getLogger(Meal.class);
    MealRepository mealRepository;

    @Autowired
    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void create(Meal meal, int restaurantId) {
        log.info("create meal {}", meal);
        mealRepository.save(meal, restaurantId);

    }

    public void update(Meal meal, int restaurantId) {
        log.info("update meal {}", meal);
        mealRepository.save(meal, restaurantId);
    }

    public Meal get(int mealId, int restaurantId) {
        log.info("get meal {}", mealId);
        return mealRepository.get(mealId, restaurantId);
    }
}
