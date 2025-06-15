package org.example.service;

import org.example.model.Trees;
import org.springframework.stereotype.Service;

@Service
public interface TreesService {
    /**
     * Сохраняет дерево в системе.
     *
     * @param tree объект дерева для сохранения, не должен быть null
     * @return сохраненное дерево
     * @throws IllegalArgumentException если tree равен null
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    Trees save(Trees tree);

    /**
     * Находит дерево по его идентификатору.
     *
     * @param id идентификатор дерева, должен быть положительным
     * @return найденное дерево
     * @throws IllegalArgumentException если id меньше или равен 0
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    Trees findById(Long id);

    /**
     * Обновляет данные существующего дерева.
     *
     * @param tree объект дерева с обновленными данными, не должен быть null
     * @throws IllegalArgumentException если tree равен null или не имеет id
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    void update(Trees tree);

    /**
     * Удаляет дерево по его идентификатору.
     *
     * @param id идентификатор дерева для удаления, должен быть положительным
     * @throws IllegalArgumentException если id меньше или равен 0
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    void deleteById(Long id);
}