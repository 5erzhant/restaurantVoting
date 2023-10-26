package ru.project.web.votingHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.project.model.User;
import ru.project.model.VotingHistory;
import ru.project.repository.jpa.JpaVotingHistoryRepository;
import ru.project.web.SecurityUtil;

import java.time.LocalDate;

@Controller
public class VotingHistoryController {
    private static final Logger log = LoggerFactory.getLogger(User.class);
    private final JpaVotingHistoryRepository repository;

    @Autowired
    public VotingHistoryController(JpaVotingHistoryRepository repository) {
        this.repository = repository;
    }

    public VotingHistory vote(int restaurantId) {
        log.info("voting for {}", restaurantId);
        return repository.save(new VotingHistory(SecurityUtil.authUserId(), restaurantId, LocalDate.now()));
    }
}
