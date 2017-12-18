package com.feo.domain.model.reportform;

import javax.persistence.*;

/**
 * 分析报告属性
 */
@Table(name = "hufu_analyze_properties")
@Entity
public class AnalysisProperties {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "strategy_id")
    private Long strategyId;
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
    private Integer groupRanksd;//集团排名

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
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

    public Integer getGroupRanksd() {
        return groupRanksd;
    }

    public void setGroupRanksd(Integer groupRanksd) {
        this.groupRanksd = groupRanksd;
    }

    public AnalysisProperties() {
    }

    @Override
    public String toString() {
        return "AnalysisProperties{" +
                "id=" + id +
                ", enforcedPolicies=" + enforcedPolicies +
                ", integrityProcess=" + integrityProcess +
                ", pinTurn=" + pinTurn +
                ", qualityNumber=" + qualityNumber +
                ", violationRate=" + violationRate +
                ", rpa=" + rpa +
                ", agents=" + agents +
                ", majors=" + majors +
                ", classType=" + classType +
                ", putAway=" + putAway +
                ", samplingRate=" + samplingRate +
                ", performRanking=" + performRanking +
                ", groupRanksd=" + groupRanksd +
                '}';
    }
}
