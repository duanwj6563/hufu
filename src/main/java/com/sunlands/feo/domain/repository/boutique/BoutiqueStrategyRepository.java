package com.sunlands.feo.domain.repository.boutique;

import com.sunlands.feo.domain.model.boutique.BoutiqueStrategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BoutiqueStrategyRepository extends JpaRepository<BoutiqueStrategy,Long> {

    /**
     * 优秀策略分页查询相关方法
     */
    Page<BoutiqueStrategy> findAllByStrategyIdInAndStatusAndUpdateDateAfterAndUpdateDateBefore(long[] ids,Integer status, Date startTime,Date endTime,Pageable pageable);
    Page<BoutiqueStrategy> findAllByStatusAndUpdateDateAfterAndUpdateDateBefore(Integer status,Date startTime,Date endTime,Pageable pageable);
    @Query(value = "SELECT strategy_id FROM hufu_boutique_strategy_user WHERE uid = ?1",nativeQuery = true)
    long[] getIds(Integer uid);
    @Query(value = "SELECT strategy_id FROM hufu_boutique_strategy_user WHERE uid = ?1 and strategy_id=?2 ",nativeQuery = true)
    long[] decideBoutiqueStrategy(Integer uid,Long strategyId);

    @Query(value = "SELECT a.strategy_id FROM hufu_strategy_applydepart a INNER JOIN hufu_boutique_strategy b on a.strategy_id =b.strategy_id WHERE a.apply_dept_id in ( ?1 )",nativeQuery = true)
    long[] getStrategyIds(Integer[] orgIds);

    @Query(value = "SELECT org.strategy_id FROM hufu_strategy_applydepart org INNER JOIN hufu_boutique_strategy_user bou  ON  org.strategy_id=bou.strategy_id  WHERE org.apply_dept_id in (?1) and bou.uid = ?2 ",nativeQuery = true)
    Long[] getStrategyIds1(Integer[] orgIds,Integer uid);

    BoutiqueStrategy findByStrategyId(Long id);

}
