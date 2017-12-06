package com.sunlands.feo.domain.repository.strategy;

import com.sunlands.feo.domain.model.strategy.StrategyPhase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyPhaseRepository extends JpaRepository<StrategyPhase, Long> {

    /**
     * 根据策略id和策略类型获取指定对应策略阶段
     * @param phase
     * @param strategyId
     * @return
     */
    StrategyPhase findByPhaseAndStrategyId(Integer phase, Long strategyId);

    List<StrategyPhase> findAllByStrategyIdIsNull();

    StrategyPhase findById(Long id);
}
