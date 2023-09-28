package ru.project.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.User;
import ru.project.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    public User create(User user) {
        return repository.save(user);
    }

    public User get(int id) {
        return repository.get(id);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
