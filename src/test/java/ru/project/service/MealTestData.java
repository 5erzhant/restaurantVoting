package ru.project.service;

import ru.project.MatcherFactory;
import ru.project.model.Meal;

import java.util.List;

import static ru.project.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID = START_SEQ + 6;
    public static final int NOT_FOUND = 10;
    public static final Meal meal1 = new Meal(MEAL_ID, "gerkules", 150);
    public static final Meal meal2 = new Meal(MEAL_ID + 1, "kompot", 30);
    public static final Meal meal3 = new Meal(MEAL_ID + 2, "omlet", 100, false);
    public static final List<Meal> restaurantMeals = List.of(meal1, meal2, meal3);
    public static final List<Meal> currentRestaurantMeals = List.of(meal1, meal2);
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.
            usingIgnoringFieldsComparator("restaurant");

    public static Meal getNew() {
        return new Meal("New", 100);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL_ID, "Updated", 555);
    }
}