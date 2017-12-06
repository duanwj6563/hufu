package com.sunlands.feo.domain.repository.strategy;

import com.sunlands.feo.domain.model.strategy.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, Long>, StrategyDao, JpaSpecificationExecutor<Strategy> {
    /**
     * 获取最新通过的策略
     *
     * @return
     */
    @Query(value = "select * from hufu_strategy where status=2 and uid=?1 order by update_time desc limit 0,1", nativeQuery = true)
    public Strategy getTopDateStrategy(Integer uid);

    Strategy findByStatusAndIdInAndStartTimeBeforeOrderByStartTimeDesc(Integer status,long[] ids,Date startTime);

    /**
     * 获取下周适用策略
     *
     * @param startTime
     * @param ids
     * @return
     */
    List<Strategy> findAllByStartTimeAfterAndIdIn(Date startTime, long[] ids);

    @Query(value = "SELECT strategy_id FROM hufu_strategy_applydepart WHERE apply_dept_id = ?1", nativeQuery = true)
    long[] queryStrategies(Integer orgId);

    /**
     * 查询某策略被设为精品的次数
     */
    @Query(value = "SELECT count(*) FROM hufu_boutique_strategy_user WHERE strategy_id=?1 ", nativeQuery = true)
    Integer selectBoutiqueStrategyNum(Long strategyId);

    /**
     * 删除更新时间为空的策略
     * @return
     */
    List<Strategy> findAllByUpdateTimeIsNull();
}
