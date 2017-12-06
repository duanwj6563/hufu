package com.sunlands.feo.port.rest.controller.message;

import com.sunlands.feo.application.message.MessageSerevice;
import com.sunlands.feo.port.rest.dto.message.MessageCondition;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 消息推送控制层
 * Created by huang on 2017/11/8.
 */
@RequestMapping("/messages")
@RestController
public class MessageController {

    //消息日志
    private final Logger logger = LoggerFactory.getLogger(MessageController.class);

    //消息Serevice
    private MessageSerevice messageSerevice;

    @ApiOperation(value = "未读消息首页铃铛")
    @GetMapping(value = "/messages/bell")
    public Map<String, Object> selectMsgBell() {
        logger.info("用户正在查询首页铃铛未读消息");
        return messageSerevice.selectMsgBell();
    }

    @ApiOperation(value = "未读消息首页推送")
    @GetMapping(value = "/messages/push")
    public List<Map<String, Object>> pushPullBell() {
        logger.info("用户正在查询首页铃铛未读消息");
        return messageSerevice.pushPullBell();
    }

    @ApiOperation(value = "消息批量标记已读")
    @PutMapping(value = "/messages/status")
    public boolean updateMsgStatus(@RequestParam Long[] ids) {
        logger.info("用户正在批量标记已读:" + Arrays.toString(ids) + "消息");
        return messageSerevice.updateMsgStatus(ids);
    }

    @ApiOperation(value = "消息中心列表")
    @GetMapping(value = "/messages")
    public Map<String, Object> selectMsgList(MessageCondition messageCondition) {
        logger.info("用户正在请求消息中心列表");
        return messageSerevice.selectMsgList(messageCondition);
    }

//    @Autowired
//    private StrategyRepository strategyRepository;
//
//    @ApiOperation(value = "测试----策略发布")
//    @GetMapping(value = "/mmsdsds")
//    public void testDoEvensdsdt() throws Exception {
//        System.out.println(">> start");
//        Strategy one = strategyRepository.findOne(5L);
//        one.setStatus(1);
//        strategyRepository.saveAndFlush(one);
//        one.setStatus(0);
//        strategyRepository.saveAndFlush(one);
//    }
//
//    @ApiOperation(value = "测试----策略驳回")
//    @GetMapping(value = "/mmm")
//    public void testDoEvent() throws Exception {
//        System.out.println(">> start");
//        Strategy one = strategyRepository.findOne(6L);
//        one.setStatus(3);
//        strategyRepository.saveAndFlush(one);
//        one.setStatus(1);
//        strategyRepository.saveAndFlush(one);
//    }
//
//    @ApiOperation(value = "测试----策略审批通过")
//    @GetMapping(value = "/ssssss")
//    public void testDoEvssssent() throws Exception {
//        System.out.println(">> start");
//        Strategy one = strategyRepository.findOne(34L);
//        one.setStatus(2);
//        strategyRepository.saveAndFlush(one);
//        one.setStatus(1);
//        strategyRepository.saveAndFlush(one);
//    }
//
//    @ApiOperation(value = "测试----策略追加")
//    @GetMapping(value = "/sssssssss")
//    public void testDoEsssvssssent() throws Exception {
//        System.out.println(">> start");
//        Strategy one = strategyRepository.findOne(7L);
//        one.setStatus(1);
//        strategyRepository.saveAndFlush(one);
//        one.setStatus(2);
//        strategyRepository.saveAndFlush(one);
//    }
//
//    @Autowired
//    private AdviseRepository adviseRepository;
//
//    @ApiOperation(value = "测试----策略备注")
//    @GetMapping(value = "/advicexxxxxxxxxxx")
//    public void testDoEven1t() throws Exception {
//        System.out.println(">> start");
//        Advise advise = new Advise();
//        advise.setType(0);
//        advise.setStrategyId(46l);
//        advise.setUid(4);
//        adviseRepository.save(advise);
//    }
//
//    @ApiOperation(value = "测试----策略批注")
//    @GetMapping(value = "/advicexxxx222xxxxxxx")
//    public void testDoEve222n1t() throws Exception {
//        System.out.println(">> start");
//        Advise advise = new Advise();
//        advise.setType(1);
//        advise.setStrategyId(46l);
//        advise.setUid(2);
//        adviseRepository.save(advise);
//    }
//
//    @Autowired
//    private AnalysisRepository analysisRepository;
//
//    @ApiOperation(value = "测试----分析报告")
//    @GetMapping(value = "/advicexxxx222xssxxxxxx")
//    public void testDoEve22是是是2n1t() throws Exception {
//        System.out.println(">> start");
//        Analyze advise = new Analyze();
//        advise.setCreater("004");
//        advise.setCreateDate(new Date());
//        advise.setStrategyId(53L);
//        advise.setReportName("xxxxxxxxxxxx");
//        analysisRepository.save(advise);
//    }

    @Autowired
    public void setMessageSerevice(MessageSerevice messageSerevice) {
        this.messageSerevice = messageSerevice;
    }
}
