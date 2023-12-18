package ru.project.repository;

import ru.project.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal save(Meal Meal, int restaurantId);

    Meal get(int id, int restaurantId);

    List<Meal> getRestaurantMeals(int restaurantId);

}
