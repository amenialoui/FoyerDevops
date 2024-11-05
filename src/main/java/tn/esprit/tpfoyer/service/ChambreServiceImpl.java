package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ChambreServiceImpl implements IChambreService {

    ChambreRepository chambreRepository;

    @Autowired
    public ChambreServiceImpl(ChambreRepository chambreRepository, ReservationRepository reservationRepository) {
        this.chambreRepository = chambreRepository;
        this.reservationRepository = reservationRepository;
    }
    public List<Chambre> retrieveAllChambres() {
        log.info("In Methodo retrieveAllChambres : ");
        List<Chambre> listC = chambreRepository.findAll();
        log.info("Out of retrieveAllChambres : ");

        return listC;
    }

    public Chambre retrieveChambre(Long chambreId) {
        Chambre c = chambreRepository.findById(chambreId).get();
        return c;
    }

    public Chambre addChambre(Chambre c) {
        Chambre chambre = chambreRepository.save(c);
        return chambre;
    }

    public Chambre modifyChambre(Chambre c) {
        Chambre chambre = chambreRepository.save(c);
        return c;
    }



    public void removeChambre(Long chambreId) {
        chambreRepository.deleteById(chambreId);
    }







    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc)
    {
        return chambreRepository.findAllByTypeC(tc);
    }



    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Vérifie la disponibilité d'une chambre pour une période donnée.
     *
     * @param idChambre       L'ID de la chambre à vérifier.
           La date de fin de la réservation.
     * @return true si la chambre est disponible, false sinon.
     */
    public boolean isChambreDisponible(long idChambre, Date dateCheck) {
        Chambre chambre = chambreRepository.findById(idChambre).orElse(null);

        if (chambre == null) {
            throw new IllegalArgumentException("Chambre introuvable avec l'ID : " + idChambre);
        }

        List<Reservation> reservations = ReservationRepository.findByChambreIdChambre(idChambre);
        System.out.println("Nombre de réservations récupérées : " + reservations.size());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCheck);
        int checkYear = calendar.get(Calendar.YEAR);

        for (Reservation reservation : reservations) {
            calendar.setTime(reservation.getAnneeUniversitaire());
            int reservationYear = calendar.get(Calendar.YEAR);
            System.out.println("Année de la réservation : " + reservationYear + ", Année de vérification : " + checkYear);

            if (reservationYear == checkYear) {
                System.out.println("La chambre est réservée pour l'année universitaire " + checkYear);
                return false;
            }
        }

        System.out.println("La chambre est disponible pour l'année universitaire " + checkYear);
        return true;
    }



















    public Chambre trouverchambreSelonEtudiant(long cin) {
        // Vérification si le CIN est invalide
        if (cin <= 0) {
            throw new IllegalArgumentException("Le CIN doit être un nombre positif.");
        }

        // Appeler la méthode du repository pour trouver la chambre
        return chambreRepository.trouverChselonEt(cin);
    }

}
