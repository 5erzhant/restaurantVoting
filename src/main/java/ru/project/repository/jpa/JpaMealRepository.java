package ru.project.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.Meal;
import ru.project.model.Restaurant;
import ru.project.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        meal.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else return em.merge(meal);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Meal get(int id, int restaurantId) {
        return em.find(Meal.class, id);
    }

    @Override
    public List<Meal> getAll(int restaurantId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}
