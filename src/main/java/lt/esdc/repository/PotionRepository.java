package lt.esdc.repository;

import lt.esdc.model.Potion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PotionRepository {

    // Requirement: Use List or Map to store data in the repository
    private final Map<String, Potion> database = new ConcurrentHashMap<>();

    public List<Potion> findAll() {
        return new ArrayList<>(database.values());
    }

    public Optional<Potion> findById(String code) {
        return Optional.ofNullable(database.get(code));
    }

    public Potion save(Potion potion) {
        database.put(potion.getCode(), potion);
        return potion;
    }

    public void deleteById(String code) {
        database.remove(code);
    }

    public boolean existsById(String code) {
        return database.containsKey(code);
    }

    public boolean isEmpty() {
        return database.isEmpty();
    }
}