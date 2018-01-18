package com.emcloud.mi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * 设备信息表
 * @author youhong
 */
@ApiModel(description = "设备信息表 @author youhong")
@Entity
@Table(name = "meter_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备编码
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "设备编码", required = true)
    @Column(name = "meter_code", length = 64, nullable = false)
    private String meterCode;

    /**
     * 设备名称
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "设备名称", required = true)
    @Column(name = "meter_name", length = 200, nullable = false)
    private String meterName;

    /**
     * 登记code
     */
    @NotNull
    @ApiModelProperty(value = "登记code", required = true)
    @Column(name = "register_code", nullable = false)
    private Integer registerCode;

    /**
     * 地址编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "地址编码")
    @Column(name = "address_code", length = 64)
    private String addressCode;

    /**
     * 组织编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "组织编码")
    @Column(name = "organization_code", length = 64)
    private String organizationCode;

    /**
     * 公司编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "公司编码")
    @Column(name = "company_code", length = 64)
    private String companyCode;

    /**
     * 串口编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "串口编码")
    @Column(name = "com_point_code", length = 64)
    private String comPointCode;

    /**
     * 设备类型代码
     */
    @NotNull
    @ApiModelProperty(value = "设备类型代码", required = true)
    @Column(name = "meter_type_code", nullable = false)
    private Integer meterTypeCode;

    /**
     * 设备类型
     */
    @NotNull
    @Size(max = 50)
    @ApiModelProperty(value = "设备类型", required = true)
    @Column(name = "meter_type", length = 50, nullable = false)
    private String meterType;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @Column(name = "longitude")
    private Double longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @Column(name = "latitude")
    private Double latitude;

    /**
     * 起始偏移
     */
    @NotNull
    @ApiModelProperty(value = "起始偏移")
    @Column(name = "start_offset")
    private Integer startOffset;

    /**
     * 寄存器数量
     */
    @NotNull
    @ApiModelProperty(value = "寄存器数量")
    @Column(name = "number_of_registers")
    private Integer numberOfRegisters;

    /**
     * 控制地址
     */
    @NotNull
    @ApiModelProperty(value = "控制地址")
    @Column(name = "control_address")
    private Integer controlAddress;

    /**
     * 创建人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "create_time", nullable = false)
    private Instant createTime;

    /**
     * 修改人
     */
    @Size(max = 20)
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @Column(name = "update_time", nullable = false)
    private Instant updateTime;

    /**
     * 下发命令
     */
    @Size(max = 100)
    @ApiModelProperty(value = "下发命令")
    @Column(name = "control_commands", length = 100)
    private String controlCommands;

    /**
     * 大Endian
     */
    @Size(max = 100)
    @ApiModelProperty(value = "大Endian")
    @Column(name = "big_endian", length = 100)
    private String bigEndian;

    /**
     * 允许重复
     */
    @ApiModelProperty(value = "允许重复")
    @Column(name = "allow_duplicate")
    private Boolean allowDuplicate;

    /**
     * 计算
     */
    @ApiModelProperty(value = "计算")
    @Column(name = "calculates")
    private Integer calculates;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "enable", nullable = false)
    private Boolean enable;

    @OneToMany(mappedBy = "meterInfo")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MultiwaySwitchInfo> multiwaySwitchInfos = new HashSet<>();

    @ManyToOne
    private MeterCategoryInfo meterCategoryInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeterCode() {
        return meterCode;
    }

    public MeterInfo meterCode(String meterCode) {
        this.meterCode = meterCode;
        return this;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public String getMeterName() {
        return meterName;
    }

    public MeterInfo meterName(String meterName) {
        this.meterName = meterName;
        return this;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public Integer getRegisterCode() {
        return registerCode;
    }

    public MeterInfo registerCode(Integer registerCode) {
        this.registerCode = registerCode;
        return this;
    }

    public void setRegisterCode(Integer registerCode) {
        this.registerCode = registerCode;
    }

    public String getAddressCode() {
        return addressCode;
    }

    public MeterInfo addressCode(String addressCode) {
        this.addressCode = addressCode;
        return this;
    }

    public void setAddressCode(String addressCode) {
        this.addressCode = addressCode;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public MeterInfo organizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
        return this;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public MeterInfo companyCode(String companyCode) {
        this.companyCode = companyCode;
        return this;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getComPointCode() {
        return comPointCode;
    }

    public MeterInfo comPointCode(String comPointCode) {
        this.comPointCode = comPointCode;
        return this;
    }

    public void setComPointCode(String comPointCode) {
        this.comPointCode = comPointCode;
    }

    public Integer getMeterTypeCode() {
        return meterTypeCode;
    }

    public MeterInfo meterTypeCode(Integer meterTypeCode) {
        this.meterTypeCode = meterTypeCode;
        return this;
    }

    public void setMeterTypeCode(Integer meterTypeCode) {
        this.meterTypeCode = meterTypeCode;
    }

    public String getMeterType() {
        return meterType;
    }

    public MeterInfo meterType(String meterType) {
        this.meterType = meterType;
        return this;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public Double getLongitude() {
        return longitude;
    }

    public MeterInfo longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
    public MeterInfo latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Boolean getEnable() {
        return enable;
    }
    public MeterInfo enable(Boolean enable) {
        this.enable = enable;
        return this;
    }
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getStartOffset() {
        return startOffset;
    }

    public MeterInfo startOffset(Integer startOffset) {
        this.startOffset = startOffset;
        return this;
    }

    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    public Integer getNumberOfRegisters() {
        return numberOfRegisters;
    }

    public MeterInfo numberOfRegisters(Integer numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
        return this;
    }

    public void setNumberOfRegisters(Integer numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
    }

    public Integer getControlAddress() {
        return controlAddress;
    }

    public MeterInfo controlAddress(Integer controlAddress) {
        this.controlAddress = controlAddress;
        return this;
    }

    public void setControlAddress(Integer controlAddress) {
        this.controlAddress = controlAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MeterInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MeterInfo createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MeterInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MeterInfo updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getControlCommands() {
        return controlCommands;
    }

    public MeterInfo controlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
        return this;
    }

    public void setControlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
    }

    public String getBigEndian() {
        return bigEndian;
    }

    public MeterInfo bigEndian(String bigEndian) {
        this.bigEndian = bigEndian;
        return this;
    }

    public void setBigEndian(String bigEndian) {
        this.bigEndian = bigEndian;
    }

    public Boolean isAllowDuplicate() {
        return allowDuplicate;
    }

    public MeterInfo allowDuplicate(Boolean allowDuplicate) {
        this.allowDuplicate = allowDuplicate;
        return this;
    }

    public void setAllowDuplicate(Boolean allowDuplicate) {
        this.allowDuplicate = allowDuplicate;
    }

    public Integer getCalculates() {
        return calculates;
    }

    public MeterInfo calculates(Integer calculates) {
        this.calculates = calculates;
        return this;
    }

    public void setCalculates(Integer calculates) {
        this.calculates = calculates;
    }

    public Set<MultiwaySwitchInfo> getMultiwaySwitchInfos() {
        return multiwaySwitchInfos;
    }

    public MeterInfo multiwaySwitchInfos(Set<MultiwaySwitchInfo> multiwaySwitchInfos) {
        this.multiwaySwitchInfos = multiwaySwitchInfos;
        return this;
    }

    public MeterInfo addMultiwaySwitchInfo(MultiwaySwitchInfo multiwaySwitchInfo) {
        this.multiwaySwitchInfos.add(multiwaySwitchInfo);
        multiwaySwitchInfo.setMeterInfo(this);
        return this;
    }

    public MeterInfo removeMultiwaySwitchInfo(MultiwaySwitchInfo multiwaySwitchInfo) {
        this.multiwaySwitchInfos.remove(multiwaySwitchInfo);
        multiwaySwitchInfo.setMeterInfo(null);
        return this;
    }

    public void setMultiwaySwitchInfos(Set<MultiwaySwitchInfo> multiwaySwitchInfos) {
        this.multiwaySwitchInfos = multiwaySwitchInfos;
    }

    public MeterCategoryInfo getMeterCategoryInfo() {
        return meterCategoryInfo;
    }

    public MeterInfo meterCategoryInfo(MeterCategoryInfo meterCategoryInfo) {
        this.meterCategoryInfo = meterCategoryInfo;
        return this;
    }

    public void setMeterCategoryInfo(MeterCategoryInfo meterCategoryInfo) {
        this.meterCategoryInfo = meterCategoryInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeterInfo meterInfo = (MeterInfo) o;
        if (meterInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meterInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeterInfo{" +
            "id=" + getId() +
            ", meterCode='" + getMeterCode() + "'" +
            ", meterName='" + getMeterName() + "'" +
            ", registerCode='" + getRegisterCode() + "'" +
            ", addressCode='" + getAddressCode() + "'" +
            ", organizationCode='" + getOrganizationCode() + "'" +
            ", companyCode='" + getCompanyCode() + "'" +
            ", comPointCode='" + getComPointCode() + "'" +
            ", meterTypeCode='" + getMeterTypeCode() + "'" +
            ", meterType='" + getMeterType() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", startOffset='" + getStartOffset() + "'" +
            ", numberOfRegisters='" + getNumberOfRegisters() + "'" +
            ", controlAddress='" + getControlAddress() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", controlCommands='" + getControlCommands() + "'" +
            ", bigEndian='" + getBigEndian() + "'" +
            ", allowDuplicate='" + isAllowDuplicate() + "'" +
            ", calculates='" + getCalculates() + "'" +
            ", enable='" + getEnable() + "'" +
            "}";
    }
}
