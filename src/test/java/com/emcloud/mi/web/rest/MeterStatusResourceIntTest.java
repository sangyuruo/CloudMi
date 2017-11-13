package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MeterStatus;
import com.emcloud.mi.repository.MeterStatusRepository;
import com.emcloud.mi.service.MeterStatusService;
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
import java.util.List;

import static com.emcloud.mi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MeterStatusResource REST controller.
 *
 * @see MeterStatusResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterStatusResourceIntTest {

    private static final String DEFAULT_METER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_METER_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_TRAFFIC_STATUS = 1;
    private static final Integer UPDATED_TRAFFIC_STATUS = 2;

    private static final Integer DEFAULT_SWITCH_STATUS = 1;
    private static final Integer UPDATED_SWITCH_STATUS = 2;

    private static final Integer DEFAULT_RECORD_TIME = 1;
    private static final Integer UPDATED_RECORD_TIME = 2;

    @Autowired
    private MeterStatusRepository meterStatusRepository;

    @Autowired
    private MeterStatusService meterStatusService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterStatusMockMvc;

    private MeterStatus meterStatus;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterStatusResource meterStatusResource = new MeterStatusResource(meterStatusService);
        this.restMeterStatusMockMvc = MockMvcBuilders.standaloneSetup(meterStatusResource)
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
    public static MeterStatus createEntity(EntityManager em) {
        MeterStatus meterStatus = new MeterStatus()
            .meterCode(DEFAULT_METER_CODE)
            .trafficStatus(DEFAULT_TRAFFIC_STATUS)
            .switchStatus(DEFAULT_SWITCH_STATUS)
            .recordTime(DEFAULT_RECORD_TIME);
        return meterStatus;
    }

    @Before
    public void initTest() {
        meterStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterStatus() throws Exception {
        int databaseSizeBeforeCreate = meterStatusRepository.findAll().size();

        // Create the MeterStatus
        restMeterStatusMockMvc.perform(post("/api/meter-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterStatus)))
            .andExpect(status().isCreated());

        // Validate the MeterStatus in the database
        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeCreate + 1);
        MeterStatus testMeterStatus = meterStatusList.get(meterStatusList.size() - 1);
        assertThat(testMeterStatus.getMeterCode()).isEqualTo(DEFAULT_METER_CODE);
        assertThat(testMeterStatus.getTrafficStatus()).isEqualTo(DEFAULT_TRAFFIC_STATUS);
        assertThat(testMeterStatus.getSwitchStatus()).isEqualTo(DEFAULT_SWITCH_STATUS);
        assertThat(testMeterStatus.getRecordTime()).isEqualTo(DEFAULT_RECORD_TIME);
    }

    @Test
    @Transactional
    public void createMeterStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterStatusRepository.findAll().size();

        // Create the MeterStatus with an existing ID
        meterStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterStatusMockMvc.perform(post("/api/meter-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterStatus)))
            .andExpect(status().isBadRequest());

        // Validate the MeterStatus in the database
        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterStatusRepository.findAll().size();
        // set the field null
        meterStatus.setMeterCode(null);

        // Create the MeterStatus, which fails.

        restMeterStatusMockMvc.perform(post("/api/meter-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterStatus)))
            .andExpect(status().isBadRequest());

        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterStatuses() throws Exception {
        // Initialize the database
        meterStatusRepository.saveAndFlush(meterStatus);

        // Get all the meterStatusList
        restMeterStatusMockMvc.perform(get("/api/meter-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCode").value(hasItem(DEFAULT_METER_CODE.toString())))
            .andExpect(jsonPath("$.[*].trafficStatus").value(hasItem(DEFAULT_TRAFFIC_STATUS)))
            .andExpect(jsonPath("$.[*].switchStatus").value(hasItem(DEFAULT_SWITCH_STATUS)))
            .andExpect(jsonPath("$.[*].recordTime").value(hasItem(DEFAULT_RECORD_TIME)));
    }

    @Test
    @Transactional
    public void getMeterStatus() throws Exception {
        // Initialize the database
        meterStatusRepository.saveAndFlush(meterStatus);

        // Get the meterStatus
        restMeterStatusMockMvc.perform(get("/api/meter-statuses/{id}", meterStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterStatus.getId().intValue()))
            .andExpect(jsonPath("$.meterCode").value(DEFAULT_METER_CODE.toString()))
            .andExpect(jsonPath("$.trafficStatus").value(DEFAULT_TRAFFIC_STATUS))
            .andExpect(jsonPath("$.switchStatus").value(DEFAULT_SWITCH_STATUS))
            .andExpect(jsonPath("$.recordTime").value(DEFAULT_RECORD_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingMeterStatus() throws Exception {
        // Get the meterStatus
        restMeterStatusMockMvc.perform(get("/api/meter-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterStatus() throws Exception {
        // Initialize the database
        meterStatusService.save(meterStatus);

        int databaseSizeBeforeUpdate = meterStatusRepository.findAll().size();

        // Update the meterStatus
        MeterStatus updatedMeterStatus = meterStatusRepository.findOne(meterStatus.getId());
        updatedMeterStatus
            .meterCode(UPDATED_METER_CODE)
            .trafficStatus(UPDATED_TRAFFIC_STATUS)
            .switchStatus(UPDATED_SWITCH_STATUS)
            .recordTime(UPDATED_RECORD_TIME);

        restMeterStatusMockMvc.perform(put("/api/meter-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterStatus)))
            .andExpect(status().isOk());

        // Validate the MeterStatus in the database
        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeUpdate);
        MeterStatus testMeterStatus = meterStatusList.get(meterStatusList.size() - 1);
        assertThat(testMeterStatus.getMeterCode()).isEqualTo(UPDATED_METER_CODE);
        assertThat(testMeterStatus.getTrafficStatus()).isEqualTo(UPDATED_TRAFFIC_STATUS);
        assertThat(testMeterStatus.getSwitchStatus()).isEqualTo(UPDATED_SWITCH_STATUS);
        assertThat(testMeterStatus.getRecordTime()).isEqualTo(UPDATED_RECORD_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterStatus() throws Exception {
        int databaseSizeBeforeUpdate = meterStatusRepository.findAll().size();

        // Create the MeterStatus

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterStatusMockMvc.perform(put("/api/meter-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterStatus)))
            .andExpect(status().isCreated());

        // Validate the MeterStatus in the database
        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterStatus() throws Exception {
        // Initialize the database
        meterStatusService.save(meterStatus);

        int databaseSizeBeforeDelete = meterStatusRepository.findAll().size();

        // Get the meterStatus
        restMeterStatusMockMvc.perform(delete("/api/meter-statuses/{id}", meterStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterStatus> meterStatusList = meterStatusRepository.findAll();
        assertThat(meterStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterStatus.class);
        MeterStatus meterStatus1 = new MeterStatus();
        meterStatus1.setId(1L);
        MeterStatus meterStatus2 = new MeterStatus();
        meterStatus2.setId(meterStatus1.getId());
        assertThat(meterStatus1).isEqualTo(meterStatus2);
        meterStatus2.setId(2L);
        assertThat(meterStatus1).isNotEqualTo(meterStatus2);
        meterStatus1.setId(null);
        assertThat(meterStatus1).isNotEqualTo(meterStatus2);
    }
}
