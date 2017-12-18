package com.feo.port.rest.dto.strategy;

import com.feo.domain.model.orgInfo.Organization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StrategyReturn {

    private Long id;

    private Integer uid;

    /**
     * 策略审核状态 0-新建,1-待审核,2-审核通过,3-驳回,
     */
    private Integer status;

    /*
    是否存在分析报告0不存在 1存在
     */
    private Integer analyze;

    //    创建时间
    private Date createDate;

    //    提交审核时间
    private Date submitDate;

    //    策略名称
    private String name;

    //    适用军团名称
    private String groupName;

    //    项目：1、自考；2、教资；3、其他
    private String firstProjectName;

    //    适用部门
    private List<Organization> applydeparts = new ArrayList<>();

    //    适用地域：1、一线城市（北上广深武）2、全国APP 3、全国SEM 4、二三线城市（外地）
    private String areaName;

    private String competitor;

    private String projectType;

    //    竞争内容
    private String compareContent;

    //    竞争对手价格
    private String comparePrice;

    //    竞争对手截杀
    private String compareKill;

    //    竞争分析
    private String analyzeAdvantage;

    //    被设为精品的次数
    private Integer boutiqueNum;

    //    被追加的次数
    private Integer additionNum;

    //    被批注的次数
    private Integer postilNum;

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Integer getBoutiqueNum() {
        return boutiqueNum;
    }

    public void setBoutiqueNum(Integer boutiqueNum) {
        this.boutiqueNum = boutiqueNum;
    }

    public Integer getAdditionNum() {
        return additionNum;
    }

    public void setAdditionNum(Integer additionNum) {
        this.additionNum = additionNum;
    }

    public Integer getPostilNum() {
        return postilNum;
    }

    public void setPostilNum(Integer postilNum) {
        this.postilNum = postilNum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFirstProjectName() {
        return firstProjectName;
    }

    public void setFirstProjectName(String firstProjectName) {
        this.firstProjectName = firstProjectName;
    }

    public List<Organization> getApplydeparts() {
        return applydeparts;
    }

    public void setApplydeparts(List<Organization> applydeparts) {
        this.applydeparts = applydeparts;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
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

    public Integer getAnalyze() {
        return analyze;
    }

    public void setAnalyze(Integer analyze) {
        this.analyze = analyze;
    }
}
