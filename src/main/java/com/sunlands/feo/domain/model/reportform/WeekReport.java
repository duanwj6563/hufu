package com.sunlands.feo.domain.model.reportform;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 周工作量详情
 */
@Entity
@Table(name = "hufu_week_report")
public class WeekReport {
    @Id
    @GeneratedValue
    private Long id;
    private Long weekId;
    private String reportName;//报告名称
    private Date createDate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String creater;//创建人

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeekId() {
        return weekId;
    }

    public void setWeekId(Long weekId) {
        this.weekId = weekId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public WeekReport() {
    }
}
