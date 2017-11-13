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
 * 多路开关信息表
 */
@ApiModel(description = "多路开关信息表")
@Entity
@Table(name = "multiway_switch_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiwaySwitchInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "multiway_switch_code", length = 200, nullable = false)
    private String multiwaySwitchCode;

    /**
     * 设备编码
     */
    @NotNull
    @ApiModelProperty(value = "设备编码", required = true)
    @Column(name = "switch_code", nullable = false)
    private Integer switchCode;

    /**
     * 开关序号
     */
    @NotNull
    @Size(max = 20)
    @ApiModelProperty(value = "开关序号", required = true)
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

    public String getmultiwaySwitchCode() {
        return multiwaySwitchCode;
    }

    public MultiwaySwitchInfo multiwaySwitchCode(String multiwaySwitchCode) {
        this.multiwaySwitchCode = multiwaySwitchCode;
        return this;
    }

    public void setmultiwaySwitchCode(String multiwaySwitchCode) {
        this.multiwaySwitchCode = multiwaySwitchCode;
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

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public MultiwaySwitchInfo createTime(ZonedDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(ZonedDateTime createTime) {
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

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }

    public MultiwaySwitchInfo updateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
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
            ", multiwaySwitchCode='" + getmultiwaySwitchCode() + "'" +
            ", switchCode='" + getSwitchCode() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", controlCommands='" + getControlCommands() + "'" +
            "}";
    }
}
