package ru.vvvresearch.service.impl;

import ru.vvvresearch.service.PresentationService;
import ru.vvvresearch.domain.Presentation;
import ru.vvvresearch.repository.PresentationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Presentation}.
 */
@Service
@Transactional
public class PresentationServiceImpl implements PresentationService {

    private final Logger log = LoggerFactory.getLogger(PresentationServiceImpl.class);

    private final PresentationRepository presentationRepository;

    public PresentationServiceImpl(PresentationRepository presentationRepository) {
        this.presentationRepository = presentationRepository;
    }

    /**
     * Save a presentation.
     *
     * @param presentation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Presentation save(Presentation presentation) {
        log.debug("Request to save Presentation : {}", presentation);
        return presentationRepository.save(presentation);
    }

    /**
     * Get all the presentations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Presentation> findAll(Pageable pageable) {
        log.debug("Request to get all Presentations");
        return presentationRepository.findAll(pageable);
    }

    /**
     * Get all the presentations with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Presentation> findAllWithEagerRelationships(Pageable pageable) {
        return presentationRepository.findAllWithEagerRelationships(pageable);
    }
    

    /**
     * Get one presentation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Presentation> findOne(Long id) {
        log.debug("Request to get Presentation : {}", id);
        return presentationRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the presentation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Presentation : {}", id);
        presentationRepository.deleteById(id);
    }
}
