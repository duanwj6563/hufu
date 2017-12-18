package com.feo.port.rest.dto.strategy;

import com.feo.domain.model.strategy.FinalStrategyPhase;

import javax.validation.constraints.NotNull;

public class FinalStrategyModel {

    private FinalStrategyPhase finalStrategyPhase;

    @NotNull(message = "主要竞争对手不能为空")
    private String competitor;

    //    竞争内容
    private String compareContent;

    //    竞争对手价格
    private String comparePrice;

    //    竞争对手截杀
    private String compareKill;

    //    竞争分析
    private String analyzeAdvantage;

    public FinalStrategyPhase getFinalStrategyPhase() {
        return finalStrategyPhase;
    }

    public void setFinalStrategyPhase(FinalStrategyPhase finalStrategyPhase) {
        this.finalStrategyPhase = finalStrategyPhase;
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
}
