package com.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 问题
 */
@Entity
@Table(name = "hufu_question_type")
public class QuestionType implements Serializable {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("问题类型名")
    private String name;

    @ApiModelProperty("问题code")
    private String code;

    @ApiModelProperty("父类Id")
    private Long parentId;

    @ApiModelProperty("问题类型1,2,3")
    private Integer type;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
