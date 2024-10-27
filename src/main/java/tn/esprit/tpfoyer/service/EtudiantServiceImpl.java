package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {


    EtudiantRepository etudiantRepository;

    public List<Etudiant> retrieveAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant retrieveEtudiant(Long etudiantId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(etudiantId);

        if (optionalEtudiant.isPresent()) {
            return optionalEtudiant.get();  // Récupérer l'étudiant s'il est présent
        } else {
            throw new IllegalArgumentException("Etudiant non trouvé avec l'ID: " + etudiantId);  // Gestion de l'absence de l'étudiant
        }
    }

    public Etudiant addEtudiant(Etudiant c) {
        return etudiantRepository.save(c);
    }
    public Etudiant modifyEtudiant(Etudiant c) {
        return etudiantRepository.save(c);
    }
    public void removeEtudiant(Long etudiantId) {
        etudiantRepository.deleteById(etudiantId);
    }
    public Etudiant recupererEtudiantParCin(long cin)
    {
        return etudiantRepository.findEtudiantByCinEtudiant(cin);
    }


    public List<Etudiant> getEtudiantsAvecReservationValidePourAnneeDonnee(int annee) {
        List<Etudiant> etudiants = etudiantRepository.findAll();

        List<Etudiant> result = new ArrayList<>();

        for (Etudiant etudiant : etudiants) {
            for (Reservation reservation : etudiant.getReservations()) {
                // Convertir Date en LocalDate
                LocalDate anneeUniversitaire = new java.sql.Date(reservation.getAnneeUniversitaire().getTime()).toLocalDate();

                if (reservation.isEstValide() && anneeUniversitaire.getYear() == annee) {
                    result.add(etudiant);
                    break; // Ajoutez l'étudiant une seule fois
                }
            }
        }

        return result;
    }


}
