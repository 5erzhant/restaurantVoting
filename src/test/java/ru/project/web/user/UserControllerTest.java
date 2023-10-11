package ru.project.web.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.project.model.User;

import static ru.project.web.user.UserTestData.*;

@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/spring-db.xml"})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void create() {
        User created = userController.create(getNew());
        int id = created.getId();
        User newUser = getNew();
        newUser.setId(id);
        USER_MATCHER.assertMatch(created, newUser);
    }

    @Test
    public void get() {
        User newUser = userController.get(USER_ID);
        USER_MATCHER.assertMatch(newUser, user);
    }

    @Test
    public void delete() {
    }

    @Test
    public void update() {
        User updated = getUpdated();
        userController.update(updated);
        USER_MATCHER.assertMatch(userController.get(USER_ID), getUpdated());
    }
}