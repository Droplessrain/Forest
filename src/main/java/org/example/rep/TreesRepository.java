package org.example.rep;

import org.example.model.Trees;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public interface TreesRepository {

    public Trees save(Trees tree) throws SQLException ;

    public Optional<Trees> findById(Long id) throws SQLException;

    public void update(Trees tree) throws SQLException;

    public void deleteById(Long id) throws SQLException;
}