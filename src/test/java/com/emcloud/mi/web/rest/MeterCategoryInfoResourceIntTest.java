package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MeterCategoryInfo;
import com.emcloud.mi.repository.MeterCategoryInfoRepository;
import com.emcloud.mi.service.MeterCategoryInfoService;
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
 * Test class for the MeterCategoryInfoResource REST controller.
 *
 * @see MeterCategoryInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MeterCategoryInfoResourceIntTest {

    private static final Integer DEFAULT_METER_TYPE_CODE = 1;
    private static final Integer UPDATED_METER_TYPE_CODE = 2;

    private static final String DEFAULT_METER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_METER_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DICT_CODE = 1;
    private static final Integer UPDATED_DICT_CODE = 2;

    private static final String DEFAULT_DICT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DICT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FUNCTION_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_METER_FACTORY = "AAAAAAAAAA";
    private static final String UPDATED_METER_FACTORY = "BBBBBBBBBB";

    private static final Integer DEFAULT_TEL = 1;
    private static final Integer UPDATED_TEL = 2;

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

    private static final Integer DEFAULT_LONG_CODE = 1;
    private static final Integer UPDATED_LONG_CODE = 2;

    private static final Boolean DEFAULT_ENABLE = false;
    private static final Boolean UPDATED_ENABLE = true;

    @Autowired
    private MeterCategoryInfoRepository meterCategoryInfoRepository;

    @Autowired
    private MeterCategoryInfoService meterCategoryInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMeterCategoryInfoMockMvc;

    private MeterCategoryInfo meterCategoryInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MeterCategoryInfoResource meterCategoryInfoResource = new MeterCategoryInfoResource(meterCategoryInfoService);
        this.restMeterCategoryInfoMockMvc = MockMvcBuilders.standaloneSetup(meterCategoryInfoResource)
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
    public static MeterCategoryInfo createEntity(EntityManager em) {
        MeterCategoryInfo meterCategoryInfo = new MeterCategoryInfo()
            .meterTypeCode(DEFAULT_METER_TYPE_CODE)
            .meterType(DEFAULT_METER_TYPE)
            .dictCode(DEFAULT_DICT_CODE)
            .dictName(DEFAULT_DICT_NAME)
            .functionCode(DEFAULT_FUNCTION_CODE)
            .meterFactory(DEFAULT_METER_FACTORY)
            .tel(DEFAULT_TEL)
            .startOffset(DEFAULT_START_OFFSET)
            .numberOfRegisters(DEFAULT_NUMBER_OF_REGISTERS)
            .controlAddress(DEFAULT_CONTROL_ADDRESS)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .controlCommands(DEFAULT_CONTROL_COMMANDS)
            .longCode(DEFAULT_LONG_CODE)
            .enable(DEFAULT_ENABLE);
        return meterCategoryInfo;
    }

    @Before
    public void initTest() {
        meterCategoryInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMeterCategoryInfo() throws Exception {
        int databaseSizeBeforeCreate = meterCategoryInfoRepository.findAll().size();

        // Create the MeterCategoryInfo
        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterCategoryInfo in the database
        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MeterCategoryInfo testMeterCategoryInfo = meterCategoryInfoList.get(meterCategoryInfoList.size() - 1);
        assertThat(testMeterCategoryInfo.getMeterTypeCode()).isEqualTo(DEFAULT_METER_TYPE_CODE);
        assertThat(testMeterCategoryInfo.getMeterType()).isEqualTo(DEFAULT_METER_TYPE);
        assertThat(testMeterCategoryInfo.getDictCode()).isEqualTo(DEFAULT_DICT_CODE);
        assertThat(testMeterCategoryInfo.getDictName()).isEqualTo(DEFAULT_DICT_NAME);
        assertThat(testMeterCategoryInfo.getFunctionCode()).isEqualTo(DEFAULT_FUNCTION_CODE);
        assertThat(testMeterCategoryInfo.getMeterFactory()).isEqualTo(DEFAULT_METER_FACTORY);
        assertThat(testMeterCategoryInfo.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testMeterCategoryInfo.getStartOffset()).isEqualTo(DEFAULT_START_OFFSET);
        assertThat(testMeterCategoryInfo.getNumberOfRegisters()).isEqualTo(DEFAULT_NUMBER_OF_REGISTERS);
        assertThat(testMeterCategoryInfo.getControlAddress()).isEqualTo(DEFAULT_CONTROL_ADDRESS);
        assertThat(testMeterCategoryInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMeterCategoryInfo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMeterCategoryInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMeterCategoryInfo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMeterCategoryInfo.getControlCommands()).isEqualTo(DEFAULT_CONTROL_COMMANDS);
        assertThat(testMeterCategoryInfo.getLongCode()).isEqualTo(DEFAULT_LONG_CODE);
        assertThat(testMeterCategoryInfo.isEnable()).isEqualTo(DEFAULT_ENABLE);
    }

    @Test
    @Transactional
    public void createMeterCategoryInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = meterCategoryInfoRepository.findAll().size();

        // Create the MeterCategoryInfo with an existing ID
        meterCategoryInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MeterCategoryInfo in the database
        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMeterTypeCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setMeterTypeCode(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setMeterType(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDictCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setDictCode(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDictNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setDictName(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFunctionCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setFunctionCode(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMeterFactoryIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setMeterFactory(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartOffsetIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setStartOffset(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumberOfRegistersIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setNumberOfRegisters(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkControlAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setControlAddress(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setCreatedBy(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setCreateTime(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setUpdatedBy(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setUpdateTime(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEnableIsRequired() throws Exception {
        int databaseSizeBeforeTest = meterCategoryInfoRepository.findAll().size();
        // set the field null
        meterCategoryInfo.setEnable(null);

        // Create the MeterCategoryInfo, which fails.

        restMeterCategoryInfoMockMvc.perform(post("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isBadRequest());

        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMeterCategoryInfos() throws Exception {
        // Initialize the database
        meterCategoryInfoRepository.saveAndFlush(meterCategoryInfo);

        // Get all the meterCategoryInfoList
        restMeterCategoryInfoMockMvc.perform(get("/api/meter-category-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(meterCategoryInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].meterTypeCode").value(hasItem(DEFAULT_METER_TYPE_CODE)))
            .andExpect(jsonPath("$.[*].meterType").value(hasItem(DEFAULT_METER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dictCode").value(hasItem(DEFAULT_DICT_CODE)))
            .andExpect(jsonPath("$.[*].dictName").value(hasItem(DEFAULT_DICT_NAME.toString())))
            .andExpect(jsonPath("$.[*].functionCode").value(hasItem(DEFAULT_FUNCTION_CODE.toString())))
            .andExpect(jsonPath("$.[*].meterFactory").value(hasItem(DEFAULT_METER_FACTORY.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].startOffset").value(hasItem(DEFAULT_START_OFFSET)))
            .andExpect(jsonPath("$.[*].numberOfRegisters").value(hasItem(DEFAULT_NUMBER_OF_REGISTERS)))
            .andExpect(jsonPath("$.[*].controlAddress").value(hasItem(DEFAULT_CONTROL_ADDRESS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(DEFAULT_UPDATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].controlCommands").value(hasItem(DEFAULT_CONTROL_COMMANDS.toString())))
            .andExpect(jsonPath("$.[*].longCode").value(hasItem(DEFAULT_LONG_CODE)))
            .andExpect(jsonPath("$.[*].enable").value(hasItem(DEFAULT_ENABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getMeterCategoryInfo() throws Exception {
        // Initialize the database
        meterCategoryInfoRepository.saveAndFlush(meterCategoryInfo);

        // Get the meterCategoryInfo
        restMeterCategoryInfoMockMvc.perform(get("/api/meter-category-infos/{id}", meterCategoryInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(meterCategoryInfo.getId().intValue()))
            .andExpect(jsonPath("$.meterTypeCode").value(DEFAULT_METER_TYPE_CODE))
            .andExpect(jsonPath("$.meterType").value(DEFAULT_METER_TYPE.toString()))
            .andExpect(jsonPath("$.dictCode").value(DEFAULT_DICT_CODE))
            .andExpect(jsonPath("$.dictName").value(DEFAULT_DICT_NAME.toString()))
            .andExpect(jsonPath("$.functionCode").value(DEFAULT_FUNCTION_CODE.toString()))
            .andExpect(jsonPath("$.meterFactory").value(DEFAULT_METER_FACTORY.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.startOffset").value(DEFAULT_START_OFFSET))
            .andExpect(jsonPath("$.numberOfRegisters").value(DEFAULT_NUMBER_OF_REGISTERS))
            .andExpect(jsonPath("$.controlAddress").value(DEFAULT_CONTROL_ADDRESS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(DEFAULT_UPDATE_TIME.toString()))
            .andExpect(jsonPath("$.controlCommands").value(DEFAULT_CONTROL_COMMANDS.toString()))
            .andExpect(jsonPath("$.longCode").value(DEFAULT_LONG_CODE))
            .andExpect(jsonPath("$.enable").value(DEFAULT_ENABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMeterCategoryInfo() throws Exception {
        // Get the meterCategoryInfo
        restMeterCategoryInfoMockMvc.perform(get("/api/meter-category-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMeterCategoryInfo() throws Exception {
        // Initialize the database
        meterCategoryInfoService.save(meterCategoryInfo);

        int databaseSizeBeforeUpdate = meterCategoryInfoRepository.findAll().size();

        // Update the meterCategoryInfo
        MeterCategoryInfo updatedMeterCategoryInfo = meterCategoryInfoRepository.findOne(meterCategoryInfo.getId());
        updatedMeterCategoryInfo
            .meterTypeCode(UPDATED_METER_TYPE_CODE)
            .meterType(UPDATED_METER_TYPE)
            .dictCode(UPDATED_DICT_CODE)
            .dictName(UPDATED_DICT_NAME)
            .functionCode(UPDATED_FUNCTION_CODE)
            .meterFactory(UPDATED_METER_FACTORY)
            .tel(UPDATED_TEL)
            .startOffset(UPDATED_START_OFFSET)
            .numberOfRegisters(UPDATED_NUMBER_OF_REGISTERS)
            .controlAddress(UPDATED_CONTROL_ADDRESS)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .controlCommands(UPDATED_CONTROL_COMMANDS)
            .longCode(UPDATED_LONG_CODE)
            .enable(UPDATED_ENABLE);

        restMeterCategoryInfoMockMvc.perform(put("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMeterCategoryInfo)))
            .andExpect(status().isOk());

        // Validate the MeterCategoryInfo in the database
        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeUpdate);
        MeterCategoryInfo testMeterCategoryInfo = meterCategoryInfoList.get(meterCategoryInfoList.size() - 1);
        assertThat(testMeterCategoryInfo.getMeterTypeCode()).isEqualTo(UPDATED_METER_TYPE_CODE);
        assertThat(testMeterCategoryInfo.getMeterType()).isEqualTo(UPDATED_METER_TYPE);
        assertThat(testMeterCategoryInfo.getDictCode()).isEqualTo(UPDATED_DICT_CODE);
        assertThat(testMeterCategoryInfo.getDictName()).isEqualTo(UPDATED_DICT_NAME);
        assertThat(testMeterCategoryInfo.getFunctionCode()).isEqualTo(UPDATED_FUNCTION_CODE);
        assertThat(testMeterCategoryInfo.getMeterFactory()).isEqualTo(UPDATED_METER_FACTORY);
        assertThat(testMeterCategoryInfo.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testMeterCategoryInfo.getStartOffset()).isEqualTo(UPDATED_START_OFFSET);
        assertThat(testMeterCategoryInfo.getNumberOfRegisters()).isEqualTo(UPDATED_NUMBER_OF_REGISTERS);
        assertThat(testMeterCategoryInfo.getControlAddress()).isEqualTo(UPDATED_CONTROL_ADDRESS);
        assertThat(testMeterCategoryInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMeterCategoryInfo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMeterCategoryInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMeterCategoryInfo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMeterCategoryInfo.getControlCommands()).isEqualTo(UPDATED_CONTROL_COMMANDS);
        assertThat(testMeterCategoryInfo.getLongCode()).isEqualTo(UPDATED_LONG_CODE);
        assertThat(testMeterCategoryInfo.isEnable()).isEqualTo(UPDATED_ENABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingMeterCategoryInfo() throws Exception {
        int databaseSizeBeforeUpdate = meterCategoryInfoRepository.findAll().size();

        // Create the MeterCategoryInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMeterCategoryInfoMockMvc.perform(put("/api/meter-category-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(meterCategoryInfo)))
            .andExpect(status().isCreated());

        // Validate the MeterCategoryInfo in the database
        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMeterCategoryInfo() throws Exception {
        // Initialize the database
        meterCategoryInfoService.save(meterCategoryInfo);

        int databaseSizeBeforeDelete = meterCategoryInfoRepository.findAll().size();

        // Get the meterCategoryInfo
        restMeterCategoryInfoMockMvc.perform(delete("/api/meter-category-infos/{id}", meterCategoryInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MeterCategoryInfo> meterCategoryInfoList = meterCategoryInfoRepository.findAll();
        assertThat(meterCategoryInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MeterCategoryInfo.class);
        MeterCategoryInfo meterCategoryInfo1 = new MeterCategoryInfo();
        meterCategoryInfo1.setId(1L);
        MeterCategoryInfo meterCategoryInfo2 = new MeterCategoryInfo();
        meterCategoryInfo2.setId(meterCategoryInfo1.getId());
        assertThat(meterCategoryInfo1).isEqualTo(meterCategoryInfo2);
        meterCategoryInfo2.setId(2L);
        assertThat(meterCategoryInfo1).isNotEqualTo(meterCategoryInfo2);
        meterCategoryInfo1.setId(null);
        assertThat(meterCategoryInfo1).isNotEqualTo(meterCategoryInfo2);
    }
}
