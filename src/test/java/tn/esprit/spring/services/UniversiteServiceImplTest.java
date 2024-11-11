package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialisation des mocks
    }

    @Test
    void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> universites = new ArrayList<>();
        universites.add(new Universite(0, "Université de Test", "Adresse Université", null));
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertEquals(1, result.size());
        assertEquals("Université de Test", result.get(0).getNomUniversite());
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        Universite universite = new Universite(1, "Université de Test", "Adresse Université", null);
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(1L);

        // Assert
        assertEquals("Université de Test", result.getNomUniversite());
    }

    @Test
    void testAddUniversite() {
        // Arrange
        Universite universite = new Universite(0, "Nouvelle Université", "Adresse Nouvelle", null);
        Universite savedUniversite = new Universite(1, "Nouvelle Université", "Adresse Nouvelle", null);
        when(universiteRepository.save(universite)).thenReturn(savedUniversite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertEquals(1, result.getIdUniversite());
        assertEquals("Nouvelle Université", result.getNomUniversite());
    }

    @Test
    void testModifyUniversite() {
        // Arrange
        Universite universite = new Universite(1, "Université Modifiée", "Adresse Modifiée", null);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.modifyUniversite(universite);

        // Assert
        assertEquals("Université Modifiée", result.getNomUniversite());
    }

    @Test
    void testRemoveUniversite() {
        // Act
        universiteService.removeUniversite(1L);

        // Assert
        verify(universiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAffecterFoyerAUniversite_Success() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        Universite universite = new Universite(1, "Université de Test", "Adresse Université", null);

        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findFirstByNomUniversite("Université de Test")).thenReturn(universite);

        // Act
        Universite result = universiteService.affecterFoyerAUniversite(1L, "Université de Test");

        // Assert
        assertEquals("Université de Test", result.getNomUniversite());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testAffecterFoyerAUniversite_FoyerNotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert & Act
        assertThrows(RuntimeException.class, () -> universiteService.affecterFoyerAUniversite(1L, "Université de Test"));
    }

    @Test
    void testAffecterFoyerAUniversite_UniversiteNotFound() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findFirstByNomUniversite("Université Inconnue")).thenReturn(null);

        // Assert & Act
        assertThrows(RuntimeException.class, () -> universiteService.affecterFoyerAUniversite(1L, "Université Inconnue"));
    }
    @Test
    void testDesaffecterFoyerAUniversite_Success() {
        // Arrange
        Universite universite = new Universite(1L, "Université de Test", "Adresse Université", null);
        Foyer foyer = new Foyer(1L, "Foyer de Test", 100, universite, null);
        universite.setFoyer(foyer);

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Universite result = universiteService.desaffecterFoyerAUniversite(1L);

        // Assert
        assertEquals("Université de Test", result.getNomUniversite());
        assertEquals(null, result.getFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testDesaffecterFoyerAUniversite_UniversiteNotFound() {
        // Arrange
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert & Act
        assertThrows(RuntimeException.class, () -> universiteService.desaffecterFoyerAUniversite(1L));
    }

    @Test
    void testDesaffecterFoyerAUniversite_NoFoyerAssigned() {
        // Arrange
        Universite universite = new Universite(1L, "Université de Test", "Adresse Université", null);

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.desaffecterFoyerAUniversite(1L);

        // Assert
        assertEquals("Université de Test", result.getNomUniversite());
        assertEquals(null, result.getFoyer());
        verify(foyerRepository, times(0)).save(any());
    }
    @Test
    void testRetrieveUniversitiesByFoyer_Success() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer 1", 100, null, null);
        Universite universite1 = new Universite(1L, "Université de Test 1", "Adresse Université 1", foyer);
        Universite universite2 = new Universite(2L, "Université de Test 2", "Adresse Université 2", foyer);

        // Foyer has two universities associated with it
        List<Universite> universites = List.of(universite1, universite2);

        // Mock the repository to return the list of universities
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveUniversitiesByFoyer(1L);

        // Assert
        assertEquals(2, result.size()); // Should return two universities
        assertTrue(result.stream().anyMatch(u -> u.getNomUniversite().equals("Université de Test 1")));
        assertTrue(result.stream().anyMatch(u -> u.getNomUniversite().equals("Université de Test 2")));
    }

    @Test
    void testRetrieveUniversitiesByFoyer_FoyerNotFound() {
        // Arrange
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        // Assert & Act
        assertThrows(RuntimeException.class, () -> universiteService.retrieveUniversitiesByFoyer(1L));
    }


}
