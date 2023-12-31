package ru.project.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meal")
public class Meal extends AbstractBaseEntity {

    @Column(name = "price")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "description")
    private String description;

    @Column(name = "is_current")
    private boolean isCurrent = true;

    public Meal() {
    }

    public Meal(String description, Integer price) {
        super(null);
        this.price = price;
        this.description = description;
    }

    public Meal(int id, String description, int price) {
        super(id);
        this.description = description;
        this.price = price;
        this.restaurant = new Restaurant();
    }

    public Meal(int id, String description, int price, boolean isCurrent) {
        this(id, description, price);
        this.isCurrent = isCurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }
}
