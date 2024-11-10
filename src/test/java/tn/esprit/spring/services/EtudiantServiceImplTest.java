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
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;  // Mock de l'interface de dépôt
   @Mock
   private UniversiteRepository universiteRepository; // Mock de l'interface UniversiteRepository


   @InjectMocks
    private EtudiantServiceImpl etudiantService;  // Service à tester

    private List<Etudiant> etudiants;  // Liste d'étudiants pour les tests

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialise les annotations Mockito
        etudiants = new ArrayList<>();  // Initialisation de la liste des étudiants
    }
//GetEtudiantsAvecReservationValidePourAnneeDonnee
   //Scenario 1 Success
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

    //Scenario2  reservation vide
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

   //Scenario3  reservation invalide
   @Test
   void testGetEtudiantsAvecReservationValidePourAnneeDonnee_ReservationInvalide() {
      // Configuration des données de test avec une réservation invalide pour l'année 2023
      Etudiant etudiant3 = new Etudiant();
      etudiant3.setNomEtudiant("Charlie");

      // Création d'une réservation invalide pour l'année 2023
      Reservation reservation2 = new Reservation();
      reservation2.setEstValide(false);

      // Utilisation du Calendar pour définir une date en 2023
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.YEAR, 2023);
      calendar.set(Calendar.MONTH, Calendar.JANUARY);
      calendar.set(Calendar.DAY_OF_MONTH, 1);
      reservation2.setAnneeUniversitaire(calendar.getTime()); // Date pour 2023

      // Ajout de la réservation à l'étudiant
      etudiant3.setReservations(new HashSet<>());
      etudiant3.getReservations().add(reservation2);

      // Liste d'étudiants incluant l'étudiant avec réservation invalide
      List<Etudiant> etudiantList = new ArrayList<>();
      etudiantList.add(etudiant3);

      // Simulation du comportement du mock
      when(etudiantRepository.findAll()).thenReturn(etudiantList);

      // Appel de la méthode à tester
      List<Etudiant> result = etudiantService.getEtudiantsAvecReservationValidePourAnneeDonnee(2023);

      // Vérification que la liste est vide (étudiant avec réservation invalide)
      assertTrue(result.isEmpty(), "La liste des étudiants doit être vide.");
   }

//InscrireNouvelEtudiant
// Scenario 1 Success
   @Test
   void addEtudiant_Succes() {
   // Configuration des valeurs d'entrée
      String nom = "Dupont";
      String prenom = "Marie";
      long cin = 12345678L;
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, -20); // 20 ans d'âge pour satisfaire la condition d'âge
      Date dateNaissance = cal.getTime();

      Etudiant e = new Etudiant();
      e.setNomEtudiant(nom);
      e.setPrenomEtudiant(prenom);
      e.setIdEtudiant(cin);
      e.setDateNaissance(dateNaissance);

   // Mock du comportement du repository
      Mockito.when(etudiantRepository.existsByCinEtudiant(cin)).thenReturn(false);
      Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(e);  // Simuler la sauvegarde de l'étudiant

   // Appel de la méthode
      Etudiant result = etudiantService.addEtudiant(e);

   // Vérifications
      Assertions.assertNotNull(result, "L'étudiant retourné ne doit pas être nul.");
      Assertions.assertEquals("Dupont", result.getNomEtudiant(), "Le nom de l'étudiant doit être Dupont.");
      Assertions.assertEquals("Marie", result.getPrenomEtudiant(), "Le prénom de l'étudiant doit être Marie.");
      verify(etudiantRepository).save(Mockito.any(Etudiant.class));
}

//   // Scenario 2 Cin Existe Deja



   // Scenario 3 Age < 18
   @Test
   void addEtudiant_Echec_AgeMoinsDe18Ans() {
      // Données de test
      String nom = "Dupont";
      String prenom = "Marie";
      long cin = 12345678L;
      Date dateNaissance = getDateNaissance(16); // Utilise la méthode utilitaire pour un âge de 16 ans

      // Mock du comportement du repository pour un CIN inexistant
      Mockito.when(etudiantRepository.existsByCinEtudiant(cin)).thenReturn(false);

      // Création de l'objet Etudiant
      Etudiant etudiant = new Etudiant();
      etudiant.setNomEtudiant(nom);
      etudiant.setPrenomEtudiant(prenom);
      etudiant.setCinEtudiant(cin);
      etudiant.setDateNaissance(dateNaissance);

      // Appel de la méthode et vérification de l'exception
      IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
         etudiantService.addEtudiant(etudiant);
      });

      // Vérification du message d'exception avec une description explicite
      Assertions.assertEquals("L'étudiant doit avoir au moins 18 ans.", exception.getMessage(),
              "L'exception doit indiquer que l'étudiant a moins de 18 ans.");
   }

   // Méthode utilitaire pour calculer la date de naissance en fonction de l'âge
   private Date getDateNaissance(int age) {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.YEAR, -age);
      return cal.getTime();
   }


//Retreive Etudiants
   //Scenario 1 Success

   @Test
   void testRetrieveEtudiant_Success() {
      Etudiant etudiant = new Etudiant();
      when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

      Etudiant result = etudiantService.retrieveEtudiant(1L);

      assertNotNull(result);
      verify(etudiantRepository, times(1)).findById(1L);
   }

   //Scenario 2 Etudiant Not Found
   @Test
   void testRetrieveEtudiant_NotFound() {
      when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(IllegalArgumentException.class, () -> etudiantService.retrieveEtudiant(1L));
      verify(etudiantRepository, times(1)).findById(1L);
   }

