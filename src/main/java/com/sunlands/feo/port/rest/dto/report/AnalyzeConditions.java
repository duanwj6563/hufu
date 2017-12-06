package com.sunlands.feo.port.rest.dto.report;

public class AnalyzeConditions extends PageParameters {
    private String screen;//筛选条件
    private String sort;//排序条件
    private String type;//类型
    private String range;//范围1我的 2所有
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String legion;//军团

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLegion() {
        return legion;
    }

    public void setLegion(String legion) {
        this.legion = legion;
    }

    public AnalyzeConditions() {
    }
}
