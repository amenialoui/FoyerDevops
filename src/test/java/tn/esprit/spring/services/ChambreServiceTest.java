package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ChambreServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChambreServiceTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllChambres() {
        // Préparation des données simulées
        Chambre chambre1 = new Chambre();
        Chambre chambre2 = new Chambre();
        List<Chambre> chambres = Arrays.asList(chambre1, chambre2);

        // Simulation du comportement du repository
        when(chambreRepository.findAll()).thenReturn(chambres);

        // Appel de la méthode à tester
        List<Chambre> result = chambreService.retrieveAllChambres();

        // Vérification des résultats
        assertEquals(2, result.size());
        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    public void testRetrieveChambre() {
        // Préparation des données simulées
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L);

        // Simulation du comportement du repository
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        // Appel de la méthode à tester
        Chambre result = chambreService.retrieveChambre(1L);

        // Vérification des résultats
        assertNotNull(result);
        assertEquals(1L, result.getIdChambre());
        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    public void testAddChambre() {
        // Préparation des données simulées
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre result = chambreService.addChambre(chambre);

        // Vérification des résultats
        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testModifyChambre() {
        // Préparation des données simulées
        Chambre chambre = new Chambre();
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre result = chambreService.modifyChambre(chambre);

        // Vérification des résultats
        assertNotNull(result);
        verify(chambreRepository, times(1)).save(chambre);
    }

    @Test
    public void testRemoveChambre() {
        // Appel de la méthode à tester
        chambreService.removeChambre(1L);

        // Vérification que le repository a été appelé avec le bon ID
        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testRecupererChambresSelonTyp() {
        // Préparation des données simulées
        TypeChambre typeChambre = TypeChambre.SIMPLE;
        Chambre chambre1 = new Chambre();
        chambre1.setTypeC(typeChambre);
        List<Chambre> chambres = Arrays.asList(chambre1);

        // Simulation du comportement du repository
        when(chambreRepository.findAllByTypeC(typeChambre)).thenReturn(chambres);

        // Appel de la méthode à tester
        List<Chambre> result = chambreService.recupererChambresSelonTyp(typeChambre);

        // Vérification des résultats
        assertEquals(1, result.size());
        verify(chambreRepository, times(1)).findAllByTypeC(typeChambre);
    }

    @Test
    public void testTrouverchambreSelonEtudiant() {
        // Préparation des données simulées
        long cin = 12345678L;
        Chambre chambre = new Chambre();
        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        // Appel de la méthode à tester
        Chambre result = chambreService.trouverchambreSelonEtudiant(cin);

        // Vérification des résultats
        assertNotNull(result);
        verify(chambreRepository, times(1)).trouverChselonEt(cin);}

/////////////////////////////////////////////////////////////////////////////////////

        @Test
        public void testChambreIntrouvable() {
            // Arrange
            long idChambre = 1L;
            Date dateCheck = new Date();
            when(chambreRepository.findById(idChambre)).thenReturn(Optional.empty());

            // Act & Assert
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                chambreService.isChambreDisponible(idChambre, dateCheck);
            });

            assertEquals("Chambre introuvable avec l'ID : " + idChambre, exception.getMessage());
        }

    @BeforeEach
    public void setUp() {
        // Initialiser les mocks
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testChambreNonDisponible() {
        // Arrange
        long idChambre = 1L;
        Date dateCheck = new GregorianCalendar(2024, Calendar.NOVEMBER, 4).getTime();

        Chambre chambre1 = new Chambre();
        chambre1.setIdChambre(idChambre);

        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(dateCheck);  // Date de réservation correspondant à dateCheck

        when(chambreRepository.findById(idChambre)).thenReturn(Optional.of(chambre1));
        // Correction ici : utiliser l'instance mockée reservationRepository
        when(ReservationRepository.findByChambreIdChambre(idChambre)).thenReturn(Arrays.asList(reservation));

        // Act
        boolean result = chambreService.isChambreDisponible(idChambre, dateCheck);

        // Assert
        assertFalse(result);
    }
    @Test
    public void testIsChambreDisponible_whenNoReservations() {
        // Arrange
        long idChambre = 1L;
        Date dateCheck = new Date();

        Chambre chambre = new Chambre();
        chambre.setIdChambre(idChambre);

        // Stubber les appels sur le mock
        when(chambreRepository.findById(idChambre)).thenReturn(Optional.of(chambre));
        // Assurez-vous d'utiliser reservationRepository ici (notez la casse)
        when(ReservationRepository.findByChambreIdChambre(idChambre)).thenReturn(Arrays.asList());

        // Act
        boolean result = chambreService.isChambreDisponible(idChambre, dateCheck);

        // Assert
        assertTrue(result); // La chambre doit être disponible
    }



}
