package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {

    UniversiteRepository universiteRepository;

    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    public Universite retrieveUniversite(Long universiteId) {
        Optional<Universite> universiteOptional = universiteRepository.findById(universiteId);
        if (universiteOptional.isPresent()) {
            return universiteOptional.get();  // Accès sécurisé
        } else {
            throw new IllegalArgumentException("Université non trouvée avec l'ID: " + universiteId);
        }
    }


    public Universite addUniversite(Universite u) {
        return universiteRepository.save(u);
    }

    public Universite modifyUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    public void removeUniversite(Long universiteId) {
        universiteRepository.deleteById(universiteId);
    }
}
