package com.emcloud.mi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * 设备分类信息表
 * @author youhong
 */
@ApiModel(description = "设备分类信息表 @author youhong")
@Entity
@Table(name = "meter_category_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterCategoryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备分类代码
     */
    @NotNull
    @ApiModelProperty(value = "设备分类代码", required = true)
    @Column(name = "meter_type_code", nullable = false)
    private Integer meterTypeCode;

    /**
     * 设备分类
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "设备分类", required = true)
    @Column(name = "meter_type", length = 64, nullable = false)
    private String meterType;

    /**
     * 设备大类代码
     */
    @NotNull
    @ApiModelProperty(value = "设备大类代码", required = true)
    @Column(name = "dict_code", nullable = false)
    private Integer dictCode;

    /**
     * 设备大类名称
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "设备大类名称", required = true)
    @Column(name = "dict_name", length = 100, nullable = false)
    private String dictName;

    /**
     * 功能码
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "功能码", required = true)
    @Column(name = "function_code", length = 100, nullable = false)
    private String functionCode;

    /**
     * 设备生产厂家
     */
    @NotNull
    @Size(max = 100)
    @ApiModelProperty(value = "设备生产厂家", required = true)
    @Column(name = "meter_factory", length = 100, nullable = false)
    private String meterFactory;

    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系方式")
    @Column(name = "tel")
    private Integer tel;

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
     * 控制区
     */
    @NotNull
    @ApiModelProperty(value = "控制区")
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
     * 长码
     */
    @ApiModelProperty(value = "长码")
    @Column(name = "long_code")
    private Integer longCode;

    /**
     * 是否有效
     */
    @NotNull
    @ApiModelProperty(value = "是否有效", required = true)
    @Column(name = "jhi_enable", nullable = false)
    private Boolean enable;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMeterTypeCode() {
        return meterTypeCode;
    }

    public MeterCategoryInfo meterTypeCode(Integer meterTypeCode) {
        this.meterTypeCode = meterTypeCode;
        return this;
    }

    public void setMeterTypeCode(Integer meterTypeCode) {
        this.meterTypeCode = meterTypeCode;
    }

    public String getMeterType() {
        return meterType;
    }

    public MeterCategoryInfo meterType(String meterType) {
        this.meterType = meterType;
        return this;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }

    public Integer getDictCode() {
        return dictCode;
    }

    public MeterCategoryInfo dictCode(Integer dictCode) {
        this.dictCode = dictCode;
        return this;
    }

    public void setDictCode(Integer dictCode) {
        this.dictCode = dictCode;
    }

    public String getDictName() {
        return dictName;
    }

    public MeterCategoryInfo dictName(String dictName) {
        this.dictName = dictName;
        return this;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public MeterCategoryInfo functionCode(String functionCode) {
        this.functionCode = functionCode;
        return this;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getMeterFactory() {
        return meterFactory;
    }

    public MeterCategoryInfo meterFactory(String meterFactory) {
        this.meterFactory = meterFactory;
        return this;
    }

    public void setMeterFactory(String meterFactory) {
        this.meterFactory = meterFactory;
    }

    public Integer getTel() {
        return tel;
    }

    public MeterCategoryInfo tel(Integer tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public Integer getStartOffset() {
        return startOffset;
    }

    public MeterCategoryInfo startOffset(Integer startOffset) {
        this.startOffset = startOffset;
        return this;
    }

    public void setStartOffset(Integer startOffset) {
        this.startOffset = startOffset;
    }

    public Integer getNumberOfRegisters() {
        return numberOfRegisters;
    }

    public MeterCategoryInfo numberOfRegisters(Integer numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
        return this;
    }

    public void setNumberOfRegisters(Integer numberOfRegisters) {
        this.numberOfRegisters = numberOfRegisters;
    }

    public Integer getControlAddress() {
        return controlAddress;
    }

    public MeterCategoryInfo controlAddress(Integer controlAddress) {
        this.controlAddress = controlAddress;
        return this;
    }

    public void setControlAddress(Integer controlAddress) {
        this.controlAddress = controlAddress;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MeterCategoryInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MeterCategoryInfo createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MeterCategoryInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MeterCategoryInfo updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getControlCommands() {
        return controlCommands;
    }

    public MeterCategoryInfo controlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
        return this;
    }

    public void setControlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
    }

    public Integer getLongCode() {
        return longCode;
    }

    public MeterCategoryInfo longCode(Integer longCode) {
        this.longCode = longCode;
        return this;
    }

    public void setLongCode(Integer longCode) {
        this.longCode = longCode;
    }

    public Boolean isEnable() {
        return enable;
    }

    public MeterCategoryInfo enable(Boolean enable) {
        this.enable = enable;
        return this;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
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
        MeterCategoryInfo meterCategoryInfo = (MeterCategoryInfo) o;
        if (meterCategoryInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meterCategoryInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeterCategoryInfo{" +
            "id=" + getId() +
            ", meterTypeCode='" + getMeterTypeCode() + "'" +
            ", meterType='" + getMeterType() + "'" +
            ", dictCode='" + getDictCode() + "'" +
            ", dictName='" + getDictName() + "'" +
            ", functionCode='" + getFunctionCode() + "'" +
            ", meterFactory='" + getMeterFactory() + "'" +
            ", tel='" + getTel() + "'" +
            ", startOffset='" + getStartOffset() + "'" +
            ", numberOfRegisters='" + getNumberOfRegisters() + "'" +
            ", controlAddress='" + getControlAddress() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", controlCommands='" + getControlCommands() + "'" +
            ", longCode='" + getLongCode() + "'" +
            ", enable='" + isEnable() + "'" +
            "}";
    }
}
