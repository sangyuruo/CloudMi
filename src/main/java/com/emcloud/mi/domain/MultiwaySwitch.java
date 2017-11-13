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
 * 多路开关状态表
 */
@ApiModel(description = "多路开关状态表")
@Entity
@Table(name = "multiway_switch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiwaySwitch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "multiway_switchcode", length = 200, nullable = false)
    private String multiwaySwitchCode;

    /**
     * 设备编码 是MultiwaySwitchInfo外键
     */
    @NotNull
    @ApiModelProperty(value = "设备编码 是MultiwaySwitchInfo外键", required = true)
    @Column(name = "switch_code", nullable = false)
    private Integer switchCode;

    /**
     * 开关序号
     */
    @ApiModelProperty(value = "开关序号")
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

    public String getmultiwaySwitchCode() {
        return multiwaySwitchCode;
    }

    public MultiwaySwitch multiwaySwitchCode(String multiwaySwitchCode) {
        this.multiwaySwitchCode = multiwaySwitchCode;
        return this;
    }

    public void setmultiwaySwitchCode(String multiwaySwitchCode) {
        this.multiwaySwitchCode = multiwaySwitchCode;
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

    public Integer getRecordTime() {
        return recordTime;
    }

    public MultiwaySwitch recordTime(Integer recordTime) {
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
            ", multiwaySwitchCode='" + getmultiwaySwitchCode() + "'" +
            ", switchCode='" + getSwitchCode() + "'" +
            ", switchStatus='" + getSwitchStatus() + "'" +
            ", recordTime='" + getRecordTime() + "'" +
            "}";
    }
}
