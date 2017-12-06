package com.sunlands.feo.domain.repository.strategy;

import com.sunlands.feo.domain.model.strategy.StrategyLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yangchao on 17/11/8.
 */
public interface StrategyLogRepository extends JpaRepository<StrategyLog, Long> {

    /**
     * 根据策略id、用户id和操作类型获取策略日志
     *
     * @param strategyId
     * @param uid
     * @param operationType
     * @return
     */
    List<StrategyLog> findByStrategyIdAndUser_IdAndOperationTypeOrderByDateDesc(Long strategyId, Integer uid, Integer operationType);

    /**
     * 根据策略id和分页条件获取日志列表页
     *
     * @param strategyId
     * @param pageable
     * @return
     */
    Page<StrategyLog> findAllByStrategyId(Long strategyId, Pageable pageable);

    /**
     * 倒序查所有
     *
     * @param sort
     * @return
     */
    List<StrategyLog> findAllByStrategyId(Long id, Sort sort);

    List<StrategyLog> findAllByUserIsNull();
}
