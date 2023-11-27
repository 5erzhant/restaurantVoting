package ru.project.repository.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.project.model.VotingHistory;
import ru.project.repository.VotingHistoryRepository;
import ru.project.web.SecurityUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository()
@Transactional(readOnly = true)
public class JpaVotingHistoryRepository implements VotingHistoryRepository {

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

    public Map<LocalDate, List<String>> getVotingHistory(int restaurantId) {
        Map<LocalDate, List<Integer>> votingHistoryMap = em.createQuery("SELECT v FROM VotingHistory v WHERE restaurantId=:restaurantId",
                        VotingHistory.class)
                .setParameter("restaurantId", restaurantId)
                .getResultList()
                .stream()
                .collect(Collectors.groupingBy(VotingHistory::getDate, Collectors.mapping(VotingHistory::getUserId,
                        Collectors.toList())));

        List<Integer> userIdList = votingHistoryMap.values().stream()
                .flatMap(List::stream)
                .distinct()
                .sorted()
                .toList();

        List<String> namesList = em.createQuery("SELECT u.name FROM User u WHERE u.id IN :list " +
                        "ORDER BY u.id ", String.class)
                .setParameter("list", userIdList)
                .getResultList();

        Map<Integer, String> nameMatchingList = IntStream.range(0, userIdList.size())
                .boxed()
                .collect(Collectors.toMap(userIdList::get, namesList::get));

        Map<LocalDate, List<String>> result = votingHistoryMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream().map(nameMatchingList::get).collect(Collectors.toList())));

        Map<LocalDate, List<String>> treeMap = new TreeMap<>(Comparator.reverseOrder());
        treeMap.putAll(result);

        return treeMap;
    }
}
