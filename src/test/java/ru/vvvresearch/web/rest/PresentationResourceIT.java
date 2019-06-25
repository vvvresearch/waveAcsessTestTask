package ru.vvvresearch.web.rest;

import ru.vvvresearch.WaveAcsessConferenceApp;
import ru.vvvresearch.domain.Presentation;
import ru.vvvresearch.repository.PresentationRepository;
import ru.vvvresearch.service.PresentationService;
import ru.vvvresearch.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static ru.vvvresearch.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link PresentationResource} REST controller.
 */
@SpringBootTest(classes = WaveAcsessConferenceApp.class)
public class PresentationResourceIT {

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private PresentationRepository presentationRepository;

    @Mock
    private PresentationRepository presentationRepositoryMock;

    @Mock
    private PresentationService presentationServiceMock;

    @Autowired
    private PresentationService presentationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPresentationMockMvc;

    private Presentation presentation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PresentationResource presentationResource = new PresentationResource(presentationService);
        this.restPresentationMockMvc = MockMvcBuilders.standaloneSetup(presentationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presentation createEntity(EntityManager em) {
        Presentation presentation = new Presentation()
            .topic(DEFAULT_TOPIC)
            .description(DEFAULT_DESCRIPTION);
        return presentation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Presentation createUpdatedEntity(EntityManager em) {
        Presentation presentation = new Presentation()
            .topic(UPDATED_TOPIC)
            .description(UPDATED_DESCRIPTION);
        return presentation;
    }

    @BeforeEach
    public void initTest() {
        presentation = createEntity(em);
    }

    @Test
    @Transactional
    public void createPresentation() throws Exception {
        int databaseSizeBeforeCreate = presentationRepository.findAll().size();

        // Create the Presentation
        restPresentationMockMvc.perform(post("/api/presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentation)))
            .andExpect(status().isCreated());

        // Validate the Presentation in the database
        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeCreate + 1);
        Presentation testPresentation = presentationList.get(presentationList.size() - 1);
        assertThat(testPresentation.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testPresentation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createPresentationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = presentationRepository.findAll().size();

        // Create the Presentation with an existing ID
        presentation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPresentationMockMvc.perform(post("/api/presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentation)))
            .andExpect(status().isBadRequest());

        // Validate the Presentation in the database
        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = presentationRepository.findAll().size();
        // set the field null
        presentation.setTopic(null);

        // Create the Presentation, which fails.

        restPresentationMockMvc.perform(post("/api/presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentation)))
            .andExpect(status().isBadRequest());

        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPresentations() throws Exception {
        // Initialize the database
        presentationRepository.saveAndFlush(presentation);

        // Get all the presentationList
        restPresentationMockMvc.perform(get("/api/presentations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(presentation.getId().intValue())))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPresentationsWithEagerRelationshipsIsEnabled() throws Exception {
        PresentationResource presentationResource = new PresentationResource(presentationServiceMock);
        when(presentationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPresentationMockMvc = MockMvcBuilders.standaloneSetup(presentationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPresentationMockMvc.perform(get("/api/presentations?eagerload=true"))
        .andExpect(status().isOk());

        verify(presentationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPresentationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        PresentationResource presentationResource = new PresentationResource(presentationServiceMock);
            when(presentationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restPresentationMockMvc = MockMvcBuilders.standaloneSetup(presentationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPresentationMockMvc.perform(get("/api/presentations?eagerload=true"))
        .andExpect(status().isOk());

            verify(presentationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPresentation() throws Exception {
        // Initialize the database
        presentationRepository.saveAndFlush(presentation);

        // Get the presentation
        restPresentationMockMvc.perform(get("/api/presentations/{id}", presentation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(presentation.getId().intValue()))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPresentation() throws Exception {
        // Get the presentation
        restPresentationMockMvc.perform(get("/api/presentations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePresentation() throws Exception {
        // Initialize the database
        presentationService.save(presentation);

        int databaseSizeBeforeUpdate = presentationRepository.findAll().size();

        // Update the presentation
        Presentation updatedPresentation = presentationRepository.findById(presentation.getId()).get();
        // Disconnect from session so that the updates on updatedPresentation are not directly saved in db
        em.detach(updatedPresentation);
        updatedPresentation
            .topic(UPDATED_TOPIC)
            .description(UPDATED_DESCRIPTION);

        restPresentationMockMvc.perform(put("/api/presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPresentation)))
            .andExpect(status().isOk());

        // Validate the Presentation in the database
        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeUpdate);
        Presentation testPresentation = presentationList.get(presentationList.size() - 1);
        assertThat(testPresentation.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testPresentation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingPresentation() throws Exception {
        int databaseSizeBeforeUpdate = presentationRepository.findAll().size();

        // Create the Presentation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPresentationMockMvc.perform(put("/api/presentations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(presentation)))
            .andExpect(status().isBadRequest());

        // Validate the Presentation in the database
        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePresentation() throws Exception {
        // Initialize the database
        presentationService.save(presentation);

        int databaseSizeBeforeDelete = presentationRepository.findAll().size();

        // Delete the presentation
        restPresentationMockMvc.perform(delete("/api/presentations/{id}", presentation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Presentation> presentationList = presentationRepository.findAll();
        assertThat(presentationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Presentation.class);
        Presentation presentation1 = new Presentation();
        presentation1.setId(1L);
        Presentation presentation2 = new Presentation();
        presentation2.setId(presentation1.getId());
        assertThat(presentation1).isEqualTo(presentation2);
        presentation2.setId(2L);
        assertThat(presentation1).isNotEqualTo(presentation2);
        presentation1.setId(null);
        assertThat(presentation1).isNotEqualTo(presentation2);
    }
}
