package ru.project.model;

import javax.persistence.*;


@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;


    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        this(null, name);
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

//    public List<Meal> getMenu() {
//        return menu;
//    }
//
//    public void setMenu(List<Meal> menu) {
//        this.menu = menu;
//    }
}
