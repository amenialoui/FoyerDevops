package tn.esprit.tpfoyer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Foyer;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {

    // Find a Foyer by its name
    Optional<Foyer> findByNomFoyer(String nomFoyer);

    // Example: Find all Foyers with a capacity greater than a specified value
    List<Foyer> findByCapaciteFoyerGreaterThan(Long capacite);

    // Example: Delete a Foyer by its name (if needed)
    void deleteByNomFoyer(String nomFoyer);

    // You can add more custom methods as needed
}
