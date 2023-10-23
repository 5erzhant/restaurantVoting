package ru.project.repository;

import ru.project.model.Restaurant;

import java.util.Set;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant, int userId);

    boolean delete(int id);

    Set<Restaurant> getAll();

    Restaurant get(int authUserId, int id);
}
