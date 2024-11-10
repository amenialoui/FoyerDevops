package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.repository.ReservationRepository;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    ReservationRepository reservationRepository;


}
