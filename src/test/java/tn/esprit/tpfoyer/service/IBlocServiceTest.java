package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllBlocs() {
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc1", 60, null, null));
        blocs.add(new Bloc(2L, "Bloc2", 40, null, null));

        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> result = blocServiceImpl.retrieveAllBlocs();

        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc(1L, "Bloc1", 60, null, null);
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc result = blocServiceImpl.retrieveBloc(1L);

        assertNotNull(result);
        assertEquals("Bloc1", result.getNomBloc());
        verify(blocRepository, times(1)).findById(1L);
    }



    @Test
    void testModifyBloc() {
        Bloc bloc = new Bloc(1L, "Bloc1", 50, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocServiceImpl.modifyBloc(bloc);

        assertNotNull(result);
        assertEquals("Bloc1", result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(1L);

        blocServiceImpl.removeBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc1", 60, null, null));

        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        List<Bloc> result = blocServiceImpl.trouverBlocsSansFoyer();

        assertEquals(1, result.size());
        verify(blocRepository, times(1)).findAllByFoyerIsNull();
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        List<Bloc> blocs = new ArrayList<>();
        blocs.add(new Bloc(1L, "Bloc1", 60, null, null));

        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc1", 60)).thenReturn(blocs);

        List<Bloc> result = blocServiceImpl.trouverBlocsParNomEtCap("Bloc1", 60);

        assertEquals(1, result.size());
        assertEquals("Bloc1", result.get(0).getNomBloc());
        verify(blocRepository, times(1)).findAllByNomBlocAndCapaciteBloc("Bloc1", 60);
    }
}
