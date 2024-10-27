package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerServiceImplMockitoTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFoyer() {
        // Données de test
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Mocked Foyer");

        // Configuration de la méthode simulée
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Exécution du test
        Foyer result = foyerService.addFoyer(foyer);

        // Vérifications
        assertNotNull(result);
        assertEquals("Mocked Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRetrieveFoyer() {
        // Données de test
        Long foyerId = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(foyerId);
        foyer.setNomFoyer("Mocked Retrieve Foyer");

        // Configuration de la méthode simulée
        when(foyerRepository.findById(foyerId)).thenReturn(Optional.of(foyer));

        // Exécution du test
        Foyer result = foyerService.retrieveFoyer(foyerId);

        // Vérifications
        assertNotNull(result);
        assertEquals(foyerId, result.getIdFoyer());
        assertEquals("Mocked Retrieve Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(foyerId);
    }

    @Test
    void testRemoveFoyer() {
        // Données de test
        Long foyerId = 1L;

        // Exécution du test
        foyerService.removeFoyer(foyerId);

        // Vérifications
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
