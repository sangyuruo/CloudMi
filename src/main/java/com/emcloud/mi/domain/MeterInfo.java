package com.emcloud.mi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * 设备信息表
 */
@ApiModel(description = "设备信息表")
@Entity
@Table(name = "meter_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "meter_code", length = 200, nullable = false)
    private String meterCode;

    /**
     * 设备编码
     */
    @NotNull
    @Size(max = 200)
    @ApiModelProperty(value = "设备编码", required = true)
    @Column(name = "meter_name", length = 200, nullable = false)
    private String meterName;

    /**
     * 设备名称
     */
    @NotNull
    @ApiModelProperty(value = "设备名称", required = true)
    @Column(name = "register_code", nullable = false)
    private Integer registerCode;

    /**
     * 登记code
     */
    @Size(max = 64)
    @ApiModelProperty(value = "登记code")
    @Column(name = "address_code", length = 64)
    private String addressCode;

    /**
     * 地址编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "地址编码")
    @Column(name = "organization_code", length = 64)
    private String organizationCode;

    /**
     * 组织编码
     */
    @Size(max = 64)
    @ApiModelProperty(value = "组织编码")
    @Column(name = "company_code", length = 64)
    private String companyCode;

    /**
     * 公司编码
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "公司编码", required = true)
    @Column(name = "meter_type", length = 100, nullable = false)
    private String meterType;

    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型")
    @Column(name = "start_offset")
    private Integer startOffset;

    /**
     * 起始偏移
     */
    @ApiModelProperty(value = "起始偏移")
    @Column(name = "number_of_registers")
    private Integer numberOfRegisters;

    /**
     * 寄存器数量
     */
    @ApiModelProperty(value = "寄存器数量")
    @Column(name = "control_address")
    private Integer controlAddress;

    /**
     * 控制地址
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "控制地址", required = true)
    @Column(name = "created_by", length = 20, nullable = false)
    private String createdBy;

    /**
     * 创建人
     */
    @NotNull
    @ApiModelProperty(value = "创建人", required = true)
    @Column(name = "create_time", nullable = false)
    private ZonedDateTime createTime;

    /**
     * 创建时间
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "创建时间", required = true)
    @Column(name = "updated_by", length = 20, nullable = false)
    private String updatedBy;

    /**
     * 修改人
     */
    @NotNull
    @ApiModelProperty(value = "修改人", required = true)
    @Column(name = "update_time", nullable = false)
    private ZonedDateTime updateTime;

    /**
     * 修改时间
     */
    @Size(max = 100)
    @ApiModelProperty(value = "修改时间")
    @Column(name = "control_commands", length = 100)
    private String controlCommands;

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

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public MeterInfo createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
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

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public MeterInfo updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
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
            ", meterType='" + getMeterType() + "'" +
            ", startOffset='" + getStartOffset() + "'" +
            ", numberOfRegisters='" + getNumberOfRegisters() + "'" +
            ", controlAddress='" + getControlAddress() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", controlCommands='" + getControlCommands() + "'" +
            "}";
    }
}