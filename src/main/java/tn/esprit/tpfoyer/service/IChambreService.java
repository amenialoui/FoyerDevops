package tn.esprit.tpfoyer.service;

import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IChambreService {

    public List<Chambre> retrieveAllChambres();
    public Chambre retrieveChambre(Long chambreId);
    public Chambre addChambre(Chambre c);
    public void removeChambre(Long chambreId);
    public Chambre modifyChambre(Chambre chambre);

    // Here we will add later methods calling keywords and methods calling JPQL
    public Optional<Chambre> trouverchambreSelonEtudiant(long Cin);
    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc);
    public boolean isChambreDisponible(long idChambre, Date dateDebut);



}
