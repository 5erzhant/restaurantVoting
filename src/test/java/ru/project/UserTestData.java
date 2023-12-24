package ru.project;

import ru.project.model.User;

import static ru.project.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int NOT_FOUND = 10;

    public static MatcherFactory.Matcher<User> USER_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator("registered", "roles", "restaurants");
    public static User user = new User(USER_ID, "User", "user@mail.ru", "user");
    public static User admin = new User(ADMIN_ID, "Admin", "admin@mail.ru", "admin");

    public static User getNew() {
        return new User(null, "New", "new@mail.ru", "password");
    }

    public static User getUpdated() {
        return new User(USER_ID, "Updated name", "newemail@mail.ru", "new password");
    }
}
