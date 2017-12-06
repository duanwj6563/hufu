package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 回访
 */
@Entity
@Table(name = "hufu_sec_strategy_phase")
public class SecStrategyPhase extends StrategyPhase {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "sec_strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<SolveFirstProblem> solveFirstProblems = new HashSet<SolveFirstProblem>();

    public Set<SolveFirstProblem> getSolveFirstProblems() {
        return solveFirstProblems;
    }

    public void setSolveFirstProblems(Set<SolveFirstProblem> solveFirstProblems) {
        this.solveFirstProblems = solveFirstProblems;
    }
}
