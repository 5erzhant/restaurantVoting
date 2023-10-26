package ru.project.util;

import ru.project.model.Meal;
import ru.project.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Util {
    public static List<Meal> getFilteredMeals(List<Meal> mealList, Predicate<Meal> filter) {
        return mealList.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static boolean isTooLate() {
        return LocalTime.now().getHour() >= SecurityUtil.getLateTime();
    }
}