package ru.project.model;

import javax.persistence.*;
import java.util.List;

@NamedQueries({@NamedQuery(name = Restaurant.GET_ALL, query = "SELECT DISTINCT r FROM Restaurant r LEFT OUTER JOIN " +
        "FETCH r.meals ORDER BY r.id"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id AND r.admin.id=:adminId")})

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    public static final String GET_ALL = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

    @OneToMany(mappedBy = "restaurant")
    private List<Meal> meals;

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

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
