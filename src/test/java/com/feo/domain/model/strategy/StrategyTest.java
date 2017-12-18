package com.feo.domain.model.strategy;

import com.feo.domain.repository.strategy.StrategyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

/**
 * Created by yangchao on 17/11/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StrategyTest {

    @Autowired
    private StrategyRepository strategyRepository;

    @Transactional
    @Test
    public void testDoEvent() throws Exception {
        System.out.println(">> start");
        Strategy s = new Strategy();
        s.setName("测试策略");
        s.setStatus(0);
        s.setGroupName("我是第一军团");
        Strategy strategy = strategyRepository.saveAndFlush(s);
//        strategy.setStatus(1);
//        strategyRepository.saveAndFlush(strategy);
        /*s.setStatus(4);
        strategyRepository.saveAndFlush(s);

        System.out.println(">>>>>>>>>>>>>>>>");

        Strategy one = strategyRepository.findOne((long) 155);
        one.setStatus(1);
        strategyRepository.saveAndFlush(one);
        one.setStatus(2);
        strategyRepository.saveAndFlush(one);
        System.out.println(">> sleep");
        Thread.sleep(10000L);
        System.out.println(">> Done");*/
    }
}