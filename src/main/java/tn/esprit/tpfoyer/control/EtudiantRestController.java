package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;
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

    @PutMapping("/modify-etudiant/{etudiant-id}")
    public Etudiant modifyEtudiant(@PathVariable("etudiant-id")Long cin,@RequestParam("etudiant-nom")String nom,
                                   @RequestParam("nouvel-email")String prenom,
                                   @RequestParam("etudiant-prenom")String email){
        return etudiantService.modifyEtudiant(cin, nom, prenom, email);
    }

    @GetMapping("/nombre-reservations/{cin}")
    public int getNombreReservationsParCin(@PathVariable("cin") long cin) {
        return etudiantService.getNombreReservationsParCin(cin);
    }
    @GetMapping("/retrieve-etudiants-avec-reservations-valides/{annee}")
    public List<Etudiant> getEtudiantsAvecReservationValidePourAnneeDonnee(@PathVariable("annee") int annee) {
        return etudiantService.getEtudiantsAvecReservationValidePourAnneeDonnee(annee);
    }



}
