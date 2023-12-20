package ru.project.repository.datajpa;

import org.springframework.stereotype.Repository;
import ru.project.model.VotingHistory;
import ru.project.repository.VotingHistoryRepository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DataJpaVotingHistoryRepository implements VotingHistoryRepository {
    private final CrudVotingHistoryRepository crudRepository;

    public DataJpaVotingHistoryRepository(CrudVotingHistoryRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public VotingHistory save(VotingHistory newVoice) {
        VotingHistory votingHistory = crudRepository.findByUserIdAndDate(newVoice.getUserId(),
                newVoice.getDate());
        if (votingHistory != null) {
            newVoice.setId(votingHistory.id());
            crudRepository.save(newVoice);
        } else {
            crudRepository.save(newVoice);
        }
        return newVoice;
    }

    @Override
    public Map<Date, String> getUserVotingHistory(int userId) {
        TreeMap<Date, String> treeMap = new TreeMap<>(Comparator.reverseOrder());
        Map<Date, String> map = getHistoryMap(crudRepository.getUserHistory(userId)).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue().get(0)));
        treeMap.putAll(map);
        return treeMap;
    }

    @Override
    public Map<Date, List<String>> getRestaurantVotingHistory(int restaurantId) {
        return getHistoryMap(crudRepository.getRestaurantHistory(restaurantId));
    }

    private Map<Date, List<String>> getHistoryMap(List<Object> list) {
        Map<Date, List<String>> map = new TreeMap<>(Comparator.reverseOrder());
        for (Object obj : list) {
            Object[] objects = (Object[]) obj;
            Date date = (Date) objects[0];
            String string = (String) objects[1];
            List<String> strings = map.getOrDefault(date, new ArrayList<>());
            strings.add(string);
            map.put(date, strings);
        }
        return map;
    }
}