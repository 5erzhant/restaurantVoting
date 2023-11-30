package ru.project.repository;

import ru.project.model.VotingHistory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface VotingHistoryRepository {

    VotingHistory save(VotingHistory votingHistory);

    Map<LocalDate, List<String>> getRestaurantVotingHistory(int restaurantId, int userId);

    Map<LocalDate, String> getUserVotingHistory(int userId);
}