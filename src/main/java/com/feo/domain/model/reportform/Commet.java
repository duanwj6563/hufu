package com.feo.domain.model.reportform;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 分析报告备注
 */
@Entity
@Table(name = "hufu_comment")
public class Commet {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull(message = "分析报告id不能为空")
    private Long analyzeId;
    @NotBlank(message = "评论信息不能为空")
    private String msg;//评论信息
    private String uid;//操作人
    private String name;//操作人姓名
    private String type;//类型
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date crtDate;//创建时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAnalyzeId() {
        return analyzeId;
    }

    public void setAnalyzeId(Long analyzeId) {
        this.analyzeId = analyzeId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Commet() {
    }
}
