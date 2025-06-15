package org.example.service;

import org.example.model.Trees;
import org.example.rep.TreesRepository;
import org.example.serviceImpl.TreesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.SQLException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TreesServiceImplTest {

    @Mock
    private TreesRepository treesRepository;

    @InjectMocks
    private TreesServiceImpl treesService;

    @Test
    void save_ShouldSaveTree_WhenValidInput() throws SQLException {
        Trees tree = new Trees(1L,  "Oak", 20);
        when(treesRepository.save(tree)).thenReturn(tree);

        treesService.save(tree);

        verify(treesRepository, times(1)).save(tree);
    }

    @Test
    void save_ShouldThrowRuntimeException_WhenDatabaseError() throws SQLException {
        Trees tree = new Trees(1L, "Oak", 20);
        when(treesRepository.save(tree)).thenThrow(new SQLException("DB error"));

        assertThrows(RuntimeException.class, () -> treesService.save(tree));
    }

    @Test
    void findById_ShouldReturnTree_WhenExists() throws SQLException {
        Trees tree = new Trees(1L, "Oak", 20);
        when(treesRepository.findById(1L)).thenReturn(Optional.of(tree));

        Trees result = treesService.findById(1L);

        assertEquals(tree, result);
        verify(treesRepository, times(1)).findById(1L);
    }

    @Test
    void findById_ShouldThrowRuntimeException_WhenNotFound() throws SQLException {
        when(treesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> treesService.findById(1L));
    }

    @Test
    void update_ShouldUpdateTree_WhenValidInput() throws SQLException {
        Trees tree = new Trees(1L, "Oak", 20);
        doNothing().when(treesRepository).update(tree);

        treesService.update(tree);

        verify(treesRepository, times(1)).update(tree);
    }

    @Test
    void deleteById_ShouldDeleteTree_WhenValidId() throws SQLException {
        doNothing().when(treesRepository).deleteById(1L);

        treesService.deleteById(1L);

        verify(treesRepository, times(1)).deleteById(1L);
    }
}