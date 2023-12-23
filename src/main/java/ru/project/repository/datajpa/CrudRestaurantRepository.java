package ru.project.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id AND r.admin.id=:adminId")
    int delete(@Param("id") int id, @Param("adminId") int userId);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT OUTER JOIN FETCH r.meals ORDER BY r.id")
    List<Restaurant> getAll();
}
