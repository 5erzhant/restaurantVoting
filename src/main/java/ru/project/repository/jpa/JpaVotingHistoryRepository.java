package ru.project.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.VotingHistory;
import ru.project.web.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;

@Repository()
@Transactional(readOnly = true)
public class JpaVotingHistoryRepository {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public VotingHistory save(VotingHistory votingHistory) {
        TypedQuery<Integer> query = em.createQuery(
                        "SELECT v.id FROM VotingHistory v WHERE v.userId = :userId AND v.date = :day", Integer.class)
                .setParameter("userId", SecurityUtil.authUserId())
                .setParameter("day", LocalDate.now());
        int id;
        try {
            id = query.getSingleResult();
        } catch (NoResultException nre) {
            em.persist(votingHistory);
            return votingHistory;
        }
        votingHistory.setId(id);
        em.merge(votingHistory);
        return votingHistory;
    }
}
