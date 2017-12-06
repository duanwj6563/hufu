package com.sunlands.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 问题
 */
@Entity
@Table(name = "hufu_question")
public class Question implements Serializable {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("问题一/二/三级")
    private Integer type;

    @ApiModelProperty("问题类型描述")
    private String description;

    @ApiModelProperty("问题类型一")
    private Long questionTypeOneId;

    @ApiModelProperty("问题类型一Name")
    private String questionTypeOneName;

    @ApiModelProperty("问题类型二")
    private Long questionTypeTwoId;

    @ApiModelProperty("问题类型二Name")
    private String questionTypeTwoName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getQuestionTypeOneId() {
        return questionTypeOneId;
    }

    public void setQuestionTypeOneId(Long questionTypeOneId) {
        this.questionTypeOneId = questionTypeOneId;
    }

    public String getQuestionTypeOneName() {
        return questionTypeOneName;
    }

    public void setQuestionTypeOneName(String questionTypeOneName) {
        this.questionTypeOneName = questionTypeOneName;
    }

    public Long getQuestionTypeTwoId() {
        return questionTypeTwoId;
    }

    public void setQuestionTypeTwoId(Long questionTypeTwoId) {
        this.questionTypeTwoId = questionTypeTwoId;
    }

    public String getQuestionTypeTwoName() {
        return questionTypeTwoName;
    }

    public void setQuestionTypeTwoName(String questionTypeTwoName) {
        this.questionTypeTwoName = questionTypeTwoName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
