package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/chambre")
public class ChambreRestController {

    IChambreService chambreService;


     @GetMapping("/retrieve-all-chambres")
    public List<Chambre> getChambres() {
        return chambreService.retrieveAllChambres();
    }



    @PostMapping("/add-chambre")
    public Chambre addChambre(@RequestBody Chambre c) {
        return chambreService.addChambre(c);
    }


    @DeleteMapping("/remove-chambre/{chambre-id}")
    public void removeChambre(@PathVariable("chambre-id") Long chId) {
        chambreService.removeChambre(chId);
    }


    @PutMapping("/modify-chambre")
    public Chambre modifyChambre(@RequestBody Chambre c) {
        return chambreService.modifyChambre(c);
    }


    @GetMapping("/trouver-chambres-selon-typ/{tc}")
    public List<Chambre> trouverChSelonTC(@PathVariable("tc") TypeChambre tc)
    {
        return chambreService.recupererChambresSelonTyp(tc);
    }

















    // http://localhost:8089/tpfoyer/chambre/retrieve-chambre/8
    @GetMapping("/trouver-chambre-selon-etudiant/{cin}")
    public Chambre trouverChSelonEt(@PathVariable("cin") long cin) {
        return chambreService.trouverchambreSelonEtudiant(cin);
    }


}
