package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservation = new Reservation();
        reservation.setIdReservation("123");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        // Initialize other fields if necessary
    }

    @Test
    void retrieveAllReservations() {
        // Given
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        // When
        List<Reservation> reservations = reservationService.retrieveAllReservations();

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    void retrieveReservation() {
        // Given
        when(reservationRepository.findById("123")).thenReturn(Optional.of(reservation));

        // When
        Reservation retrievedReservation = reservationService.retrieveReservation("123");

        // Then
        assertNotNull(retrievedReservation);
        assertEquals(reservation.getIdReservation(), retrievedReservation.getIdReservation());
    }

    @Test
    void addReservation() {
        // Given
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // When
        Reservation savedReservation = reservationService.addReservation(reservation);

        // Then
        assertNotNull(savedReservation);
        assertEquals(reservation.getIdReservation(), savedReservation.getIdReservation());
    }

    @Test
    void modifyReservation() {
        // Given
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // When
        Reservation updatedReservation = reservationService.modifyReservation(reservation);

        // Then
        assertNotNull(updatedReservation);
        assertEquals(reservation.getIdReservation(), updatedReservation.getIdReservation());
    }

    @Test
    void trouverResSelonDateEtStatus() {
        // Given
        Date date = new Date();
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, true))
                .thenReturn(List.of(reservation));

        // When
        List<Reservation> reservations = reservationService.trouverResSelonDateEtStatus(date, true);

        // Then
        assertNotNull(reservations);
        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    void removeReservation() {
        // Given
        doNothing().when(reservationRepository).deleteById("123");

        // When
        reservationService.removeReservation("123");

        // Then
        verify(reservationRepository, times(1)).deleteById("123");
    }
}
