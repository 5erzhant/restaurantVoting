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
        if (restaurant.isNew()) {
            restaurant.setAdmin(em.getReference(User.class, userId));
            em.persist(restaurant);
            return restaurant;
        }
        return get(restaurant.id(), userId) == null ? null : em.merge(restaurant);
    }

    @Override
    public Restaurant get(int id, int userId) {
        Restaurant restaurant = em.find(Restaurant.class, id);
        return restaurant != null && restaurant.getAdmin().getId() == userId ? restaurant : null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .setParameter("adminId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Restaurant> getUserRestaurants(int userId) {
        return em.createNamedQuery(Restaurant.USER_RESTAURANTS, Restaurant.class)
                .setParameter("adminId", userId)
                .getResultList();
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.GET_ALL, Restaurant.class).getResultList();
    }
}
