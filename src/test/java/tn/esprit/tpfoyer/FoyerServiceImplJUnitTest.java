package tn.esprit.tpfoyer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class FoyerServiceImplJUnitTest {

    @Autowired
    private FoyerServiceImpl foyerService;

    @Autowired
    private FoyerRepository foyerRepository;

    @BeforeEach
    public void setUp() {
        foyerRepository.deleteAll(); // Clean up the repository before each test
    }

    @Test
    void testRetrieveAllFoyers() {
        // Prepare test data
        Foyer foyer1 = new Foyer();
        foyer1.setNomFoyer("Foyer1");
        foyer1.setCapaciteFoyer(100L);

        Foyer foyer2 = new Foyer();
        foyer2.setNomFoyer("Foyer2");
        foyer2.setCapaciteFoyer(200L);

        // Save data to repository
        foyerRepository.save(foyer1);
        foyerRepository.save(foyer2);

        // Call the service method
        List<Foyer> foyers = foyerService.retrieveAllFoyers();
        assertEquals(2, foyers.size());
    }

    @Test
    void testRetrieveFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("FoyerTest");
        foyer.setCapaciteFoyer(150L);
        Foyer savedFoyer = foyerRepository.save(foyer);

        Foyer result = foyerService.retrieveFoyer(savedFoyer.getIdFoyer());
        assertEquals(savedFoyer.getIdFoyer(), result.getIdFoyer());
        assertEquals("FoyerTest", result.getNomFoyer());
    }

    @Test
    void testRetrieveFoyer_notFound() {
        Long invalidId = 999L; // An ID that doesn't exist

        // Assert that the method throws an exception for an invalid ID
        assertThrows(IllegalArgumentException.class, () -> foyerService.retrieveFoyer(invalidId));
    }

    @Test
    void testAddFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("NewFoyer");
        foyer.setCapaciteFoyer(300L);
        Foyer result = foyerService.addFoyer(foyer);
        assertTrue(foyerRepository.findById(result.getIdFoyer()).isPresent());
    }

    @Test
    void testModifyFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("InitialName");
        foyer.setCapaciteFoyer(250L);
        Foyer savedFoyer = foyerRepository.save(foyer);
        savedFoyer.setNomFoyer("UpdatedName");

        Foyer result = foyerService.modifyFoyer(savedFoyer);
        assertEquals("UpdatedName", result.getNomFoyer());
    }

    @Test
    void testRemoveFoyer() {
        Foyer foyer = new Foyer();
        foyer.setNomFoyer("FoyerToDelete");
        foyer.setCapaciteFoyer(400L);
        Foyer savedFoyer = foyerRepository.save(foyer);

        foyerService.removeFoyer(savedFoyer.getIdFoyer());
        assertTrue(foyerRepository.findById(savedFoyer.getIdFoyer()).isEmpty());
    }
}