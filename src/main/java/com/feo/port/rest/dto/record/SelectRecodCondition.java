package com.feo.port.rest.dto.record;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 质检查询筛选项
 * Created by huang on 2017/11/7.
 */
public class SelectRecodCondition {

    @ApiModelProperty("页码")
    private Integer page = 1;

    @ApiModelProperty("每页多少")
    private Integer size = 20;

    @ApiModelProperty("质检录音状态")
    private Integer status = 0;

    @ApiModelProperty("录音打分状态")
    private Integer selectStatus;

    @ApiModelProperty("是否违规")
    private Integer isViolation;

    @ApiModelProperty("事业部id")
    private Integer enterpriseId;

    @ApiModelProperty("责任方/军团id")
    private Integer groupId;

    @ApiModelProperty("销售部id")
    private Integer sellId;

    @ApiModelProperty("销售组id")
    private Integer sellGroupId;

    @ApiModelProperty("员工工号")
    private Integer workNum;

    @ApiModelProperty("策略名称")
    private String name;

    @ApiModelProperty("开始录音时长")
    private Integer startTimeLength;

    @ApiModelProperty("截止录音时长")
    private Integer endTimeLength;

    @ApiModelProperty("执行总得分")
    private Integer sumScore;

    @ApiModelProperty("学员情况 0、企业普通员工 1、政府事业单位 3、...")
    private Integer state;

    @ApiModelProperty("操作时间")
    private Date date;

    @ApiModelProperty("录音类型 0、常规 1、违规 2、后端发起 3、语音系统")
    private Integer recordType;

    @ApiModelProperty("录音性质：0、首咨 1、回访 2、跨期")
    private Integer recType;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getSellId() {
        return sellId;
    }

    public void setSellId(Integer sellId) {
        this.sellId = sellId;
    }

    public Integer getSellGroupId() {
        return sellGroupId;
    }

    public void setSellGroupId(Integer sellGroupId) {
        this.sellGroupId = sellGroupId;
    }

    public Integer getWorkNum() {
        return workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStartTimeLength() {
        return startTimeLength;
    }

    public void setStartTimeLength(Integer startTimeLength) {
        this.startTimeLength = startTimeLength;
    }

    public Integer getEndTimeLength() {
        return endTimeLength;
    }

    public void setEndTimeLength(Integer endTimeLength) {
        this.endTimeLength = endTimeLength;
    }

    public Integer getSumScore() {
        return sumScore;
    }

    public void setSumScore(Integer sumScore) {
        this.sumScore = sumScore;
    }

    public Date getDate() {
        return date;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public Integer getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(Integer selectStatus) {
        this.selectStatus = selectStatus;
    }

    public Integer getIsViolation() {
        return isViolation;
    }

    public void setIsViolation(Integer isViolation) {
        this.isViolation = isViolation;
    }
}
