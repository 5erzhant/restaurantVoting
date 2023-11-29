package ru.project;

import ru.project.model.Restaurant;

import java.util.List;

import static ru.project.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RESTAURANT_ID = START_SEQ + 3;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Restaurant");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT_ID + 1, "Kafe");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT_ID + 2, "Shawerma");
    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);
    public static final List<Restaurant> admin1Restaurants = List.of(restaurant1, restaurant2);

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator("admin", "meals");

    public static Restaurant getNew() {
        return new Restaurant(null, "Restaurant");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT_ID, "Updated restaurant");
        updated.setAdmin(UserTestData.admin);
        return updated;
    }


}