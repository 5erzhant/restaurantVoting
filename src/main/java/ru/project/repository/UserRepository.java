package ru.project.repository;

import ru.project.model.Restaurant;
import ru.project.model.User;

import java.util.List;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    List<Restaurant> getUserRestaurants(int userId);
}
