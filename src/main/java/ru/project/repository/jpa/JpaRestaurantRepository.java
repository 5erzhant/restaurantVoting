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
    @Transactional
    public boolean delete(int adminId, int id) {
        return em.createNamedQuery(Restaurant.DELETE).setParameter("id", id)
                .setParameter("adminId", adminId)
                .executeUpdate() != 0;
    }

    @Override
    public List<Restaurant> getRestaurants(int userId) {
        return em.createNamedQuery(Restaurant.USERS_RESTAURANTS, Restaurant.class)
                .setParameter("adminId", userId)
                .getResultList();
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.GET_ALL, Restaurant.class).getResultList();
    }
}
