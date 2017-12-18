package com.feo.application.strategy;

import com.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.feo.domain.model.strategy.StrategyLog;

public interface StrategyLogService {
    /**
     * 新建日志对象 - 注意:只是返回对象,需要手动持久化
     * @param userId
     * @param content
     * @return
     */
    StrategyLog newStrategyLog(Integer userId, String content, StrategyLogOperationType ot);

    /**
     * 查询日志信息 - 取最后一条
     * @param StrategyId 策略id
     * @param userId 操作用户id
     * @param ot 操作类型
     * @return
     */
    StrategyLog queryByStrategyIdAndUidAndType(Long StrategyId,Integer userId,StrategyLogOperationType ot);

}
