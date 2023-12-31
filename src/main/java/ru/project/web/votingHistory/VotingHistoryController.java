package ru.project.web.votingHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.User;
import ru.project.model.VotingHistory;
import ru.project.repository.VotingHistoryRepository;
import ru.project.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class VotingHistoryController {
    private static final Logger log = LoggerFactory.getLogger(User.class);
    private final VotingHistoryRepository repository;

    @Autowired
    public VotingHistoryController(VotingHistoryRepository repository) {
        this.repository = repository;
    }

    public void vote(int restaurantId) {
        log.info("voting for {}", restaurantId);
        repository.save(new VotingHistory(SecurityUtil.authUserId(), restaurantId, LocalDate.now()));
    }

    public Map<Date, List<String>> getRestaurantVotingHistory(int restaurantId) {
        log.info("restaurant voting history");
        return repository.getRestaurantVotingHistory(restaurantId);
    }

    public Map<Date, String> getUserVotingHistory() {
        log.info("user voting history");
        return repository.getUserVotingHistory(SecurityUtil.authUserId());
    }
}
