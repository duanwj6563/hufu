package com.feo.port.rest.dto.boutique;


public class BoutiqueSelectCondition {

//    优秀类型 0，历程完整 1，推班类型明确 2，推专业灵活
    private Integer goodType;
//    录音性质：1、首咨 2、回访 3、跨期
    private Integer itemType;
//    1、我的 2、全部
    private Integer own;
//    页面容量
    private Integer pageSize;
//    页码
    private Integer pageNum;
//    开始时间
    private String startTime;
//    结束时间
    private String endTime;

    private Integer levelOne;

    private Integer levelTwo;

    private Integer levelThree;

    private Integer levelFour;

    public Integer getLevelOne() {
        return levelOne;
    }

    public void setLevelOne(Integer levelOne) {
        this.levelOne = levelOne;
    }

    public Integer getLevelTwo() {
        return levelTwo;
    }

    public void setLevelTwo(Integer levelTwo) {
        this.levelTwo = levelTwo;
    }

    public Integer getLevelThree() {
        return levelThree;
    }

    public void setLevelThree(Integer levelThree) {
        this.levelThree = levelThree;
    }

    public Integer getLevelFour() {
        return levelFour;
    }

    public void setLevelFour(Integer levelFour) {
        this.levelFour = levelFour;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public Integer getOwn() {
        return own;
    }

    public void setOwn(Integer own) {
        this.own = own;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
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

}
