package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Etudiant;
import java.util.List;


public interface IEtudiantService {

    public List<Etudiant> retrieveAllEtudiants();
    public Etudiant retrieveEtudiant(Long etudiantId);
    public void removeEtudiant(Long etudiantId);
    public Etudiant modifyEtudiant(long cin, String nom, String prenom, String email);
    List<Etudiant> getEtudiantsAvecReservationValidePourAnneeDonnee(int annee);
    int getNombreReservationsParCin(long cin);
    Etudiant addEtudiant(Etudiant c);
}
