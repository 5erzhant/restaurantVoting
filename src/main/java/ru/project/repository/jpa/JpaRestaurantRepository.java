package ru.project.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.Restaurant;
import ru.project.model.User;
import ru.project.repository.RestaurantRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant, int userId) {
        restaurant.setAdmin(em.getReference(User.class, userId));
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else return em.merge(restaurant);
    }

    @Override
    public Restaurant get(int authUserId, int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Restaurant> getAll() {
        return null;
    }

}
