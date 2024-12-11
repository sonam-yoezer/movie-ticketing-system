package com.java.movieticketingsystem.utils;

import org.springframework.lang.NonNull;

import java.util.List;

public interface IGenericCrudService<T, E> {

    /**
     * Fetches list of all entities
     *
     * @return The list of entities of particular type in the system
     */
    List<E> findAll();

    /**
     * Saves entities data
     *
     * @param entity The entity object to be saved in the system
     * @return The saved entity
     */
    E save(@NonNull T entity);

    /**
     * Fetches entity by id
     *
     * @param id The unique identifier of the entity created
     * @return The entity object matching its id
     */
    E fetchById(long id) throws Exception;

    /**
     * Updates the entity
     *
     * @param id     The unique identifier of the entity
     * @param entity The entity object containing updated values
     */
    String update(long id, @NonNull E entity);

    /**
     * Delete the entity by id.
     *
     * @param id Identifier for entity.
     * @return String as message.
     */
    String deleteById(long id);
}
