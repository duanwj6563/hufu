package com.feo.domain.repository.strategy;

import com.feo.domain.model.strategy.AdditionContent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AdditionContentRepository extends JpaRepository<AdditionContent,Long> {

//    List<AdditionContent> findByStrategyIdAndOrderByDateDesc(Long strategyId);

    /**
     * 根据策略id查询全部追加内容
     * @param strategyId
     * @return
     */
    List<AdditionContent> findByStrategyId(Long strategyId);

    /**
     * 根据策略id和时间获取截止某时间点以后的追加列表
     * @param strategyId
     * @param date
     * @return
     */
    List<AdditionContent> findByStrategyIdAndDateAfter(Long strategyId, Date date);

//    @Query("select a from AdditionContent a where a.strategyId = ?1")

    /**
     * 根据策略id分页查询追加列表
     * @param strategyId
     * @param pageable
     * @return
     */
    Page<AdditionContent> findAllByStrategyId(Long strategyId, Pageable pageable);
}
