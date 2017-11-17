package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MeterInfo;
import com.emcloud.mi.repository.MeterInfoRepository;
import com.emcloud.mi.service.MeterInfoService;
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
 * Test class for the MeterInfoResource REST controller.
 *
 * @see MeterInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterInfoResourceIntTest {

    private static final String DEFAULT_METER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_METER_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_METER_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_REGISTER_CODE = 1;
    private static final Integer UPDATED_REGISTER_CODE = 2;

    private static final String DEFAULT_ADDRESS_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANIZATION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ORGANIZATION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_METER_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_OFFSET = 1;
    private static final Integer UPDATED_START_OFFSET = 2;

    private static final Integer DEFAULT_NUMBER_OF_REGISTERS = 1;
    private static final Integer UPDATED_NUMBER_OF_REGISTERS = 2;

    private static final Integer DEFAULT_CONTROL_ADDRESS = 1;
    private static final Integer UPDATED_CONTROL_ADDRESS = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTROL_COMMANDS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_COMMANDS = "BBBBBBBBBB";

    @Autowired
    private MeterInfoRepository meterInfoRepository;

    @Autowired
    private MeterInfoService meterInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterInfoMockMvc;

    private MeterInfo meterInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterInfoResource meterInfoResource = new MeterInfoResource(meterInfoService);
        this.restMeterInfoMockMvc = MockMvcBuilders.standaloneSetup(meterInfoResource)
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
    public static MeterInfo createEntity(EntityManager em) {
        MeterInfo meterInfo = new MeterInfo()
            .meterCode(DEFAULT_METER_CODE)
            .meterName(DEFAULT_METER_NAME)
            .registerCode(DEFAULT_REGISTER_CODE)
            .addressCode(DEFAULT_ADDRESS_CODE)
            .organizationCode(DEFAULT_ORGANIZATION_CODE)
            .companyCode(DEFAULT_COMPANY_CODE)
            .meterType(DEFAULT_METER_TYPE)
            .startOffset(DEFAULT_START_OFFSET)
            .numberOfRegisters(DEFAULT_NUMBER_OF_REGISTERS)
            .controlAddress(DEFAULT_CONTROL_ADDRESS)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .controlCommands(DEFAULT_CONTROL_COMMANDS);
        return meterInfo;
    }

    @Before
    public void initTest() {
        meterInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterInfo() throws Exception {
        int databaseSizeBeforeCreate = meterInfoRepository.findAll().size();

        // Create the MeterInfo
        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MeterInfo testMeterInfo = meterInfoList.get(meterInfoList.size() - 1);
        assertThat(testMeterInfo.getMeterCode()).isEqualTo(DEFAULT_METER_CODE);
        assertThat(testMeterInfo.getMeterName()).isEqualTo(DEFAULT_METER_NAME);
        assertThat(testMeterInfo.getRegisterCode()).isEqualTo(DEFAULT_REGISTER_CODE);
        assertThat(testMeterInfo.getAddressCode()).isEqualTo(DEFAULT_ADDRESS_CODE);
        assertThat(testMeterInfo.getOrganizationCode()).isEqualTo(DEFAULT_ORGANIZATION_CODE);
        assertThat(testMeterInfo.getCompanyCode()).isEqualTo(DEFAULT_COMPANY_CODE);
        assertThat(testMeterInfo.getMeterType()).isEqualTo(DEFAULT_METER_TYPE);
        assertThat(testMeterInfo.getStartOffset()).isEqualTo(DEFAULT_START_OFFSET);
        assertThat(testMeterInfo.getNumberOfRegisters()).isEqualTo(DEFAULT_NUMBER_OF_REGISTERS);
        assertThat(testMeterInfo.getControlAddress()).isEqualTo(DEFAULT_CONTROL_ADDRESS);
        assertThat(testMeterInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterInfo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterInfo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMeterInfo.getControlCommands()).isEqualTo(DEFAULT_CONTROL_COMMANDS);
    }

    @Test
    @Transactional
    public void createMeterInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterInfoRepository.findAll().size();

        // Create the MeterInfo with an existing ID
        meterInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterCode(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterName(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRegisterCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setRegisterCode(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setMeterType(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCreatedBy(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setCreateTime(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setUpdatedBy(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterInfoRepository.findAll().size();
        // set the field null
        meterInfo.setUpdateTime(null);

        // Create the MeterInfo, which fails.

        restMeterInfoMockMvc.perform(post("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isBadRequest());

        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterInfos() throws Exception {
        // Initialize the database
        meterInfoRepository.saveAndFlush(meterInfo);

        // Get all the meterInfoList
        restMeterInfoMockMvc.perform(get("/api/meter-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterCode").value(hasItem(DEFAULT_METER_CODE.toString())))
            .andExpect(jsonPath("$.[*].meterName").value(hasItem(DEFAULT_METER_NAME.toString())))
            .andExpect(jsonPath("$.[*].registerCode").value(hasItem(DEFAULT_REGISTER_CODE)))
            .andExpect(jsonPath("$.[*].addressCode").value(hasItem(DEFAULT_ADDRESS_CODE.toString())))
            .andExpect(jsonPath("$.[*].organizationCode").value(hasItem(DEFAULT_ORGANIZATION_CODE.toString())))
            .andExpect(jsonPath("$.[*].companyCode").value(hasItem(DEFAULT_COMPANY_CODE.toString())))
            .andExpect(jsonPath("$.[*].meterType").value(hasItem(DEFAULT_METER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].startOffset").value(hasItem(DEFAULT_START_OFFSET)))
            .andExpect(jsonPath("$.[*].numberOfRegisters").value(hasItem(DEFAULT_NUMBER_OF_REGISTERS)))
            .andExpect(jsonPath("$.[*].controlAddress").value(hasItem(DEFAULT_CONTROL_ADDRESS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].controlCommands").value(hasItem(DEFAULT_CONTROL_COMMANDS.toString())));
    }

    @Test
    @Transactional
    public void getMeterInfo() throws Exception {
        // Initialize the database
        meterInfoRepository.saveAndFlush(meterInfo);

        // Get the meterInfo
        restMeterInfoMockMvc.perform(get("/api/meter-infos/{id}", meterInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterInfo.getId().intValue()))
            .andExpect(jsonPath("$.meterCode").value(DEFAULT_METER_CODE.toString()))
            .andExpect(jsonPath("$.meterName").value(DEFAULT_METER_NAME.toString()))
            .andExpect(jsonPath("$.registerCode").value(DEFAULT_REGISTER_CODE))
            .andExpect(jsonPath("$.addressCode").value(DEFAULT_ADDRESS_CODE.toString()))
            .andExpect(jsonPath("$.organizationCode").value(DEFAULT_ORGANIZATION_CODE.toString()))
            .andExpect(jsonPath("$.companyCode").value(DEFAULT_COMPANY_CODE.toString()))
            .andExpect(jsonPath("$.meterType").value(DEFAULT_METER_TYPE.toString()))
            .andExpect(jsonPath("$.startOffset").value(DEFAULT_START_OFFSET))
            .andExpect(jsonPath("$.numberOfRegisters").value(DEFAULT_NUMBER_OF_REGISTERS))
            .andExpect(jsonPath("$.controlAddress").value(DEFAULT_CONTROL_ADDRESS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.controlCommands").value(DEFAULT_CONTROL_COMMANDS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMeterInfo() throws Exception {
        // Get the meterInfo
        restMeterInfoMockMvc.perform(get("/api/meter-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterInfo() throws Exception {
        // Initialize the database
        meterInfoService.save(meterInfo);

        int databaseSizeBeforeUpdate = meterInfoRepository.findAll().size();

        // Update the meterInfo
        MeterInfo updatedMeterInfo = meterInfoRepository.findOne(meterInfo.getId());
        updatedMeterInfo
            .meterCode(UPDATED_METER_CODE)
            .meterName(UPDATED_METER_NAME)
            .registerCode(UPDATED_REGISTER_CODE)
            .addressCode(UPDATED_ADDRESS_CODE)
            .organizationCode(UPDATED_ORGANIZATION_CODE)
            .companyCode(UPDATED_COMPANY_CODE)
            .meterType(UPDATED_METER_TYPE)
            .startOffset(UPDATED_START_OFFSET)
            .numberOfRegisters(UPDATED_NUMBER_OF_REGISTERS)
            .controlAddress(UPDATED_CONTROL_ADDRESS)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .controlCommands(UPDATED_CONTROL_COMMANDS);

        restMeterInfoMockMvc.perform(put("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterInfo)))
            .andExpect(status().isOk());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeUpdate);
        MeterInfo testMeterInfo = meterInfoList.get(meterInfoList.size() - 1);
        assertThat(testMeterInfo.getMeterCode()).isEqualTo(UPDATED_METER_CODE);
        assertThat(testMeterInfo.getMeterName()).isEqualTo(UPDATED_METER_NAME);
        assertThat(testMeterInfo.getRegisterCode()).isEqualTo(UPDATED_REGISTER_CODE);
        assertThat(testMeterInfo.getAddressCode()).isEqualTo(UPDATED_ADDRESS_CODE);
        assertThat(testMeterInfo.getOrganizationCode()).isEqualTo(UPDATED_ORGANIZATION_CODE);
        assertThat(testMeterInfo.getCompanyCode()).isEqualTo(UPDATED_COMPANY_CODE);
        assertThat(testMeterInfo.getMeterType()).isEqualTo(UPDATED_METER_TYPE);
        assertThat(testMeterInfo.getStartOffset()).isEqualTo(UPDATED_START_OFFSET);
        assertThat(testMeterInfo.getNumberOfRegisters()).isEqualTo(UPDATED_NUMBER_OF_REGISTERS);
        assertThat(testMeterInfo.getControlAddress()).isEqualTo(UPDATED_CONTROL_ADDRESS);
        assertThat(testMeterInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMeterInfo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMeterInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMeterInfo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMeterInfo.getControlCommands()).isEqualTo(UPDATED_CONTROL_COMMANDS);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterInfo() throws Exception {
        int databaseSizeBeforeUpdate = meterInfoRepository.findAll().size();

        // Create the MeterInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterInfoMockMvc.perform(put("/api/meter-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterInfo in the database
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterInfo() throws Exception {
        // Initialize the database
        meterInfoService.save(meterInfo);

        int databaseSizeBeforeDelete = meterInfoRepository.findAll().size();

        // Get the meterInfo
        restMeterInfoMockMvc.perform(delete("/api/meter-infos/{id}", meterInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterInfo> meterInfoList = meterInfoRepository.findAll();
        assertThat(meterInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterInfo.class);
        MeterInfo meterInfo1 = new MeterInfo();
        meterInfo1.setId(1L);
        MeterInfo meterInfo2 = new MeterInfo();
        meterInfo2.setId(meterInfo1.getId());
        assertThat(meterInfo1).isEqualTo(meterInfo2);
        meterInfo2.setId(2L);
        assertThat(meterInfo1).isNotEqualTo(meterInfo2);
        meterInfo1.setId(null);
        assertThat(meterInfo1).isNotEqualTo(meterInfo2);
    }
}
