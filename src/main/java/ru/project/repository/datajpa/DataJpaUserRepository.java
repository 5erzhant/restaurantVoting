package ru.project.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.project.model.User;
import ru.project.repository.UserRepository;

@Repository
public class DataJpaUserRepository implements UserRepository {
    private final CrudUserRepository crudRepository;

    public DataJpaUserRepository(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public User getWithRestaurants(int id) {
        return crudRepository.getWithRestaurants(id);
    }
}
