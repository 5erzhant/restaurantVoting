package ru.project.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.project.model.User;
import ru.project.service.UserService;
import ru.project.web.SecurityUtil;

import static ru.project.util.ValidationUtil.assureIdConsistent;
import static ru.project.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    private static final Logger log = LoggerFactory.getLogger(User.class);

    @Autowired
    private UserService service;

    public User getWithRestaurants(int userId) {
        log.info("get user {} with restaurants", userId);
        SecurityUtil.setAuthUserId(userId);
        return service.getWithRestaurants(userId);
    }

    public User get(int id) {
        log.info("get user {}", id);
        return service.get(id);
    }

    public void delete() {
        int id = SecurityUtil.authUserId();
        log.info("delete user {}", id);
        service.delete(id);
    }

    public User create(User user) {
        log.info("create user {}", user);
        checkNew(user);
        SecurityUtil.setAuthUserId(service.create(user).getId());
        return user;
    }

    public void update(User user, int id) {
        log.info("update user {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }
}