// Remove Etudiant
   //Scenario 1 Success
@Test
void testRemoveEtudiant_Exist() {
   // Configuration des données de test
   Etudiant etudiant = new Etudiant();
   etudiant.setCinEtudiant(1L);
   etudiant.setNomEtudiant("Alice");

   // Simulation du comportement du mock
   when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

   // Appel de la méthode à tester
   etudiantService.removeEtudiant(1L);

   // Vérification que la méthode deleteById a été appelée
   verify(etudiantRepository, times(1)).deleteById(1L);
}

   //Scenario 2 Etudiant not found
   @Test
   void testRemoveEtudiant_NonExistant() {
      // Simulation du comportement du mock (l'étudiant n'existe pas)
      when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

      // Vérification que l'exception IllegalArgumentException est lancée
      IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
         etudiantService.removeEtudiant(1L);
      });

      // Vérification du message d'exception
      assertEquals("Aucun étudiant trouvé avec l'ID: 1", thrown.getMessage(), "Le message d'exception doit être correct.");

      // Vérification que la méthode deleteById n'a pas été appelée
      verify(etudiantRepository, times(0)).deleteById(1L);
   }


   //Scenario 2 CIN Etudiant not found
   @Test
   void testRemoveEtudiant_InvalidId() {
      // Test avec un ID invalide (null ou négatif)
      Long invalidId = null;

      // Appel de la méthode à tester et vérification qu'une exception est lancée
      assertThrows(IllegalArgumentException.class, () -> etudiantService.removeEtudiant(invalidId));

      // Vérification que la méthode deleteById n'a pas été appelée
      verify(etudiantRepository, times(0)).deleteById(anyLong());
   }

//UpdateEtudiant
   //Scenario1 Success
   @Test
   void testModifyEtudiant_Success() {
      long cin = 123456789;
      Etudiant existingEtudiant = new Etudiant();
      existingEtudiant.setCinEtudiant(cin);
      existingEtudiant.setNomEtudiant("Alice");

      when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(existingEtudiant);
      when(etudiantRepository.save(existingEtudiant)).thenReturn(existingEtudiant);

      Etudiant result = etudiantService.modifyEtudiant(cin, "AliceUpdated", "NewPrenom", "alice@example.com");

      assertEquals("AliceUpdated", result.getNomEtudiant());
      assertEquals("NewPrenom", result.getPrenomEtudiant());
      assertEquals("alice@example.com", result.getEmail());
}
      //scenario2
      @Test
      void testModifyEtudiant_NotFound() {
         long cin = 123456789;

         when(etudiantRepository.findEtudiantByCinEtudiant(cin)).thenReturn(null);

         assertThrows(IllegalArgumentException.class, () ->
                 etudiantService.modifyEtudiant(cin, "AliceUpdated", "NewPrenom", "alice@example.com")
         );
      }



   //GetNombreReservationsParCin
   //Scenario 1 Success
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
   //Scenario 2 pas de reservation
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
   //Scenario 3 Etudiant Not Found
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


   // Scénario 1 : La liste des étudiants est vide et lève une exception
   @Test
   void testRetrieveAllEtudiants_Vide_LanceException() {
      // Simulation du comportement du repository pour retourner une liste vide
      when(etudiantRepository.findAll()).thenReturn(etudiants);

      // Vérification que l'exception est bien levée
      IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
         etudiantService.retrieveAllEtudiants();
      });

      assertEquals("Aucun étudiant trouvé.", thrown.getMessage(), "Le message d'exception doit être celui attendu.");
   }

   // Scénario 2 : La liste des étudiants contient des étudiants
   @Test
   void testRetrieveAllEtudiants_AvecEtudiants() {
      // Configuration des données de test
      Etudiant etudiant1 = new Etudiant();
      etudiant1.setNomEtudiant("Alice");
      etudiant1.setPrenomEtudiant("Dupont");
      etudiant1.setIdEtudiant(12345678L);
      etudiant1.setDateNaissance(new Date());  // Date de naissance générique pour le test

      Etudiant etudiant2 = new Etudiant();
      etudiant2.setNomEtudiant("Bob");
      etudiant2.setPrenomEtudiant("Martin");
      etudiant2.setIdEtudiant(87654321L);
      etudiant2.setDateNaissance(new Date());

      // Ajouter les étudiants à la liste
      etudiants.add(etudiant1);
      etudiants.add(etudiant2);

      // Simulation du comportement du repository pour retourner la liste avec des étudiants
      when(etudiantRepository.findAll()).thenReturn(etudiants);

      // Appel de la méthode à tester
      List<Etudiant> result = etudiantService.retrieveAllEtudiants();

      // Vérification que la liste contient les bons étudiants
      assertEquals(2, result.size(), "La liste des étudiants doit contenir 2 étudiants.");
      assertEquals("Alice", result.get(0).getNomEtudiant(), "Le premier étudiant doit être Alice.");
      assertEquals("Bob", result.get(1).getNomEtudiant(), "Le deuxième étudiant doit être Bob.");
   }







}

