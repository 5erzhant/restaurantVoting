package ru.project.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {
    private User admin;
    private List<Meal> menu;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public void setMenu(List<Meal> menu) {
        this.menu = menu;
    }
}
