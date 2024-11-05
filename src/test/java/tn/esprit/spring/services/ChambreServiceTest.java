package tn.esprit.spring.services;

import org.aspectj.lang.annotation.Before;
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
////////////////////////////////////////////////////////////////////////////////
@Test
public void testTrouverChambreSelonEtudiant() {
    long cinValide = 123456789; // Remplacez par un CIN valide
    Optional<Chambre> optionalChambre = chambreService.trouverchambreSelonEtudiant(cinValide);

    // Vérifier si la chambre est présente
    if (optionalChambre.isPresent()) {
        Chambre chambre = optionalChambre.get(); // Récupérer la chambre à l'intérieur de l'Optional
        // Ajoutez vos assertions ici pour vérifier les propriétés de la chambre
        assertNotNull(chambre);
        // Par exemple, assertEquals(expectedId, chambre.getId());
    } else {
        // Gérer le cas où la chambre n'est pas trouvée
        fail("La chambre n'a pas été trouvée pour le CIN: " + cinValide);
    }
}


    @Test
    public void testTrouverChambreSelonEtudiant_CINExist() {
        long cin = 12345678L;
        Chambre chambre = new Chambre();
        chambre.setIdChambre(1L); // Ajoutez un ID pour vérification
        when(chambreRepository.trouverChselonEt(cin)).thenReturn(chambre);

        Optional<Chambre> result = chambreService.trouverchambreSelonEtudiant(cin);

        assertTrue(result.isPresent(), "La chambre doit être présente pour un CIN valide");
        assertEquals(1L, result.get().getIdChambre(), "L'ID de la chambre retournée doit correspondre à l'ID attendu");
        verify(chambreRepository, times(1)).trouverChselonEt(cin);
    }


    @Test
    void testTrouverchambreSelonEtudiant_CINNotExist() {
        long cinInexistant = 123456789;

        // Configurez le mock pour retourner null
        when(chambreRepository.trouverChselonEt(cinInexistant)).thenReturn(null);

        // Exécutez la méthode
        Optional<Chambre> resultat = chambreService.trouverchambreSelonEtudiant(cinInexistant);

        // Vérifiez que le résultat est un Optional vide
        assertTrue(resultat.isEmpty());
    }

    @Test
    void testChambreNonDisponible() {
        long cinInvalide = -1;

        // Attendez une IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            chambreService.trouverchambreSelonEtudiant(cinInvalide);
        });
    }


    @Test
    public void testTrouverchambreSelonEtudiant_CINInvalid() {
        // Préparation des données simulées pour un CIN non valide
        long invalidCin = -123456L;

        // Appel de la méthode à tester avec un CIN invalide
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            chambreService.trouverchambreSelonEtudiant(invalidCin);
        });

        // Vérification de l'exception levée
        assertEquals("CIN invalide", exception.getMessage());
    }
    @Test
    public void testTrouverchambreSelonEtudiant_CINZero() {
        // Tester avec un CIN égal à zéro
        long zeroCin = 0L;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            chambreService.trouverchambreSelonEtudiant(zeroCin);
        });

        assertEquals("CIN invalide", exception.getMessage());
    }

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











}
