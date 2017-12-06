package com.sunlands.feo.domain.model.reportform;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.sunlands.feo.domain.event.EntityObject;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分析报告
 */
@Entity
@Table(name = "hufu_strategy_analyze")
public class Analyze extends EntityObject implements Serializable {
    public interface analyzeSimpleView {
    }

    public interface analyzeDetailView extends analyzeSimpleView {

    }

    @Id
    @GeneratedValue()
    private Long id;
    @NotNull(message = "策略id不能为空")
    private Long strategyId;
    @NotBlank(message = "报告名称不能为空")
    private String reportName;//报告名称
    private String legion;//军团
    private String egatus;//军团长
    private String creater;//创建人
    private String issuesAndrec;//执行问题与建议
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserModules> userModules = new ArrayList<>();//用户模块
    private String userAnalysis;//用户分析
    private String questionWord;//问题话术
    private String suggestedWord;//建议话术
    private String weekEffect;//上周培训效果分析
    private String remark;//备注
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyDate;//修改时间
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AnalysisProperties analysisProperties;

    public AnalysisProperties getAnalysisProperties() {
        return analysisProperties;
    }

    public void setAnalysisProperties(AnalysisProperties analysisProperties) {
        this.analysisProperties = analysisProperties;
    }

    @JsonView(analyzeSimpleView.class)
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

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getLegion() {
        return legion;
    }

    public void setLegion(String legion) {
        this.legion = legion;
    }

    public String getEgatus() {
        return egatus;
    }

    public void setEgatus(String egatus) {
        this.egatus = egatus;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getIssuesAndrec() {
        return issuesAndrec;
    }

    public void setIssuesAndrec(String issuesAndrec) {
        this.issuesAndrec = issuesAndrec;
    }

    public List<UserModules> getUserModules() {
        return userModules;
    }

    public void setUserModules(List<UserModules> userModules) {
        this.userModules = userModules;
    }

    public String getUserAnalysis() {
        return userAnalysis;
    }

    public void setUserAnalysis(String userAnalysis) {
        this.userAnalysis = userAnalysis;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getQuestionWord() {
        return questionWord;
    }

    public void setQuestionWord(String questionWord) {
        this.questionWord = questionWord;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public void setSuggestedWord(String suggestedWord) {
        this.suggestedWord = suggestedWord;
    }

    public String getWeekEffect() {
        return weekEffect;
    }

    public void setWeekEffect(String weekEffect) {
        this.weekEffect = weekEffect;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Analyze() {
    }

    public Analyze(Long id, Long strategyId, String reportName, Date modifyDate) {
        this.strategyId = strategyId;
        this.id = id;
        this.reportName = reportName;
        this.modifyDate = modifyDate;
    }

    @Override
    public String toString() {
        return "Analyze{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", reportName='" + reportName + '\'' +
                ", legion='" + legion + '\'' +
                ", egatus='" + egatus + '\'' +
                ", creater='" + creater + '\'' +
                ", issuesAndrec='" + issuesAndrec + '\'' +
                ", userModules=" + userModules +
                ", userAnalysis='" + userAnalysis + '\'' +
                ", questionWord='" + questionWord + '\'' +
                ", suggestedWord='" + suggestedWord + '\'' +
                ", weekEffect='" + weekEffect + '\'' +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                '}';
    }
}
