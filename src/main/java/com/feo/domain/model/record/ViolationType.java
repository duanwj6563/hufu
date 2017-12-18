package com.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 违规类型
 */
@Entity
@Table(name = "hufu_violation_type")
public class ViolationType implements Serializable {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name="id",length=11,nullable = false)
    private Long id;

    @ApiModelProperty("违规类型名称")
    private String name;

    @ApiModelProperty("违规类型code")
    private String code;

    @ApiModelProperty("父类Id")
    private Long parentId;

    @ApiModelProperty("违规描述")
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
