package com.sunlands.feo.domain.repository.strategy;

import com.sunlands.feo.port.rest.dto.CommonPage;
import com.sunlands.feo.port.rest.dto.strategy.SelectStrategyCondition;

import java.math.BigInteger;

public interface StrategyDao {
    /**
     * 根据查询策略条件查询策略列表
     * @param selectStrategyCondition
     * @return
     */
    CommonPage queryStrategys(SelectStrategyCondition selectStrategyCondition);

    /**
     * 获取符合条件的策略总条数
     * @param selectStrategyCondition
     * @return
     */
    BigInteger getTotalElements(SelectStrategyCondition selectStrategyCondition);

}
