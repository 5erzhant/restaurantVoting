package ru.project.repository.datajpa;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.Restaurant;

import java.util.List;

public interface CrudRestaurantRepository extends CrudRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query(name = Restaurant.DELETE)
    int delete(@Param("id") int id, @Param("adminId") int userId);

    @Query(name = Restaurant.GET_ALL)
    List<Restaurant> getAll();

    @Query(name = Restaurant.USER_RESTAURANTS)
    List<Restaurant> getRestaurants(@Param("adminId") int id);

}
