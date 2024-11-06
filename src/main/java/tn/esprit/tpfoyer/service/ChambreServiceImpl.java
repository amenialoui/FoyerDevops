package tn.esprit.tpfoyer.service;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
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
import java.util.Optional;
import java.util.logging.Logger;

@Service
@Slf4j
public class ChambreServiceImpl implements IChambreService {

    ChambreRepository chambreRepository;
    // Créer un logger pour la classe
    private static final Logger logger = (Logger) LoggerFactory.getLogger(ChambreServiceImpl.class);

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
        return chambreRepository.findById(chambreId).orElse(null);
    }


    public Chambre addChambre(Chambre c) {
        return chambreRepository.save(c);
    }

    public Chambre modifyChambre(Chambre c) {
        return chambreRepository.save(c);
    }



    public void removeChambre(Long chambreId) {
        chambreRepository.deleteById(chambreId);
    }







    public List<Chambre> recupererChambresSelonTyp(TypeChambre tc)
    {
        return chambreRepository.findAllByTypeC(tc);
    }



    final ReservationRepository reservationRepository;

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
        logger.info("Nombre de réservations récupérées :" + reservations.size());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateCheck);
        int checkYear = calendar.get(Calendar.YEAR);

        for (Reservation reservation : reservations) {
            calendar.setTime(reservation.getAnneeUniversitaire());
            int reservationYear = calendar.get(Calendar.YEAR);
            logger.info("Année de la réservation : " + reservationYear + ", Année de vérification : " + checkYear);

            if (reservationYear == checkYear) {
               logger.info("La chambre est réservée pour l'année universitaire " + checkYear);
                return false;
            }
        }

        logger.info("La chambre est disponible pour l'année universitaire " + checkYear);
        return true;
    }



















    public Optional<Chambre> trouverchambreSelonEtudiant(long cin) {
        // Vérification si le CIN est invalide
        if (cin <= 0) {
            throw new IllegalArgumentException("CIN invalide");
        }

        try {
            // Appeler la méthode du repository pour trouver la chambre
            Optional<Chambre> chambre = Optional.ofNullable(chambreRepository.trouverChselonEt(cin));

            // Vérifier si la chambre est présente, sinon retourner un Optional vide
            if (chambre.isEmpty()) {
                // Logique pour le cas où le CIN n'existe pas dans la base de données
                logger.info("La chambre n'a pas été trouvée pour le CIN: " + cin);
                return Optional.empty(); // Retourner un Optional vide si la chambre n'existe pas
            }

            return chambre; // Retourner la chambre trouvée

        } catch (Exception e) {
            // Gérer l'exception et éventuellement logguer l'erreur
            logger.info("Erreur lors de la recherche de la chambre : " + e.getMessage());
            return Optional.empty(); // Retourner un Optional vide en cas d'erreur
        }
    }




}
