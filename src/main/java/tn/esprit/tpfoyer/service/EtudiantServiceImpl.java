package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    EtudiantRepository etudiantRepository;
    UniversiteRepository universiteRepository;
    ReservationRepository reservationRepository;



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

    public List<Etudiant> findEtudiantsByUniversite(long idUniversite) {
        Optional<Universite> universiteOpt = universiteRepository.findById(idUniversite);

        if (universiteOpt.isPresent()) {
            return etudiantRepository.findEtudiantsByUniversite(idUniversite);
        }
        return Collections.emptyList(); // Retourner une liste vide si l'université n'existe pas
    }




    public String inscrireNouvelEtudiant(String nomEt, String prenomEt, long cin, Date dateNaissance) {
        // Vérifier si l'étudiant est déjà inscrit par son CIN
        if (etudiantRepository.existsByCinEtudiant(cin)) {
            throw new IllegalArgumentException("Un étudiant avec ce CIN est déjà inscrit.");
        }

        // Vérifier l'âge de l'étudiant (doit être supérieur ou égal à 18 ans)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        Date ageLimite = calendar.getTime();

        if (dateNaissance.after(ageLimite)) {
            throw new IllegalArgumentException("L'étudiant doit avoir au moins 18 ans.");
        }

        // Créer l'objet étudiant et l'enregistrer
        Etudiant etudiant = new Etudiant();
        etudiant.setNomEtudiant(nomEt);
        etudiant.setPrenomEtudiant(prenomEt);
        etudiant.setCinEtudiant(cin);
        etudiant.setDateNaissance(dateNaissance);

        etudiantRepository.save(etudiant);

        return "Inscription réussie pour l'étudiant " + nomEt + " " + prenomEt;
    }






}
