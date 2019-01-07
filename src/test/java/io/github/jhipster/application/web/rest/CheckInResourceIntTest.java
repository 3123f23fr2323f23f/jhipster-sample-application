package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.CheckIn;
import io.github.jhipster.application.repository.CheckInRepository;
import io.github.jhipster.application.service.CheckInService;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CheckInResource REST controller.
 *
 * @see CheckInResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class CheckInResourceIntTest {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    private static final String DEFAULT_TIME_CHECK_IN = "AAAAAAAAAA";
    private static final String UPDATED_TIME_CHECK_IN = "BBBBBBBBBB";

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInService checkInService;

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

    private MockMvc restCheckInMockMvc;

    private CheckIn checkIn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CheckInResource checkInResource = new CheckInResource(checkInService);
        this.restCheckInMockMvc = MockMvcBuilders.standaloneSetup(checkInResource)
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
    public static CheckIn createEntity(EntityManager em) {
        CheckIn checkIn = new CheckIn()
            .userID(DEFAULT_USER_ID)
            .timeCheckIn(DEFAULT_TIME_CHECK_IN);
        return checkIn;
    }

    @Before
    public void initTest() {
        checkIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckIn() throws Exception {
        int databaseSizeBeforeCreate = checkInRepository.findAll().size();

        // Create the CheckIn
        restCheckInMockMvc.perform(post("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkIn)))
            .andExpect(status().isCreated());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate + 1);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getUserID()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testCheckIn.getTimeCheckIn()).isEqualTo(DEFAULT_TIME_CHECK_IN);
    }

    @Test
    @Transactional
    public void createCheckInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkInRepository.findAll().size();

        // Create the CheckIn with an existing ID
        checkIn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInMockMvc.perform(post("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkIn)))
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCheckIns() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get all the checkInList
        restCheckInMockMvc.perform(get("/api/check-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].timeCheckIn").value(hasItem(DEFAULT_TIME_CHECK_IN.toString())));
    }
    
    @Test
    @Transactional
    public void getCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get the checkIn
        restCheckInMockMvc.perform(get("/api/check-ins/{id}", checkIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkIn.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.timeCheckIn").value(DEFAULT_TIME_CHECK_IN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCheckIn() throws Exception {
        // Get the checkIn
        restCheckInMockMvc.perform(get("/api/check-ins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckIn() throws Exception {
        // Initialize the database
        checkInService.save(checkIn);

        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Update the checkIn
        CheckIn updatedCheckIn = checkInRepository.findById(checkIn.getId()).get();
        // Disconnect from session so that the updates on updatedCheckIn are not directly saved in db
        em.detach(updatedCheckIn);
        updatedCheckIn
            .userID(UPDATED_USER_ID)
            .timeCheckIn(UPDATED_TIME_CHECK_IN);

        restCheckInMockMvc.perform(put("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCheckIn)))
            .andExpect(status().isOk());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getUserID()).isEqualTo(UPDATED_USER_ID);
        assertThat(testCheckIn.getTimeCheckIn()).isEqualTo(UPDATED_TIME_CHECK_IN);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Create the CheckIn

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckInMockMvc.perform(put("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkIn)))
            .andExpect(status().isBadRequest());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCheckIn() throws Exception {
        // Initialize the database
        checkInService.save(checkIn);

        int databaseSizeBeforeDelete = checkInRepository.findAll().size();

        // Get the checkIn
        restCheckInMockMvc.perform(delete("/api/check-ins/{id}", checkIn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckIn.class);
        CheckIn checkIn1 = new CheckIn();
        checkIn1.setId(1L);
        CheckIn checkIn2 = new CheckIn();
        checkIn2.setId(checkIn1.getId());
        assertThat(checkIn1).isEqualTo(checkIn2);
        checkIn2.setId(2L);
        assertThat(checkIn1).isNotEqualTo(checkIn2);
        checkIn1.setId(null);
        assertThat(checkIn1).isNotEqualTo(checkIn2);
    }
}
