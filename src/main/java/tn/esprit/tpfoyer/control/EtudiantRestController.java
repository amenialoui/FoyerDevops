package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.sql.Date;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/etudiant")
public class EtudiantRestController {

    IEtudiantService etudiantService;


    @GetMapping("/retrieve-all-etudiants")
    public List<Etudiant> getEtudiants() {
        return etudiantService.retrieveAllEtudiants();
    }



    @GetMapping("/retrieve-etudiant/{etudiant-id}")
    public Etudiant retrieveEtudiant(@PathVariable("etudiant-id") Long chId) {
        return etudiantService.retrieveEtudiant(chId);
    }

    @PostMapping("/add-etudiant")
    public Etudiant addEtudiant(@RequestBody Etudiant c) {
        return etudiantService.addEtudiant(c);
    }

    @DeleteMapping("/remove-etudiant/{etudiant-id}")
    public void removeEtudiant(@PathVariable("etudiant-id") Long chId) {
        etudiantService.removeEtudiant(chId);
    }

    @PutMapping("/modify-etudiant")
    public Etudiant modifyEtudiant(@RequestBody Etudiant c) {
        return etudiantService.modifyEtudiant(c);
    }

    @PutMapping("/modify-etudiant/email")
    public Etudiant modifyEtudiantEmail(@RequestParam("etudiant-id") Long etudiantId,
                                        @RequestParam("nouvel-email") String nouvelEmail) {
        return etudiantService.updateEmailEtudiant(etudiantId, nouvelEmail);
    }
    @GetMapping("/nombre-reservations/{cin}")
    public int getNombreReservationsParCin(@PathVariable("cin") long cin) {
        return etudiantService.getNombreReservationsParCin(cin);
    }
    @GetMapping("/retrieve-etudiants-avec-reservations-valides/{annee}")
    public List<Etudiant> getEtudiantsAvecReservationValidePourAnneeDonnee(@PathVariable("annee") int annee) {
        return etudiantService.getEtudiantsAvecReservationValidePourAnneeDonnee(annee);
    }
    @PostMapping("/inscrire-nouvel-etudiant")
    public String inscrireNouvelEtudiant(@RequestParam("nomEt") String nomEt,
                                         @RequestParam("prenomEt") String prenomEt,
                                         @RequestParam("cin") long cin,
                                         @RequestParam("dateNaissance") String dateNaissance) {

        return etudiantService.inscrireNouvelEtudiant(nomEt, prenomEt, cin, Date.valueOf(dateNaissance));
    }

}
