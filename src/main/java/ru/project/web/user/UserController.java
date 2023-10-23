package ru.project.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.User;
import ru.project.repository.UserRepository;
import ru.project.web.SecurityUtil;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(User.class);
    private final UserRepository repository;

    @Autowired
    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        log.info("create user {}", user);
        return repository.save(user);
    }

    public User get(int id) {
        log.info("get user {}", id);
        return repository.get(id);
    }

    public void delete() {
        int id = SecurityUtil.authUserId();
        log.info("delete user {}", id);
        repository.delete(id);
    }

    public void update(User user) {
        log.info("update user {}", user);
        repository.save(user);
    }
}
