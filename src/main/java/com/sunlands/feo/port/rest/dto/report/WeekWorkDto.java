package com.sunlands.feo.port.rest.dto.report;

import java.io.Serializable;

/**
 * 周工作量dto
 */
public class WeekWorkDto implements Serializable {
    private Integer rank;//排名
    private String name;//sop名字
    private String divisionName;//负责事业部
    private Integer number;//负责军团数
    private String legionName;//负责军团
    private Integer hearNumber;//周听数
    private Integer time;//时长
    private Integer gorpNumber;//军团报告数
    private Integer recordNumber;//优秀录音下载数
    private Integer strategyNumber;//系统提交策略数

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLegionName() {
        return legionName;
    }

    public void setLegionName(String legionName) {
        this.legionName = legionName;
    }

    public Integer getHearNumber() {
        return hearNumber;
    }

    public void setHearNumber(Integer hearNumber) {
        this.hearNumber = hearNumber;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getGorpNumber() {
        return gorpNumber;
    }

    public void setGorpNumber(Integer gorpNumber) {
        this.gorpNumber = gorpNumber;
    }

    public Integer getRecordNumber() {
        return recordNumber;
    }

    public void setRecordNumber(Integer recordNumber) {
        this.recordNumber = recordNumber;
    }

    public Integer getStrategyNumber() {
        return strategyNumber;
    }

    public void setStrategyNumber(Integer strategyNumber) {
        this.strategyNumber = strategyNumber;
    }

    public WeekWorkDto() {
    }
}
