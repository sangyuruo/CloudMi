package com.emcloud.mi.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * 设备状态表
 */
@ApiModel(description = "设备状态表")
@Entity
@Table(name = "meter_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "meter_code", length = 200, nullable = false)
    private String meterCode;

    /**
     * 设备编码  是MeterInfo外键
     */
    @ApiModelProperty(value = "设备编码  是MeterInfo外键")
    @Column(name = "traffic_status")
    private Integer trafficStatus;

    /**
     * 通讯状态
     */
    @ApiModelProperty(value = "通讯状态")
    @Column(name = "switch_status")
    private Integer switchStatus;

    /**
     * 开关状态
     */
    @ApiModelProperty(value = "开关状态")
    @Column(name = "record_time")
    private Integer recordTime;

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

    public MeterStatus meterCode(String meterCode) {
        this.meterCode = meterCode;
        return this;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    public Integer getTrafficStatus() {
        return trafficStatus;
    }

    public MeterStatus trafficStatus(Integer trafficStatus) {
        this.trafficStatus = trafficStatus;
        return this;
    }

    public void setTrafficStatus(Integer trafficStatus) {
        this.trafficStatus = trafficStatus;
    }

    public Integer getSwitchStatus() {
        return switchStatus;
    }

    public MeterStatus switchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
        return this;
    }

    public void setSwitchStatus(Integer switchStatus) {
        this.switchStatus = switchStatus;
    }

    public Integer getRecordTime() {
        return recordTime;
    }

    public MeterStatus recordTime(Integer recordTime) {
        this.recordTime = recordTime;
        return this;
    }

    public void setRecordTime(Integer recordTime) {
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
        MeterStatus meterStatus = (MeterStatus) o;
        if (meterStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meterStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeterStatus{" +
            "id=" + getId() +
            ", meterCode='" + getMeterCode() + "'" +
            ", trafficStatus='" + getTrafficStatus() + "'" +
            ", switchStatus='" + getSwitchStatus() + "'" +
            ", recordTime='" + getRecordTime() + "'" +
            "}";
    }
}
