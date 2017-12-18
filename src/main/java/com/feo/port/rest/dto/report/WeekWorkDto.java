package com.feo.port.rest.dto.report;

import lombok.Data;

import java.io.Serializable;

/**
 * 周工作量dto
 */
@Data
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

    public WeekWorkDto() {
    }
}
