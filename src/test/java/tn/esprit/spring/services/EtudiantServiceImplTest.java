package tn.esprit.spring.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;  // Mock de l'interface de dépôt

    @InjectMocks
    private EtudiantServiceImpl etudiantService;  // Service à tester

    private List<Etudiant> etudiants;  // Liste d'étudiants pour les tests

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les annotations Mockito
        etudiants = new ArrayList<>();  // Initialisation de la liste des étudiants
    }

    @Test
    void testGetEtudiantsAvecReservationValidePourAnneeDonnee() {
       // Configuration des données de test
       Etudiant etudiant1 = new Etudiant();
       etudiant1.setNomEtudiant("Alice");

       // Création d'une réservation valide pour l'année 2023
       Reservation reservation1 = new Reservation();
       reservation1.setEstValide(true);

       // Utilisation du Calendar pour définir une date en 2023
       Calendar calendar = Calendar.getInstance();
       calendar.set(Calendar.YEAR, 2023);
       calendar.set(Calendar.MONTH, Calendar.JANUARY);
       calendar.set(Calendar.DAY_OF_MONTH, 1);
       reservation1.setAnneeUniversitaire(calendar.getTime()); // Date pour 2023

       // Ajout de la réservation à l'étudiant
       etudiant1.setReservations(new HashSet<>());
       etudiant1.getReservations().add(reservation1);

       // Ajout de l'étudiant à la liste
       List<Etudiant> etudiantList= new ArrayList<>();
       etudiantList.add(etudiant1);

       // Simulation du comportement du mock
       when(etudiantRepository.findAll()).thenReturn(etudiantList);

       // Appel de la méthode à tester
       List<Etudiant> result = etudiantService.getEtudiantsAvecReservationValidePourAnneeDonnee(2023);

       // Vérification du résultat
       assertEquals(1, result.size(), "La liste des étudiants doit contenir 1 étudiant.");
       assertEquals("Alice", result.get(0).getNomEtudiant(), "Le nom de l'étudiant doit être Alice.");
    }


    @Test
    void testGetEtudiantsAvecReservationValidePourAnneeDonnee_SansReservationValide() {
       // Configuration des données de test sans réservations valides
       Etudiant etudiant2 = new Etudiant();
       etudiant2.setNomEtudiant("Bob");
       etudiant2.setReservations(new HashSet<>()); // Pas de réservations

       etudiants.add(etudiant2);

       // Simulation du comportement du mock
       when(etudiantRepository.findAll()).thenReturn(etudiants);

       // Appel de la méthode à tester
       List<Etudiant> result = etudiantService.getEtudiantsAvecReservationValidePourAnneeDonnee(2023);

       // Vérification que le résultat est vide
       assertTrue(result.isEmpty(), "La liste des étudiants doit être vide.");
    }



    @Test
    void testInscrireNouvelEtudiant_Succes() {
       // Configuration des valeurs d'entrée
       String nom = "Dupont";
       String prenom = "Marie";
       long cin = 12345678L;
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.YEAR, -20); // 20 ans d'âge pour satisfaire la condition d'âge
       Date dateNaissance = cal.getTime();

       // Mock du comportement du repository
       Mockito.when(etudiantRepository.existsByCinEtudiant(cin)).thenReturn(false);

       // Appel de la méthode
       String result = etudiantService.inscrireNouvelEtudiant(nom, prenom, cin, dateNaissance);

       // Vérifications
       Assertions.assertEquals("Inscription réussie pour l'étudiant Dupont Marie", result);
       verify(etudiantRepository).save(Mockito.any(Etudiant.class));
    }

    @Test
    void testInscrireNouvelEtudiant_Echec_CinDejaExistant() {
       String nom = "Dupont";
       String prenom = "Marie";
       long cin = 12345678L;
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.YEAR, -20);
       Date dateNaissance = cal.getTime();

       // Mock du comportement du repository pour CIN déjà existant
       Mockito.when(etudiantRepository.existsByCinEtudiant(cin)).thenReturn(true);

       // Appel de la méthode et vérification de l'exception
       Assertions.assertThrows(IllegalArgumentException.class, () -> {
          etudiantService.inscrireNouvelEtudiant(nom, prenom, cin, dateNaissance);
       });
    }

    @Test
    void testInscrireNouvelEtudiant_Echec_AgeMoinsDe18Ans() {
       String nom = "Dupont";
       String prenom = "Marie";
       long cin = 12345678L;
       Calendar cal = Calendar.getInstance();
       cal.add(Calendar.YEAR, -16); // Âge inférieur à 18 ans
       Date dateNaissance = cal.getTime();

       // Mock du comportement du repository pour CIN inexistant
       Mockito.when(etudiantRepository.existsByCinEtudiant(cin)).thenReturn(false);

       // Appel de la méthode et vérification de l'exception
       Assertions.assertThrows(IllegalArgumentException.class, () -> {
          etudiantService.inscrireNouvelEtudiant(nom, prenom, cin, dateNaissance);
       });
    }


   @Test
   void testRetrieveEtudiant_Success() {
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

      Etudiant result = etudiantService.retrieveEtudiant(1L);

      assertNotNull(result);
      verify(etudiantRepository, times(1)).findById(1L);
   }

   @Test
   void testRetrieveEtudiant_NotFound() {
      when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(IllegalArgumentException.class, () -> etudiantService.retrieveEtudiant(1L));
      verify(etudiantRepository, times(1)).findById(1L);
   }

   @Test
   void testRemoveEtudiant() {
      etudiantService.removeEtudiant(1L);
      verify(etudiantRepository, times(1)).deleteById(1L);
   }

   @Test
   void testUpdateEmailEtudiant_Success() {
      // Préparation des données
      long etudiantId = 1L;
      String nouvelEmail = "new.email@example.com";

      Etudiant etudiant = new Etudiant();
      etudiant.setIdEtudiant(etudiantId);
      etudiant.setEmail("old.email@example.com");

      // Définir le comportement du mock
      when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));
      when(etudiantRepository.save(any(Etudiant.class))).thenAnswer(i -> i.getArgument(0));

      // Appel de la méthode à tester
      Etudiant result = etudiantService.updateEmailEtudiant(etudiantId, nouvelEmail);

      // Vérifications
      assertNotNull(result);
      assertEquals(nouvelEmail, result.getEmail());
      verify(etudiantRepository, times(1)).findById(etudiantId);
      verify(etudiantRepository, times(1)).save(etudiant);
   }

   @Test
   void testUpdateEmailEtudiant_EtudiantNotFound() {
      // Préparation des données
      long etudiantId = 2L;
      String nouvelEmail = "new.email@example.com";

      // Définir le comportement du mock
      when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.empty());

      // Appel de la méthode à tester et vérification de l'exception
      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         etudiantService.updateEmailEtudiant(etudiantId, nouvelEmail);
      });

      // Vérification du message d'exception
      assertEquals("Étudiant introuvable avec l'ID: " + etudiantId, exception.getMessage());

      // Vérification des interactions avec le mock
      verify(etudiantRepository, times(1)).findById(etudiantId);
      verify(etudiantRepository, never()).save(any(Etudiant.class));
   }

   @Test
   void testGetNombreReservationsParCin_multipleReservations() {
      // Scénario 1 : Étudiant avec plusieurs réservations
      long cin = 12345678L;
      Etudiant etudiant = new Etudiant();
      etudiant.setCinEtudiant(cin);
      etudiant.setReservations(new HashSet<>(Arrays.asList(new Reservation(), new Reservation(), new Reservation()))); // 3 réservations

      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

      int result = etudiantService.getNombreReservationsParCin(cin);

      assertEquals(3, result);
      verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
   }

   @Test
   void testGetNombreReservationsParCin_noReservations() {
      // Scénario 2 : Étudiant sans réservations
      long cin = 87654321L;
      Etudiant etudiant = new Etudiant();
      etudiant.setCinEtudiant(cin);
      etudiant.setReservations(new HashSet<>()); // 0 réservations

      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(etudiant);

      int result = etudiantService.getNombreReservationsParCin(cin);

      assertEquals(0, result);
      verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
   }

   @Test
   void testGetNombreReservationsParCin_etudiantNonTrouve() {
      // Scénario 3 : Étudiant non trouvé avec le CIN donné
      long cin = 99999999L;

      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(null);

      Exception exception = assertThrows(IllegalArgumentException.class, () -> {
         etudiantService.getNombreReservationsParCin(cin);
      });

      assertEquals("Aucun étudiant trouvé avec le CIN: " + cin, exception.getMessage());
      verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(cin);
   }



}

