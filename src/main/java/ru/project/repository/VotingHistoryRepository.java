package ru.project.repository;

import ru.project.model.VotingHistory;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VotingHistoryRepository {

    VotingHistory save(VotingHistory votingHistory);

    Map<Date, List<String>> getRestaurantVotingHistory(int restaurantId);

    Map<Date, String> getUserVotingHistory(int userId);
}