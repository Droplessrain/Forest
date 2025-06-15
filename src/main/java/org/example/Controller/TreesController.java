package org.example.Controller;

import lombok.AllArgsConstructor;
import org.example.model.Trees;
import org.example.service.TreesService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trees")
@AllArgsConstructor
public class TreesController {

    private final TreesService treesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTree(@RequestBody Trees tree) {
        try {
            treesService.save(tree);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e);
        }
    }

    @GetMapping("/{id}")
    public Trees getTreeById(@PathVariable("id") Long id) {
        try {
            return treesService.findById(id);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e);
        }
    }

    @PutMapping("/{id}")
    public void updateTree(@PathVariable("id") Long id, @RequestBody Trees updatedTree) {
        try {
            updatedTree.setId(id);
            treesService.update(updatedTree);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTree(@PathVariable("id") Long id) {
        try {
            treesService.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database error", e);
        }
    }
}