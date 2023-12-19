package ru.project.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.project.model.Meal;
import ru.project.repository.MealRepository;

import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private final CrudMealRepository crudMealRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaMealRepository(CrudMealRepository crudMealRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudMealRepository = crudMealRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Meal save(Meal meal, int restaurantId) {
        if (!meal.isNew() && get(meal.id(), restaurantId) == null) {
            return null;
        }
        meal.setRestaurant(crudRestaurantRepository.getReferenceById(restaurantId));
            return crudMealRepository.save(meal);
    }

    @Override
    public Meal get(int id, int restaurantId) {
        return crudMealRepository.findById(id)
                .filter(meal -> meal.getRestaurant().getId() == restaurantId)
                .orElse(null);
    }

    @Override
    public List<Meal> getRestaurantMeals(int restaurantId) {
        return crudMealRepository.findMealByRestaurantId(restaurantId);
    }
}
