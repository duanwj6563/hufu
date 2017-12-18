package com.feo.domain.model.reportform;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 质检综合周报
 */
@Entity
@Table(name = "hufu_quality_week")
public class QualityWeek {
    @Id
    @GeneratedValue
    private Long id;
    private String reportName;  //报告名称
    private String departmentName;  //事业部
    private String legion;  //军团
    private String area;//地区
    private Integer totalNumber;  //总机会数
    private Double linkBack;  //环比
    private Double enforcedPolicies;//策略执行率
    private Double integrityProcess;//流程完整度（流程完整性执行占比）
    private Double pinTurn;//销转
    private Integer qualityNumber;//质检抽检数（抽取数）
    private Double violationRate;//违规率
    private Integer rpa;//RPA
    private Double agents;//探需
    private Double majors;//专业（主推专业执行占比）
    private Double classType;//班型（主推班型执行占比）
    private Double putAway;//截杀（截杀策略执行占比）
    private Double samplingRate;//抽检比例（抽检率）
    private Integer performRanking;//执行排行（执行排名）
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;//创建时间

    public Long getId() {
        return id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getLegion() {
        return legion;
    }

    public void setLegion(String legion) {
        this.legion = legion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Double getLinkBack() {
        return linkBack;
    }

    public void setLinkBack(Double linkBack) {
        this.linkBack = linkBack;
    }

    public Double getEnforcedPolicies() {
        return enforcedPolicies;
    }

    public void setEnforcedPolicies(Double enforcedPolicies) {
        this.enforcedPolicies = enforcedPolicies;
    }

    public Double getIntegrityProcess() {
        return integrityProcess;
    }

    public void setIntegrityProcess(Double integrityProcess) {
        this.integrityProcess = integrityProcess;
    }

    public Double getPinTurn() {
        return pinTurn;
    }

    public void setPinTurn(Double pinTurn) {
        this.pinTurn = pinTurn;
    }

    public Integer getQualityNumber() {
        return qualityNumber;
    }

    public void setQualityNumber(Integer qualityNumber) {
        this.qualityNumber = qualityNumber;
    }

    public Double getViolationRate() {
        return violationRate;
    }

    public void setViolationRate(Double violationRate) {
        this.violationRate = violationRate;
    }

    public Integer getRpa() {
        return rpa;
    }

    public void setRpa(Integer rpa) {
        this.rpa = rpa;
    }

    public Double getAgents() {
        return agents;
    }

    public void setAgents(Double agents) {
        this.agents = agents;
    }

    public Double getMajors() {
        return majors;
    }

    public void setMajors(Double majors) {
        this.majors = majors;
    }

    public Double getClassType() {
        return classType;
    }

    public void setClassType(Double classType) {
        this.classType = classType;
    }

    public Double getPutAway() {
        return putAway;
    }

    public void setPutAway(Double putAway) {
        this.putAway = putAway;
    }

    public Double getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(Double samplingRate) {
        this.samplingRate = samplingRate;
    }

    public Integer getPerformRanking() {
        return performRanking;
    }

    public void setPerformRanking(Integer performRanking) {
        this.performRanking = performRanking;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
