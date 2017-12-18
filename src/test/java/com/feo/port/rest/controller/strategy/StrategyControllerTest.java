package com.feo.port.rest.controller.strategy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * 策略管理模块 单元测试
 * Created by yangchao on 17/11/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StrategyControllerTest {
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 修改策略状态
     * @throws Exception
     */
    @Test
    public void testUpdateStrategyStatus() throws Exception {
        /*mockMvc.perform(put("/strategy/{strategyId}/stauts",1L)
                    .param("auditStatus","PASS")
                    //.accept(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.code","$.msg").exists())
                //.andExpect(jsonPath("$.code").value(200))
                //.andExpect(jsonPath("$.msg").value("成功"))
                .andReturn();*/
    }
}