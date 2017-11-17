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
 * 设备状态表
 * @author youhong
 */
@ApiModel(description = "设备状态表 @author youhong")
@Entity
@Table(name = "meter_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MeterStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备编码  是MeterInfo外键
     */
    @NotNull
    @Size(max = 64)
    @ApiModelProperty(value = "设备编码  是MeterInfo外键", required = true)
    @Column(name = "meter_code", length = 64, nullable = false)
    private String meterCode;

    /**
     * 通讯状态
     */
    @ApiModelProperty(value = "通讯状态")
    @Column(name = "traffic_status")
    private Integer trafficStatus;

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

    public Instant getRecordTime() {
        return recordTime;
    }

    public MeterStatus recordTime(Instant recordTime) {
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
