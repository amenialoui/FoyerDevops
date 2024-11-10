package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
@Service
@AllArgsConstructor
@Slf4j
public class ChambreServiceImpl implements IChambreService {

    ChambreRepository chambreRepository;



























    public Chambre trouverchambreSelonEtudiant(long cin) {
       //

        return chambreRepository.trouverChselonEt(cin);
    }
}
