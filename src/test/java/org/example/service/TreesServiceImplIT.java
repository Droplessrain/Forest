package org.example.service;

import org.example.config.TestJdbcConfig;
import org.example.model.Trees;
import org.example.serviceImpl.TreesServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static org.junit.Assert.*;

@Sql(scripts = "/sql/trees/init_trees.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/trees/clean_trees.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Import(TestJdbcConfig.class)
public class TreesServiceImplIT extends AbstractTestcontainers {

    static {
        postgreSQLContainer.withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger("POSTGRES")));
    }

    @Autowired
    private TreesServiceImpl treesService;

    @Test
    void saveAndFindById_ShouldWorkCorrectly() {
        Trees tree = new Trees(10L, "Ольха", 21);

        Trees saved = treesService.save(tree);
        Trees found = treesService.findById(saved.getId());

        assertNotNull(found.getId());
        assertEquals(21, found.getAge());
        assertEquals("Ольха", found.getName());
    }

    @Test
    void update_ShouldUpdateTree() {
        Trees tree = new Trees(10L, "Ольха", 20);
        Trees saved = treesService.save(tree);

        Trees updated = new Trees(saved.getId(), "Updated Oak", 21);
        treesService.update(updated);

        Trees found = treesService.findById(saved.getId());
        assertEquals(21, found.getAge());
        assertEquals("Updated Oak", found.getName());
    }

    @Test
    void deleteById_ShouldRemoveTree() {
        Trees tree = new Trees(10L, "Ольха", 2020);
        Trees saved = treesService.save(tree);

        treesService.deleteById(saved.getId());

        assertThrows(RuntimeException.class, () -> treesService.findById(saved.getId()));
    }
}