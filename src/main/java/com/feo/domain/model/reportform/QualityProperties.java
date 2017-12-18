package com.feo.domain.model.reportform;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 质检周报其他属性
 */
//@Entity
//@Table(name = "hufu_quality_properties")
public class QualityProperties implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private Integer update;//策略是否更新 0-否 1-是
    private Integer orderNumber;//订单数量
    private Integer qualityNumber;//总体质检数
    private Double qualityRate;//质检率
    private Integer violationsNumber;//违规数
    private Double violationsRate;//违规率
    private Double ring;//环比
    private Integer complainedSuccess;//申诉成功
    private Double successRate;//申诉成功率
    private Integer levelViolations;//一级违规
    private Integer secondaryViolations;//二级违规
    private Integer threeViolations;//三级违规
    private Double levelProportion;//一级占比
    private Double secondaryProportion;//二级占比
    private Double threeProportion;//三级占比
    private Integer fiction;//虚构政策
    private Double fictionProportion;//虚构政策占比
    private Integer passRate;//通过率
    private Double passProportion;//占比
    private Integer other;//其他
    private Double otherProportion;//占比

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUpdate() {
        return update;
    }

    public void setUpdate(Integer update) {
        this.update = update;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getQualityNumber() {
        return qualityNumber;
    }

    public void setQualityNumber(Integer qualityNumber) {
        this.qualityNumber = qualityNumber;
    }

    public Double getQualityRate() {
        return qualityRate;
    }

    public void setQualityRate(Double qualityRate) {
        this.qualityRate = qualityRate;
    }

    public Integer getViolationsNumber() {
        return violationsNumber;
    }

    public void setViolationsNumber(Integer violationsNumber) {
        this.violationsNumber = violationsNumber;
    }

    public Double getViolationsRate() {
        return violationsRate;
    }

    public void setViolationsRate(Double violationsRate) {
        this.violationsRate = violationsRate;
    }

    public Double getRing() {
        return ring;
    }

    public void setRing(Double ring) {
        this.ring = ring;
    }

    public Integer getComplainedSuccess() {
        return complainedSuccess;
    }

    public void setComplainedSuccess(Integer complainedSuccess) {
        this.complainedSuccess = complainedSuccess;
    }

    public Double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(Double successRate) {
        this.successRate = successRate;
    }

    public Integer getLevelViolations() {
        return levelViolations;
    }

    public void setLevelViolations(Integer levelViolations) {
        this.levelViolations = levelViolations;
    }

    public Integer getSecondaryViolations() {
        return secondaryViolations;
    }

    public void setSecondaryViolations(Integer secondaryViolations) {
        this.secondaryViolations = secondaryViolations;
    }

    public Integer getThreeViolations() {
        return threeViolations;
    }

    public void setThreeViolations(Integer threeViolations) {
        this.threeViolations = threeViolations;
    }

    public Double getLevelProportion() {
        return levelProportion;
    }

    public void setLevelProportion(Double levelProportion) {
        this.levelProportion = levelProportion;
    }

    public Double getSecondaryProportion() {
        return secondaryProportion;
    }

    public void setSecondaryProportion(Double secondaryProportion) {
        this.secondaryProportion = secondaryProportion;
    }

    public Double getThreeProportion() {
        return threeProportion;
    }

    public void setThreeProportion(Double threeProportion) {
        this.threeProportion = threeProportion;
    }

    public Integer getFiction() {
        return fiction;
    }

    public void setFiction(Integer fiction) {
        this.fiction = fiction;
    }

    public Double getFictionProportion() {
        return fictionProportion;
    }

    public void setFictionProportion(Double fictionProportion) {
        this.fictionProportion = fictionProportion;
    }

    public Integer getPassRate() {
        return passRate;
    }

    public void setPassRate(Integer passRate) {
        this.passRate = passRate;
    }

    public Double getPassProportion() {
        return passProportion;
    }

    public void setPassProportion(Double passProportion) {
        this.passProportion = passProportion;
    }

    public Integer getOther() {
        return other;
    }

    public void setOther(Integer other) {
        this.other = other;
    }

    public Double getOtherProportion() {
        return otherProportion;
    }

    public void setOtherProportion(Double otherProportion) {
        this.otherProportion = otherProportion;
    }

}
