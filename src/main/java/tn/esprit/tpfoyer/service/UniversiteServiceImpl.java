package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements IUniversiteService {

    UniversiteRepository universiteRepository;
    FoyerRepository foyerRepository;

    public List<Universite> retrieveAllUniversites() {
        return universiteRepository.findAll();
    }

    public Universite retrieveUniversite(Long universiteId) {
        return universiteRepository.findById(universiteId).get();
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
    public List<Universite> getAllUniversities() {
        return universiteRepository.findAll();
    }
    @Override
    public Universite affecterFoyerAUniversite(long idFoyer, String nomUniversite) {
        Foyer foyer = foyerRepository.findById(idFoyer)
                .orElseThrow(() -> new RuntimeException("Foyer non trouvé"));

        Universite universite = universiteRepository.findFirstByNomUniversite(nomUniversite);
        if (universite == null) {
            throw new RuntimeException("Université non trouvée");
        }

        foyer.setUniversite(universite);
        foyerRepository.save(foyer);  // Associer et sauvegarder le foyer avec l'université

        return universite;
    }
    public Universite desaffecterFoyerAUniversite(long idUniversite) {

        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université non trouvée"));

        Foyer foyer = universite.getFoyer();

        if(foyer != null) {

            foyer.setUniversite(null);
            universite.setFoyer(null);

            foyerRepository.save(foyer);
        }

        return universite;
    }

}
