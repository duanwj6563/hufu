package com.feo.domain.repository.strategy;

import com.feo.domain.model.strategy.Advise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdviseRepository extends JpaRepository<Advise,Long> {

    /**
     * 根据策略id和类型查询对应的建议或批注列表
     * @param strategyId
     * @param type
     * @return
     */
    List<Advise> findByStrategyIdAndTypeOrderByDateDesc(Long strategyId,int type);

    /**
     * 根据策略id和类型查询对应的建议或批注列表页面
     * @param type
     * @param pageable
     * @return
     */
    public Page<Advise> findAllByStrategyIdAndType(Long strategyId,Integer type, Pageable pageable);

}
