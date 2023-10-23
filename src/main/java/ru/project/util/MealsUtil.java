package ru.project.util;

import ru.project.model.Meal;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static List<Meal> getFilteredMeals(List<Meal> mealList, Predicate<Meal> filter) {
        return mealList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}