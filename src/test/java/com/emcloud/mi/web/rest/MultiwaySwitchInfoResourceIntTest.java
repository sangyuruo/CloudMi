package com.emcloud.mi.web.rest;

import com.emcloud.mi.EmCloudMiApp;

import com.emcloud.mi.config.SecurityBeanOverrideConfiguration;

import com.emcloud.mi.domain.MultiwaySwitchInfo;
import com.emcloud.mi.repository.MultiwaySwitchInfoRepository;
import com.emcloud.mi.service.MultiwaySwitchInfoService;
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
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.emcloud.mi.web.rest.TestUtil.sameInstant;
import static com.emcloud.mi.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MultiwaySwitchInfoResource REST controller.
 *
 * @see MultiwaySwitchInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmCloudMiApp.class, SecurityBeanOverrideConfiguration.class})
public class MultiwaySwitchInfoResourceIntTest {

    private static final String DEFAULT_MULTIWAY_SWITCHCODE = "AAAAAAAAAA";
    private static final String UPDATED_MULTIWAY_SWITCHCODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SWITCH_CODE = 1;
    private static final Integer UPDATED_SWITCH_CODE = 2;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CONTROL_COMMANDS = "AAAAAAAAAA";
    private static final String UPDATED_CONTROL_COMMANDS = "BBBBBBBBBB";

    @Autowired
    private MultiwaySwitchInfoRepository multiwaySwitchInfoRepository;

    @Autowired
    private MultiwaySwitchInfoService multiwaySwitchInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMultiwaySwitchInfoMockMvc;

