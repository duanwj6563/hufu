package com.sunlands.feo.application;

import com.alibaba.fastjson.JSON;
import com.sunlands.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.sunlands.feo.domain.model.strategy.StrategyLog;
import com.sunlands.feo.application.strategy.StrategyLogService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yangchao on 17/11/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StrategyLogServiceTest {

    @Autowired
    private StrategyLogService strategyLogService;

    @Test
    public void testNewStrategyLog() throws Exception {
        Integer userId = 1;
        String content = "content";
        StrategyLog strategyLog = strategyLogService.newStrategyLog(userId, content, StrategyLogOperationType.READ);
        System.out.println();
        System.out.println("* "+ JSON.toJSONString(strategyLog));
        System.out.println();
    }

    @Test
    public void testQueryByStrategyIdAndUidAndType() throws Exception {
        Long strategyId = 1L;
        Integer userId = 1;
        StrategyLog strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(strategyId, userId, StrategyLogOperationType.READ);
        System.out.println();
        System.out.println("* "+ JSON.toJSONString(strategyLog));
        System.out.println();
    }
}