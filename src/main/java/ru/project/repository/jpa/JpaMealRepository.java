package ru.project.repository.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.project.model.Meal;
import ru.project.model.Restaurant;
import ru.project.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int restaurantId) {
        if (meal.isNew()) {
            meal.setRestaurant(em.getReference(Restaurant.class, restaurantId));
            em.persist(meal);
            return meal;
        }
        return get(meal.id(), restaurantId) == null ? null : em.merge(meal);
    }

    @Override
    public Meal get(int id, int restaurantId) {
        Meal meal = em.find(Meal.class, id);
        return meal != null && meal.getRestaurant().getId() == restaurantId ? meal : null;
    }

    @Override
    public List<Meal> getRestaurantMeals(int restaurantId) {
        return em.createNamedQuery(Meal.RESTAURANT_MEALS, Meal.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList();
    }
}
