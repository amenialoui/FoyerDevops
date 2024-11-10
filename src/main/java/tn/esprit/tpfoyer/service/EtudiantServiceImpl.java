package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
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
        List<Etudiant> etudiants = etudiantRepository.findAll();
        if (etudiants.isEmpty()) {
            throw new IllegalArgumentException("Aucun étudiant trouvé.");
        }
        return etudiants;
    }


    public Etudiant retrieveEtudiant(Long etudiantId) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(etudiantId);

        if (optionalEtudiant.isPresent()) {
            return optionalEtudiant.get();  // Récupérer l'étudiant s'il est présent
        } else {
            throw new IllegalArgumentException("Etudiant non trouvé avec l'ID: " + etudiantId);  // Gestion de l'absence de l'étudiant
        }
    }

    public void removeEtudiant(Long etudiantId) {
        if (etudiantId == null || etudiantId <= 0) {
            throw new IllegalArgumentException("L'ID de l'étudiant doit être un nombre positif valide.");
        }

        // Vérification si l'étudiant existe dans la base de données
        Optional<Etudiant> etudiant = etudiantRepository.findById(etudiantId);
        if (etudiant.isPresent()) {
            etudiantRepository.deleteById(etudiantId);  // Suppression de l'étudiant
        } else {
            throw new IllegalArgumentException("Aucun étudiant trouvé avec l'ID: " + etudiantId);
        }
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




    public Etudiant addEtudiant(Etudiant c) {
        // Vérifier si l'étudiant est déjà inscrit par son CIN
        if (etudiantRepository.existsByCinEtudiant(c.getIdEtudiant())) {
            throw new IllegalArgumentException("Un étudiant avec ce CIN est déjà inscrit.");
        }

        // Vérifier l'âge de l'étudiant (doit être supérieur ou égal à 18 ans)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        Date ageLimite = calendar.getTime();

        if (c.getDateNaissance().after(ageLimite)) {
            throw new IllegalArgumentException("L'étudiant doit avoir au moins 18 ans.");
        }

        return etudiantRepository.save(c);
    }



    public int getNombreReservationsParCin(long cin) {
        Etudiant etudiant = etudiantRepository.findEtudiantByCinEtudiant(cin);

        if (etudiant == null) {
            throw new IllegalArgumentException("Aucun étudiant trouvé avec le CIN: " + cin);
        }

        return etudiant.getReservations() != null ? etudiant.getReservations().size() : 0;
    }


    public Etudiant modifyEtudiant(long cin, String nom, String prenom, String email) {
        // Rechercher l'étudiant dans la base de données par CIN
        Etudiant existingEtudiant = etudiantRepository.findEtudiantByCinEtudiant(cin);

        if (existingEtudiant == null) {
            throw new IllegalArgumentException("Aucun étudiant trouvé avec le CIN: " + cin);
        }

        // Mettre à jour les champs spécifiques de l'étudiant si les valeurs ne sont pas nulles
        if (nom != null) {
            existingEtudiant.setNomEtudiant(nom);
        }
        if (prenom != null) {
            existingEtudiant.setPrenomEtudiant(prenom);
        }
        if (email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            existingEtudiant.setEmail(email);
        }

        // Sauvegarder les modifications dans la base de données
        return etudiantRepository.save(existingEtudiant);
    }














}
