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
 * 多路开关信息表
 * @author youhong
 */
@ApiModel(description = "多路开关信息表 @author youhong")
@Entity
@Table(name = "multiway_switch_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiwaySwitchInfo implements Serializable {

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
     * 开关序号
     */
    @NotNull
    @ApiModelProperty(value = "开关序号", required = true)
    @Column(name = "switch_code", nullable = false)
    private Integer switchCode;

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

    @ManyToOne
    private MeterInfo meterInfo;

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

    public MultiwaySwitchInfo meterCode(String meterCode) {
        this.meterCode = meterCode;
        return this;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public Integer getSwitchCode() {
        return switchCode;
    }

    public MultiwaySwitchInfo switchCode(Integer switchCode) {
        this.switchCode = switchCode;
        return this;
    }

    public void setSwitchCode(Integer switchCode) {
        this.switchCode = switchCode;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public MultiwaySwitchInfo createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public MultiwaySwitchInfo createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public MultiwaySwitchInfo updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public MultiwaySwitchInfo updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getControlCommands() {
        return controlCommands;
    }

    public MultiwaySwitchInfo controlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
        return this;
    }

    public void setControlCommands(String controlCommands) {
        this.controlCommands = controlCommands;
    }

    public MeterInfo getMeterInfo() {
        return meterInfo;
    }

    public MultiwaySwitchInfo meterInfo(MeterInfo meterInfo) {
        this.meterInfo = meterInfo;
        return this;
    }

    public void setMeterInfo(MeterInfo meterInfo) {
        this.meterInfo = meterInfo;
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
        MultiwaySwitchInfo multiwaySwitchInfo = (MultiwaySwitchInfo) o;
        if (multiwaySwitchInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), multiwaySwitchInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MultiwaySwitchInfo{" +
            "id=" + getId() +
            ", meterCode='" + getMeterCode() + "'" +
            ", switchCode='" + getSwitchCode() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", controlCommands='" + getControlCommands() + "'" +
            "}";
    }
}
