package com.java.movieticketingsystem.utils;

import org.springframework.lang.NonNull;

import java.util.List;

public interface IGenericCrudService<T, E> {

    /**
     * Fetches list of all entities
     *
     * @return The list of entities of a particular type in the system
     */
    List<E> findAll();

    /**
     * Saves an entity's data
     *
     * @param entity The entity object to be saved in the system
     * @return The saved entity
     */
    E save(@NonNull T entity);

    /**
     * Fetches an entity by its ID
     *
     * @param id The unique identifier of the entity created
     * @return The entity object matching its ID
     */
    E fetchById(long id) throws Exception;

    /**
     * Updates the entity
     *
     * @param id     The unique identifier of the entity
     * @param entity The entity object containing updated values
     * @return A message indicating the success of the update operation
     */
    String update(long id, @NonNull E entity);

    /**
     * Deletes the entity by ID.
     *
     * @param id The identifier for the entity
     * @return A message indicating the success of the delete operation
     */
    String deleteById(long id);
}
