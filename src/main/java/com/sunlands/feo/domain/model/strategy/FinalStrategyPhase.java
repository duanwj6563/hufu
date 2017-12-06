package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 库存跨期
 */
@Entity
@Table(name = "hufu_final_strategy_phase")
public class FinalStrategyPhase extends StrategyPhase {

    //    库存跨期
    //    库存跨期触发式开场
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "final_strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<TriggerOpen> triggerOpens = new HashSet<TriggerOpen>();

    //    库存跨期辅助咨询工具
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "final_strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<AssistTool> assistTools = new HashSet<>();

    public Set<TriggerOpen> getTriggerOpens() {
        return triggerOpens;
    }

    public void setTriggerOpens(Set<TriggerOpen> triggerOpens) {
        this.triggerOpens = triggerOpens;
    }

    public Set<AssistTool> getAssistTools() {
        return assistTools;
    }

    public void setAssistTools(Set<AssistTool> assistTools) {
        this.assistTools = assistTools;
    }
}
