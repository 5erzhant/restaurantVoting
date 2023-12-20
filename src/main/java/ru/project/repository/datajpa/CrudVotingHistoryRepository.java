package ru.project.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.VotingHistory;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVotingHistoryRepository extends JpaRepository<VotingHistory, Integer> {
    VotingHistory findByUserIdAndDate(int userId, LocalDate date);

    @Query(value = "SELECT VOTING_HISTORY.DAY, USERS.NAME FROM VOTING_HISTORY JOIN USERS ON " +
            "VOTING_HISTORY.USER_ID = USERS.ID WHERE VOTING_HISTORY.RESTAURANT_ID=:id", nativeQuery = true)
    List<Object> getRestaurantHistory(@Param("id") int restaurantId);

    @Query(value = "SELECT VOTING_HISTORY.DAY, RESTAURANT.NAME FROM VOTING_HISTORY JOIN RESTAURANT ON " +
            "VOTING_HISTORY.RESTAURANT_ID = RESTAURANT.ID WHERE VOTING_HISTORY.USER_ID=:id", nativeQuery = true)
    List<Object> getUserHistory(@Param("id") int userId);

}
