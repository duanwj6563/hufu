package com.feo.port.rest.dto.report;

import lombok.Data;

@Data
public class AnalyzeConditions extends PageParameters {
    private String screen;//筛选条件
    private String sort;//排序条件
    private String type;//类型
    private String range;//范围1我的 2所有
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String legion;//军团

    public AnalyzeConditions() {
    }
}
