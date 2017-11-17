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
 * 多路开关状态表
 * @author youhong
 */
@ApiModel(description = "多路开关状态表 @author youhong")
@Entity
@Table(name = "multiway_switch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiwaySwitch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备编码 是MultiwaySwitchInfo外键
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "设备编码 是MultiwaySwitchInfo外键", required = true)
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
     * 开关状态
     */
    @ApiModelProperty(value = "开关状态")
    @Column(name = "switch_status")
    private Integer switchStatus;

    /**
     * 记录时间
     */
    @ApiModelProperty(value = "记录时间")
    @Column(name = "record_time")
    private Instant recordTime;

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

    public MultiwaySwitch meterCode(String meterCode) {
        this.meterCode = meterCode;
        return this;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public Integer getSwitchCode() {
        return switchCode;
    }

    public MultiwaySwitch switchCode(Integer switchCode) {
        this.switchCode = switchCode;
        return this;
    }

    public void setSwitchCode(Integer switchCode) {
        this.switchCode = switchCode;
    }

    public Integer getSwitchStatus() {
        return switchStatus;
    }

    public MultiwaySwitch switchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
        return this;
    }

    public void setSwitchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
    }

    public Instant getRecordTime() {
        return recordTime;
    }

    public MultiwaySwitch recordTime(Instant recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public void setRecordTime(Instant recordTime) {
        this.recordTime = recordTime;
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
        MultiwaySwitch multiwaySwitch = (MultiwaySwitch) o;
        if (multiwaySwitch.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), multiwaySwitch.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MultiwaySwitch{" +
            "id=" + getId() +
            ", meterCode='" + getMeterCode() + "'" +
            ", switchCode='" + getSwitchCode() + "'" +
            ", switchStatus='" + getSwitchStatus() + "'" +
            ", recordTime='" + getRecordTime() + "'" +
            "}";
    }
}
