package ru.project.model;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private Set<Meal> mealList;

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

    public Set<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(Set<Meal> mealList) {
        this.mealList = mealList;
    }
}
