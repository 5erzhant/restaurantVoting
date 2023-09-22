package ru.project.repository;

import ru.project.model.User;

public interface UserRepository {
    User save(User user);

    boolean delete(int id);

    User get(int id);

}
