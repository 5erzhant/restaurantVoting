package ru.project.model;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = Restaurant.USERS_RESTAURANTS, query = "SELECT r FROM Restaurant r WHERE r.admin.id=:adminId " +
                "ORDER BY r.name"),
        @NamedQuery(name = Restaurant.GET_ALL, query = "SELECT r FROM Restaurant r ORDER BY r.name"),
        @NamedQuery(name = Restaurant.DELETE, query = "DELETE FROM Restaurant r WHERE r.id=:id AND r.admin.id=:adminId")})

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    public static final String USERS_RESTAURANTS = "Restaurant.getRestaurants";
    public static final String GET_ALL = "Restaurant.getAll";
    public static final String DELETE = "Restaurant.delete";

    @ManyToOne(fetch = FetchType.LAZY)
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
}
