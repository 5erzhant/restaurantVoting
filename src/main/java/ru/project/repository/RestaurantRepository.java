package ru.project.repository;

import ru.project.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {
    Restaurant save(Restaurant restaurant, int userId);

    boolean delete(int id, int restaurantId);

    List<Restaurant> getAll();

    Restaurant get(int id, int userId);
}
