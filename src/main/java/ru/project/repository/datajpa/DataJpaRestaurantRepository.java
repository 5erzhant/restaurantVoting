package ru.project.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.project.model.Restaurant;
import ru.project.repository.RestaurantRepository;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {
    private final CrudRestaurantRepository crudRestaurantRepository;
    private final CrudUserRepository crudUserRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRepository, CrudUserRepository crudUserRepository) {
        this.crudRestaurantRepository = crudRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant, int userId) {
        if (!restaurant.isNew() && get(restaurant.id(), userId) == null) {
            return null;
        }
        restaurant.setAdmin(crudUserRepository.getReferenceById(userId));
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRestaurantRepository.delete(id, userId) != 0;
    }

    @Override
    public List<Restaurant> getUserRestaurants(int userId) {
        return crudRestaurantRepository.getRestaurants(userId);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.getAll();
    }

    @Override
    public Restaurant get(int id, int userId) {
        return crudRestaurantRepository.findById(id)
                .filter(restaurant -> restaurant.getAdmin().getId() == userId)
                .orElse(null);
    }
}
