package ru.project.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.User;
import ru.project.service.UserService;
import ru.project.web.SecurityUtil;

import static ru.project.util.ValidationUtil.assureIdConsistent;
import static ru.project.util.ValidationUtil.checkNew;

@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(User.class);
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    public User create(User user) {
        log.info("create user {}", user);
        checkNew(user);
        return service.create(user);
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

    public void update(User user, int id) {
        log.info("update user {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public User getWithRestaurants(int id) {
        log.info("get user {} with restaurants", id);
        return service.getWithRestaurants(id);
    }
}
