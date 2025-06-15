package org.example.serviceImpl;

import org.example.model.Trees;
import org.example.rep.TreesRepository;
import org.example.service.TreesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class TreesServiceImpl implements TreesService {

    private final TreesRepository treesRepository;

    @Autowired
    public TreesServiceImpl(TreesRepository treesRepository) {
        this.treesRepository = treesRepository;
    }

    @Override
    public Trees save(Trees tree) {
        try {
            return treesRepository.save(tree);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save tree", e);
        }
    }

    @Override
    public Trees findById(Long id) {
        try {
            return treesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Tree not found with id: " + id));
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find tree with id: " + id, e);
        }
    }

    @Override
    public void update(Trees tree) {
        try {
            treesRepository.update(tree);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update tree with id: " + tree.getId(), e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            treesRepository.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete tree with id: " + id, e);
        }
    }
}
