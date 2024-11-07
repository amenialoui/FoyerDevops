package tn.esprit.tpfoyer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes = FoyerServiceImplMockitoTest.class)
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
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
        // Test data
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("Mocked Foyer");

        // Mocking the repository method
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Executing the service method
        Foyer result = foyerService.addFoyer(foyer);

        // Assertions
        assertNotNull(result);
        assertEquals("Mocked Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRetrieveFoyer() {
        // Test data
        Long foyerId = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(foyerId);
        foyer.setNomFoyer("Mocked Retrieve Foyer");

        // Mocking the repository method
        when(foyerRepository.findById(foyerId)).thenReturn(Optional.of(foyer));

        // Executing the service method
        Foyer result = foyerService.retrieveFoyer(foyerId);

        // Assertions
        assertNotNull(result);
        assertEquals(foyerId, result.getIdFoyer());
        assertEquals("Mocked Retrieve Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).findById(foyerId);
    }

    @Test
    void testRetrieveFoyerNotFound() {
        // Test data
        Long foyerId = 999L;

        // Mocking the repository method to return an empty Optional
        when(foyerRepository.findById(foyerId)).thenReturn(Optional.empty());

        // Executing the service method and asserting the exception
        Exception exception = assertThrows(IllegalArgumentException.class, () -> foyerService.retrieveFoyer(foyerId));

        assertEquals("Foyer not found with ID: " + foyerId, exception.getMessage());
        verify(foyerRepository, times(1)).findById(foyerId);
    }

    @Test
    void testModifyFoyer() {
        // Test data
        Long foyerId = 1L;
        Foyer foyer = new Foyer();
        foyer.setIdFoyer(foyerId);
        foyer.setNomFoyer("Modified Foyer");

        // Mocking the repository method
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Executing the service method
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assertions
        assertNotNull(result);
        assertEquals("Modified Foyer", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRemoveFoyer() {
        // Test data
        Long foyerId = 1L;

        // Executing the service method
        foyerService.removeFoyer(foyerId);

        // Verifications
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}
