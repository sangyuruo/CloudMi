package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MultiwaySwitch;
import com.emcloud.mi.repository.MultiwaySwitchRepository;
import com.emcloud.mi.service.MultiwaySwitchService;
import com.emcloud.mi.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.emcloud.mi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MultiwaySwitchResource REST controller.
 *
 * @see MultiwaySwitchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MultiwaySwitchResourceIntTest {

    private static final String DEFAULT_METER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_METER_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SWITCH_CODE = 1;
    private static final Integer UPDATED_SWITCH_CODE = 2;

    private static final Integer DEFAULT_SWITCH_STATUS = 1;
    private static final Integer UPDATED_SWITCH_STATUS = 2;

    private static final Instant DEFAULT_RECORD_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECORD_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MultiwaySwitchRepository multiwaySwitchRepository;

    @Autowired
    private MultiwaySwitchService multiwaySwitchService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMultiwaySwitchMockMvc;

    private MultiwaySwitch multiwaySwitch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MultiwaySwitchResource multiwaySwitchResource = new MultiwaySwitchResource(multiwaySwitchService);
        this.restMultiwaySwitchMockMvc = MockMvcBuilders.standaloneSetup(multiwaySwitchResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MultiwaySwitch createEntity(EntityManager em) {
        MultiwaySwitch multiwaySwitch = new MultiwaySwitch()
            .meterCode(DEFAULT_METER_CODE)
            .switchCode(DEFAULT_SWITCH_CODE)
            .switchStatus(DEFAULT_SWITCH_STATUS)
            .recordTime(DEFAULT_RECORD_TIME);
        return multiwaySwitch;
    }

    @Before
    public void initTest() {
        multiwaySwitch = createEntity(em);
    }

    @Test
    @Transactional
    public void createMultiwaySwitch() throws Exception {
        int databaseSizeBeforeCreate = multiwaySwitchRepository.findAll().size();

        // Create the MultiwaySwitch
        restMultiwaySwitchMockMvc.perform(post("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitch)))
            .andExpect(status().isCreated());

        // Validate the MultiwaySwitch in the database
        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeCreate + 1);
        MultiwaySwitch testMultiwaySwitch = multiwaySwitchList.get(multiwaySwitchList.size() - 1);
        assertThat(testMultiwaySwitch.getMeterCode()).isEqualTo(DEFAULT_METER_CODE);
        assertThat(testMultiwaySwitch.getSwitchCode()).isEqualTo(DEFAULT_SWITCH_CODE);
        assertThat(testMultiwaySwitch.getSwitchStatus()).isEqualTo(DEFAULT_SWITCH_STATUS);
        assertThat(testMultiwaySwitch.getRecordTime()).isEqualTo(DEFAULT_RECORD_TIME);
    }

    @Test
    @Transactional
    public void createMultiwaySwitchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = multiwaySwitchRepository.findAll().size();

        // Create the MultiwaySwitch with an existing ID
        multiwaySwitch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMultiwaySwitchMockMvc.perform(post("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitch)))
            .andExpect(status().isBadRequest());

        // Validate the MultiwaySwitch in the database
        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchRepository.findAll().size();
        // set the field null
        multiwaySwitch.setMeterCode(null);

        // Create the MultiwaySwitch, which fails.

        restMultiwaySwitchMockMvc.perform(post("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitch)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwitchCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchRepository.findAll().size();
        // set the field null
        multiwaySwitch.setSwitchCode(null);

        // Create the MultiwaySwitch, which fails.

        restMultiwaySwitchMockMvc.perform(post("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitch)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMultiwaySwitches() throws Exception {
        // Initialize the database
        multiwaySwitchRepository.saveAndFlush(multiwaySwitch);

        // Get all the multiwaySwitchList
        restMultiwaySwitchMockMvc.perform(get("/api/multiway-switches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(multiwaySwitch.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCode").value(hasItem(DEFAULT_METER_CODE.toString())))
            .andExpect(jsonPath("$.[*].switchCode").value(hasItem(DEFAULT_SWITCH_CODE)))
            .andExpect(jsonPath("$.[*].switchStatus").value(hasItem(DEFAULT_SWITCH_STATUS)))
            .andExpect(jsonPath("$.[*].recordTime").value(hasItem(DEFAULT_RECORD_TIME.toString())));
    }

    @Test
    @Transactional
    public void getMultiwaySwitch() throws Exception {
        // Initialize the database
        multiwaySwitchRepository.saveAndFlush(multiwaySwitch);

        // Get the multiwaySwitch
        restMultiwaySwitchMockMvc.perform(get("/api/multiway-switches/{id}", multiwaySwitch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(multiwaySwitch.getId().intValue()))
            .andExpect(jsonPath("$.meterCode").value(DEFAULT_METER_CODE.toString()))
            .andExpect(jsonPath("$.switchCode").value(DEFAULT_SWITCH_CODE))
            .andExpect(jsonPath("$.switchStatus").value(DEFAULT_SWITCH_STATUS))
            .andExpect(jsonPath("$.recordTime").value(DEFAULT_RECORD_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMultiwaySwitch() throws Exception {
        // Get the multiwaySwitch
        restMultiwaySwitchMockMvc.perform(get("/api/multiway-switches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultiwaySwitch() throws Exception {
        // Initialize the database
        multiwaySwitchService.save(multiwaySwitch);

        int databaseSizeBeforeUpdate = multiwaySwitchRepository.findAll().size();

        // Update the multiwaySwitch
        MultiwaySwitch updatedMultiwaySwitch = multiwaySwitchRepository.findOne(multiwaySwitch.getId());
        updatedMultiwaySwitch
            .meterCode(UPDATED_METER_CODE)
            .switchCode(UPDATED_SWITCH_CODE)
            .switchStatus(UPDATED_SWITCH_STATUS)
            .recordTime(UPDATED_RECORD_TIME);

        restMultiwaySwitchMockMvc.perform(put("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMultiwaySwitch)))
            .andExpect(status().isOk());

        // Validate the MultiwaySwitch in the database
        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeUpdate);
        MultiwaySwitch testMultiwaySwitch = multiwaySwitchList.get(multiwaySwitchList.size() - 1);
        assertThat(testMultiwaySwitch.getMeterCode()).isEqualTo(UPDATED_METER_CODE);
        assertThat(testMultiwaySwitch.getSwitchCode()).isEqualTo(UPDATED_SWITCH_CODE);
        assertThat(testMultiwaySwitch.getSwitchStatus()).isEqualTo(UPDATED_SWITCH_STATUS);
        assertThat(testMultiwaySwitch.getRecordTime()).isEqualTo(UPDATED_RECORD_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMultiwaySwitch() throws Exception {
        int databaseSizeBeforeUpdate = multiwaySwitchRepository.findAll().size();

        // Create the MultiwaySwitch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMultiwaySwitchMockMvc.perform(put("/api/multiway-switches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitch)))
            .andExpect(status().isCreated());

        // Validate the MultiwaySwitch in the database
        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMultiwaySwitch() throws Exception {
        // Initialize the database
        multiwaySwitchService.save(multiwaySwitch);

        int databaseSizeBeforeDelete = multiwaySwitchRepository.findAll().size();

        // Get the multiwaySwitch
        restMultiwaySwitchMockMvc.perform(delete("/api/multiway-switches/{id}", multiwaySwitch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MultiwaySwitch> multiwaySwitchList = multiwaySwitchRepository.findAll();
        assertThat(multiwaySwitchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MultiwaySwitch.class);
        MultiwaySwitch multiwaySwitch1 = new MultiwaySwitch();
        multiwaySwitch1.setId(1L);
        MultiwaySwitch multiwaySwitch2 = new MultiwaySwitch();
        multiwaySwitch2.setId(multiwaySwitch1.getId());
        assertThat(multiwaySwitch1).isEqualTo(multiwaySwitch2);
        multiwaySwitch2.setId(2L);
        assertThat(multiwaySwitch1).isNotEqualTo(multiwaySwitch2);
        multiwaySwitch1.setId(null);
        assertThat(multiwaySwitch1).isNotEqualTo(multiwaySwitch2);
    }
}
