package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 首咨阶段
 */
//@JsonIgnoreProperties(value={"needs","strategyPhases","strategyLogs","advises"})
@Entity
@Table(name = "hufu_first_strategy_phase")
public class FirstStrategyPhase extends StrategyPhase {


    //主推项目
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "first_strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<SecondProject> secondProjects = new HashSet<SecondProject>();

    public Set<SecondProject> getSecondProjects() {
        return secondProjects;
    }

    public void setSecondProjects(Set<SecondProject> secondProjects) {
        this.secondProjects = secondProjects;
    }

}
