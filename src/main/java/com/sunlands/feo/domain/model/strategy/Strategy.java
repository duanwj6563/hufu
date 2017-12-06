package com.sunlands.feo.domain.model.strategy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sunlands.feo.domain.event.EntityObject;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.model.reportform.AnalysisProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 策略实体
 */
@JsonIgnoreProperties(value = {"applydeparts", "strategyPhases", "strategyLogs", "advises"})
@Entity
@Table(name = "hufu_strategy")
public class Strategy extends EntityObject implements Serializable {

    @JsonIgnore
    @Transient
    private Integer _status;

    public Strategy(){
        _status = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //    @ManyToOne(fetch = FetchType.EAGER)
    //    @JoinColumn(name = "uid")
    //    创建者的id
    private Integer uid;

    /**
     * 策略审核状态 0-新建,1-待审核,2-审核通过,3-驳回,
     */
    @Column(name = "status")
    private Integer status;

    //    创建时间
    @Column(name = "create_date")
    private Date createDate;

    //    提交审核时间
    @Column(name = "submit_date")
    private Date submitDate;

    //    策略名称
    @Column(name = "name")
    private String name;

    //    适用军团名称
    private String groupName;

    //    项目：1、自考；2、教资；3、其他
    //    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @Column(name = "first_project_id")
    private Integer firstProjectId;

    //    适用部门
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hufu_strategy_applydepart", joinColumns = @JoinColumn(name = "strategy_id"), inverseJoinColumns = @JoinColumn(name = "apply_dept_id"))
    private List<Organization> applydeparts = new ArrayList<>();

    //    适用地域：1、一线城市（北上广深武）2、全国APP 3、全国SEM 4、二三线城市（外地）
    @Column(name = "area")
    private Integer area;

    //    策略阶段
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_id")
    private Set<StrategyPhase> strategyPhases = new HashSet<>();

    //    竞争对手
    @Column(name = "competitor")
    private String competitor;

    //    竞争内容
    @Column(name = "compare_content")
    private String compareContent;

    //    竞争对手价格
    @Column(name = "compare_price")
    private String comparePrice;

    //    竞争对手截杀
    @Column(name = "compare_kill")
    private String compareKill;

    //    竞争分析
    @Column(name = "analyze_advantage")
    private String analyzeAdvantage;

    //    策略操作记录
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_id")
    private Set<StrategyLog> strategyLogs = new HashSet<>();


    @PostLoad
    private void onPostLoad(){
        _status = this.getStatus();
    }

    private Date startTime;

    private Date updateTime;
    //
    private Integer submitNum;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private AnalysisProperties analysisProperties;

    public AnalysisProperties getAnalysisProperties() {
        return analysisProperties;
    }

    public void setAnalysisProperties(AnalysisProperties analysisProperties) {
        this.analysisProperties = analysisProperties;
    }

    public Integer getSubmitNum() {
        return submitNum;
    }

    public void setSubmitNum(Integer submitNum) {
        this.submitNum = submitNum;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<StrategyLog> getStrategyLogs() {
        return strategyLogs;
    }

    public void setStrategyLogs(Set<StrategyLog> strategyLogs) {
        this.strategyLogs = strategyLogs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Organization> getApplydeparts() {
        return applydeparts;
    }

    public void setApplydeparts(List<Organization> applydeparts) {
        this.applydeparts = applydeparts;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Set<StrategyPhase> getStrategyPhases() {
        return strategyPhases;
    }

    public void setStrategyPhases(Set<StrategyPhase> strategyPhases) {
        this.strategyPhases = strategyPhases;
    }

    public String getCompetitor() {
        return competitor;
    }

    public void setCompetitor(String competitor) {
        this.competitor = competitor;
    }

    public String getCompareContent() {
        return compareContent;
    }

    public void setCompareContent(String compareContent) {
        this.compareContent = compareContent;
    }

    public String getComparePrice() {
        return comparePrice;
    }

    public void setComparePrice(String comparePrice) {
        this.comparePrice = comparePrice;
    }

    public String getCompareKill() {
        return compareKill;
    }

    public void setCompareKill(String compareKill) {
        this.compareKill = compareKill;
    }

    public String getAnalyzeAdvantage() {
        return analyzeAdvantage;
    }

    public void setAnalyzeAdvantage(String analyzeAdvantage) {
        this.analyzeAdvantage = analyzeAdvantage;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFirstProjectId() {
        return firstProjectId;
    }

    public void setFirstProjectId(Integer firstProjectId) {
        this.firstProjectId = firstProjectId;
    }

    public Integer get_status() {
        return _status;
    }

    public Strategy set_status(Integer _status) {
        return this;
    }
}