    private MultiwaySwitchInfo multiwaySwitchInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MultiwaySwitchInfoResource multiwaySwitchInfoResource = new MultiwaySwitchInfoResource(multiwaySwitchInfoService);
        this.restMultiwaySwitchInfoMockMvc = MockMvcBuilders.standaloneSetup(multiwaySwitchInfoResource)
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
    public static MultiwaySwitchInfo createEntity(EntityManager em) {
        MultiwaySwitchInfo multiwaySwitchInfo = new MultiwaySwitchInfo()
            .multiwaySwitchcode(DEFAULT_MULTIWAY_SWITCHCODE)
            .switchCode(DEFAULT_SWITCH_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createTime(DEFAULT_CREATE_TIME)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updateTime(DEFAULT_UPDATE_TIME)
            .controlCommands(DEFAULT_CONTROL_COMMANDS);
        return multiwaySwitchInfo;
    }

    @Before
    public void initTest() {
        multiwaySwitchInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createMultiwaySwitchInfo() throws Exception {
        int databaseSizeBeforeCreate = multiwaySwitchInfoRepository.findAll().size();

        // Create the MultiwaySwitchInfo
        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isCreated());

        // Validate the MultiwaySwitchInfo in the database
        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeCreate + 1);
        MultiwaySwitchInfo testMultiwaySwitchInfo = multiwaySwitchInfoList.get(multiwaySwitchInfoList.size() - 1);
        assertThat(testMultiwaySwitchInfo.getMultiwaySwitchcode()).isEqualTo(DEFAULT_MULTIWAY_SWITCHCODE);
        assertThat(testMultiwaySwitchInfo.getSwitchCode()).isEqualTo(DEFAULT_SWITCH_CODE);
        assertThat(testMultiwaySwitchInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMultiwaySwitchInfo.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testMultiwaySwitchInfo.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testMultiwaySwitchInfo.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
        assertThat(testMultiwaySwitchInfo.getControlCommands()).isEqualTo(DEFAULT_CONTROL_COMMANDS);
    }

    @Test
    @Transactional
    public void createMultiwaySwitchInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = multiwaySwitchInfoRepository.findAll().size();

        // Create the MultiwaySwitchInfo with an existing ID
        multiwaySwitchInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        // Validate the MultiwaySwitchInfo in the database
        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMultiwaySwitchcodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setMultiwaySwitchcode(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSwitchCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setSwitchCode(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setCreatedBy(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setCreateTime(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setUpdatedBy(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUpdateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = multiwaySwitchInfoRepository.findAll().size();
        // set the field null
        multiwaySwitchInfo.setUpdateTime(null);

        // Create the MultiwaySwitchInfo, which fails.

        restMultiwaySwitchInfoMockMvc.perform(post("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isBadRequest());

        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMultiwaySwitchInfos() throws Exception {
        // Initialize the database
        multiwaySwitchInfoRepository.saveAndFlush(multiwaySwitchInfo);

        // Get all the multiwaySwitchInfoList
        restMultiwaySwitchInfoMockMvc.perform(get("/api/multiway-switch-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(multiwaySwitchInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].multiwaySwitchcode").value(hasItem(DEFAULT_MULTIWAY_SWITCHCODE.toString())))
            .andExpect(jsonPath("$.[*].switchCode").value(hasItem(DEFAULT_SWITCH_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))))
            .andExpect(jsonPath("$.[*].controlCommands").value(hasItem(DEFAULT_CONTROL_COMMANDS.toString())));
    }

    @Test
    @Transactional
    public void getMultiwaySwitchInfo() throws Exception {
        // Initialize the database
        multiwaySwitchInfoRepository.saveAndFlush(multiwaySwitchInfo);

        // Get the multiwaySwitchInfo
        restMultiwaySwitchInfoMockMvc.perform(get("/api/multiway-switch-infos/{id}", multiwaySwitchInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(multiwaySwitchInfo.getId().intValue()))
            .andExpect(jsonPath("$.multiwaySwitchcode").value(DEFAULT_MULTIWAY_SWITCHCODE.toString()))
            .andExpect(jsonPath("$.switchCode").value(DEFAULT_SWITCH_CODE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)))
            .andExpect(jsonPath("$.controlCommands").value(DEFAULT_CONTROL_COMMANDS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMultiwaySwitchInfo() throws Exception {
        // Get the multiwaySwitchInfo
        restMultiwaySwitchInfoMockMvc.perform(get("/api/multiway-switch-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMultiwaySwitchInfo() throws Exception {
        // Initialize the database
        multiwaySwitchInfoService.save(multiwaySwitchInfo);

        int databaseSizeBeforeUpdate = multiwaySwitchInfoRepository.findAll().size();

        // Update the multiwaySwitchInfo
        MultiwaySwitchInfo updatedMultiwaySwitchInfo = multiwaySwitchInfoRepository.findOne(multiwaySwitchInfo.getId());
        updatedMultiwaySwitchInfo
            .multiwaySwitchcode(UPDATED_MULTIWAY_SWITCHCODE)
            .switchCode(UPDATED_SWITCH_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createTime(UPDATED_CREATE_TIME)
            .updatedBy(UPDATED_UPDATED_BY)
            .updateTime(UPDATED_UPDATE_TIME)
            .controlCommands(UPDATED_CONTROL_COMMANDS);

        restMultiwaySwitchInfoMockMvc.perform(put("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMultiwaySwitchInfo)))
            .andExpect(status().isOk());

        // Validate the MultiwaySwitchInfo in the database
        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeUpdate);
        MultiwaySwitchInfo testMultiwaySwitchInfo = multiwaySwitchInfoList.get(multiwaySwitchInfoList.size() - 1);
        assertThat(testMultiwaySwitchInfo.getMultiwaySwitchcode()).isEqualTo(UPDATED_MULTIWAY_SWITCHCODE);
        assertThat(testMultiwaySwitchInfo.getSwitchCode()).isEqualTo(UPDATED_SWITCH_CODE);
        assertThat(testMultiwaySwitchInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMultiwaySwitchInfo.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testMultiwaySwitchInfo.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testMultiwaySwitchInfo.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
        assertThat(testMultiwaySwitchInfo.getControlCommands()).isEqualTo(UPDATED_CONTROL_COMMANDS);
    }

    @Test
    @Transactional
    public void updateNonExistingMultiwaySwitchInfo() throws Exception {
        int databaseSizeBeforeUpdate = multiwaySwitchInfoRepository.findAll().size();

        // Create the MultiwaySwitchInfo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMultiwaySwitchInfoMockMvc.perform(put("/api/multiway-switch-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(multiwaySwitchInfo)))
            .andExpect(status().isCreated());

        // Validate the MultiwaySwitchInfo in the database
        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMultiwaySwitchInfo() throws Exception {
        // Initialize the database
        multiwaySwitchInfoService.save(multiwaySwitchInfo);

        int databaseSizeBeforeDelete = multiwaySwitchInfoRepository.findAll().size();

        // Get the multiwaySwitchInfo
        restMultiwaySwitchInfoMockMvc.perform(delete("/api/multiway-switch-infos/{id}", multiwaySwitchInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MultiwaySwitchInfo> multiwaySwitchInfoList = multiwaySwitchInfoRepository.findAll();
        assertThat(multiwaySwitchInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MultiwaySwitchInfo.class);
        MultiwaySwitchInfo multiwaySwitchInfo1 = new MultiwaySwitchInfo();
        multiwaySwitchInfo1.setId(1L);
        MultiwaySwitchInfo multiwaySwitchInfo2 = new MultiwaySwitchInfo();
        multiwaySwitchInfo2.setId(multiwaySwitchInfo1.getId());
        assertThat(multiwaySwitchInfo1).isEqualTo(multiwaySwitchInfo2);
        multiwaySwitchInfo2.setId(2L);
        assertThat(multiwaySwitchInfo1).isNotEqualTo(multiwaySwitchInfo2);
        multiwaySwitchInfo1.setId(null);
        assertThat(multiwaySwitchInfo1).isNotEqualTo(multiwaySwitchInfo2);
    }
}
