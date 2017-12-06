package com.sunlands.feo.port.rest.dto.strategy;

public class AddStrategyPhaseModel {

    private Long strategyPhaseId;

    private Integer type;

    private Integer number;

    private String content;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getStrategyPhaseId() {
        return strategyPhaseId;
    }

    public void setStrategyPhaseId(Long strategyPhaseId) {
        this.strategyPhaseId = strategyPhaseId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
