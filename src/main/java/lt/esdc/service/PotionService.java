package lt.esdc.service;

import lt.esdc.exception.EmptyCauldronException;
import lt.esdc.exception.PotionAlreadyExistsException;
import lt.esdc.exception.PotionNotFoundException;
import lt.esdc.exception.NotYourPotionException;
import lt.esdc.exception.RandomExplosionException;
import lt.esdc.model.Potion;
import lt.esdc.repository.PotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PotionService {

    private final PotionRepository repository;

    public PotionService(PotionRepository repository) {
        this.repository = repository;
    }

    public List<Potion> getAllPotions() {
        // Requirement: 500 error if DB is empty when getting the list
        if (repository.isEmpty()) {
            throw new EmptyCauldronException("The cauldron is empty! No potions found.");
        }
        return repository.findAll();
    }

    public Potion getPotionByCode(String code) {
        return repository.findById(code)
                .orElseThrow(() -> new PotionNotFoundException("Potion with code " + code + " not found."));
    }

    public Potion createPotion(Potion potion) {
        // Requirement: 400 error when trying to create an already existing object
        if (repository.existsById(potion.getCode())) {
            throw new PotionAlreadyExistsException("Potion with code " + potion.getCode() + " already exists!");
        }
        return repository.save(potion);
    }

    public Potion updatePotion(String code, Potion potion, String alchemistId) {
        Potion existing = repository.findById(code)
                .orElseThrow(() -> new PotionNotFoundException("Potion with code " + code + " not found."));

        if (!alchemistId.equals(existing.getAlchemistId())) {
            throw new NotYourPotionException("You cannot modify a potion that belongs to another alchemist!");
        }

        potion.setCode(code); // Ensure the code matches the path variable
        potion.setAlchemistId(alchemistId); // Prevent erasure of the alchemist ID
        return repository.save(potion);
    }

    public void deletePotion(String code, String alchemistId) {
        Potion existing = repository.findById(code)
                .orElseThrow(() -> new PotionNotFoundException("Cannot delete: Potion with code " + code + " not found."));

        if (!alchemistId.equals(existing.getAlchemistId())) {
            throw new NotYourPotionException("You cannot delete a potion that belongs to another alchemist!");
        }
        
        // Requirement: 50% chance to throw "Random delete error"
        if (Math.random() < 0.5) {
            throw new RandomExplosionException("Random deletion error: The potion exploded in your hands!");
        }
        repository.deleteById(code);
    }
}