package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Etudiant;
import java.util.Date;
import java.util.List;


public interface IEtudiantService {

    public List<Etudiant> retrieveAllEtudiants();
    public Etudiant retrieveEtudiant(Long etudiantId);
    public Etudiant addEtudiant(Etudiant c);
    public void removeEtudiant(Long etudiantId);
    public Etudiant modifyEtudiant(Etudiant etudiant);
    List<Etudiant> getEtudiantsAvecReservationValidePourAnneeDonnee(int annee);
    String inscrireNouvelEtudiant(String nomEt, String prenomEt, long cin, Date dateNaissance);
    Etudiant updateEmailEtudiant(Long etudiantId, String nouvelEmail);
    int getNombreReservationsParCin(long cin);
}
