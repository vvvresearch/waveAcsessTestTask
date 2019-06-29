package ru.vvvresearch.web.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.vvvresearch.domain.Presentation;
import ru.vvvresearch.security.AuthoritiesConstants;
import ru.vvvresearch.service.PresentationService;
import ru.vvvresearch.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.vvvresearch.domain.Presentation}.
 */
@RestController
@RequestMapping("/api")
public class PresentationResource {

    private final Logger log = LoggerFactory.getLogger(PresentationResource.class);

    private static final String ENTITY_NAME = "presentation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PresentationService presentationService;

    public PresentationResource(PresentationService presentationService) {
        this.presentationService = presentationService;
    }

    /**
     * {@code POST  /presentations} : Create a new presentation.
     *
     * @param presentation the presentation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new presentation, or with status {@code 400 (Bad Request)} if the presentation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/presentations")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN +","+AuthoritiesConstants.PRESENTER+ "\")")
    public ResponseEntity<Presentation> createPresentation(@Valid @RequestBody Presentation presentation) throws URISyntaxException {
        log.debug("REST request to save Presentation : {}", presentation);
        if (presentation.getId() != null) {
            throw new BadRequestAlertException("A new presentation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Presentation result = presentationService.save(presentation);
        return ResponseEntity.created(new URI("/api/presentations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /presentations} : Updates an existing presentation.
     *
     * @param presentation the presentation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated presentation,
     * or with status {@code 400 (Bad Request)} if the presentation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the presentation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/presentations")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN +","+AuthoritiesConstants.PRESENTER+ "\")")
    public ResponseEntity<Presentation> updatePresentation(@Valid @RequestBody Presentation presentation) throws URISyntaxException {
        log.debug("REST request to update Presentation : {}", presentation);
        if (presentation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Presentation result = presentationService.save(presentation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, presentation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /presentations} : get all the presentations.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of presentations in body.
     */
    @GetMapping("/presentations")
    public ResponseEntity<List<Presentation>> getAllPresentations(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Presentations");
        Page<Presentation> page;
        if (eagerload) {
            page = presentationService.findAllWithEagerRelationships(pageable);
        } else {
            page = presentationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /presentations/:id} : get the "id" presentation.
     *
     * @param id the id of the presentation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the presentation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/presentations/{id}")
    public ResponseEntity<Presentation> getPresentation(@PathVariable Long id) {
        log.debug("REST request to get Presentation : {}", id);
        Optional<Presentation> presentation = presentationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(presentation);
    }

    /**
     * {@code DELETE  /presentations/:id} : delete the "id" presentation.
     *
     * @param id the id of the presentation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/presentations/{id}")
    @PreAuthorize("hasAnyRole(\"" + AuthoritiesConstants.ADMIN +","+AuthoritiesConstants.PRESENTER+ "\")")
    public ResponseEntity<Void> deletePresentation(@PathVariable Long id) {
        log.debug("REST request to delete Presentation : {}", id);
        presentationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
