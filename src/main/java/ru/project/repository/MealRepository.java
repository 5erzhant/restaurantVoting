package ru.project.repository;

import ru.project.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id);

    Meal get(int id);

    List<Meal> getAll();
}
