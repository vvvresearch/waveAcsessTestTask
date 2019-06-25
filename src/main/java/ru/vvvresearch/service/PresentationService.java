package ru.vvvresearch.service;

import ru.vvvresearch.domain.Presentation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Presentation}.
 */
public interface PresentationService {

    /**
     * Save a presentation.
     *
     * @param presentation the entity to save.
     * @return the persisted entity.
     */
    Presentation save(Presentation presentation);

    /**
     * Get all the presentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Presentation> findAll(Pageable pageable);

    /**
     * Get all the presentations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Presentation> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" presentation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Presentation> findOne(Long id);

    /**
     * Delete the "id" presentation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
