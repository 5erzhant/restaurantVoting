package ru.project.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @OneToOne
    @JoinColumn(name = "admin_id")
    private User admin;

    @OneToMany
    @JoinColumn(name = "restaurant_id")
    private List<Meal> menu = new ArrayList<>();

    public Restaurant() {
    }
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
