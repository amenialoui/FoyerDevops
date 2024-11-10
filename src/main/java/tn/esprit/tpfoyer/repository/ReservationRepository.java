package tn.esprit.tpfoyer.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String>
{



    List<Reservation> findAllByAnneeUniversitaireBeforeAndEstValide(Date d, boolean b );

    List<Reservation> findByEtudiantsContains(Etudiant etudiant);
}



