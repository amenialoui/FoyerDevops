package tn.esprit.tpfoyer.control;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;

import tn.esprit.tpfoyer.service.IReservationService;


@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRestController {

    IReservationService reservationService;



}
