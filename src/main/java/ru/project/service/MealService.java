package ru.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.project.model.Meal;
import ru.project.repository.MealRepository;

import java.util.List;

import static ru.project.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {
    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        return repository.save(meal, restaurantId);
    }

    public void update(Meal meal, int restaurantId) {
        Assert.notNull(meal, "meal must not be null");
        repository.save(meal, restaurantId);
    }

    public Meal get(int id, int restaurantId) {
        checkNotFoundWithId(repository.get(id, restaurantId), id);
        return repository.get(id, restaurantId);
    }

    public List<Meal> getAll(int restaurantId) {
        return repository.getAll(restaurantId);
    }
}
