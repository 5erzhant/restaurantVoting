package ru.project.web.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.project.model.User;
import ru.project.service.UserService;

import static ru.project.web.user.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void create() {
        User created = userService.create(getNew());
        int id = created.getId();
        User newUser = getNew();
        newUser.setId(id);
        USER_MATCHER.assertMatch(created, newUser);
    }

    @Test
    public void get() {
        User newUser = userService.get(USER_ID);
        USER_MATCHER.assertMatch(newUser, user);
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
        User updated = getUpdated();
        userService.update(updated);
        USER_MATCHER.assertMatch(userService.get(USER_ID), getUpdated());
    }
}