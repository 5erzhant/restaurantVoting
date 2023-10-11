package ru.project.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.Meal;
import ru.project.repository.MealRepository;

@Controller
public class MealController {
    MealRepository mealRepository;

    @Autowired
    public MealController(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public void create(Meal meal, int restaurantId) {
        mealRepository.save(meal, restaurantId);

    }
}
