package org.example.repImpl;

import org.example.model.Trees;
import org.example.rep.TreesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

@Repository
public class TreesRepositoryImpl implements TreesRepository {

    private final DataSource dataSource;

    @Autowired
    public TreesRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Trees save(Trees tree) {
        String sql = "INSERT INTO trees (name, age) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tree.getName());
            ps.setInt(2, tree.getAge());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating tree failed, no rows affected.");
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tree.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating tree failed, no ID obtained.");
                }
            }
            return tree;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save tree", e);
        }
    }

    @Override
    public Optional<Trees> findById(Long id) {
        String sql = "SELECT * FROM trees WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Trees tree = new Trees(
                            rs.getLong("id"),
                            rs.getString("name"),
                            rs.getInt("age")
                    );
                    return Optional.of(tree);
                }
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find tree by id: " + id, e);
        }
    }

    @Override
    public void update(Trees tree) {
        String sql = "UPDATE trees SET name = ?, age = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, tree.getName());
            ps.setInt(2, tree.getAge());
            ps.setLong(3, tree.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating tree failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update tree", e);
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM trees WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting tree failed, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete tree by id: " + id, e);
        }
    }
}