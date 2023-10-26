package ru.project.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "voting_history")
public class VotingHistory extends AbstractBaseEntity {
    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "restaurant_id", nullable = false)
    private int restaurantId;

    @Column(name = "day", nullable = false)
    private LocalDate date;

    public VotingHistory(Integer id) {
        super(id);
    }

    public VotingHistory() {
    }

    public VotingHistory(int userId, int restaurantId, LocalDate date) {
        super(null);
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}

