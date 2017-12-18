package com.feo.port.rest.controller.record;

import com.feo.application.record.RecordService;
import com.feo.common.StrUtil;
import com.feo.domain.exception.ServerStatus;
import com.feo.domain.exception.UserDefinedException;
import com.feo.domain.model.record.Record;
import com.feo.port.rest.dto.record.AddRecordScoreInfo;
import com.feo.port.rest.dto.record.AddViolationInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 质检控制层
 * Created by huang on 2017/11/1.
 */
@RestController
@RequestMapping("/qc")
public class QCController {

    //质检日志
    private final Logger logger = LoggerFactory.getLogger(QCController.class);

    //质检Service
    private RecordService recordService;

    @ApiOperation(value = "录音质检任务添加")
    @PostMapping(value = "/tasks")
    public List<Long> addRecordShoppingCart(@RequestBody Long[] callIds) {
        logger.info("正在添加录音任务ids：" + Arrays.toString(callIds));
        return recordService.addRecordShoppingCart(callIds);
    }

    @ApiOperation(value = "录音质检任务列表")
    @GetMapping(value = "/tasks")
    public List<Map<String, Object>> selectRecordShoppingCartList() {
        logger.info("正在请求录音打包列表详情");
        return recordService.selectRecordShoppingCartList();
    }

    //录音自动清空
    @Scheduled(cron = "0 0 1 * * ?")
//    @DeleteMapping(value = "/tasks")
    @GetMapping(value = "/clear")
    public String deleteRecordShoppingCart() {
        logger.info("每日清除未提交的质检打分列表");
        return recordService.deleteRecordShoppingCart();
    }

    @ApiOperation(value = "录音质检详情一(录音基本信息)")
    @GetMapping(value = "/tasks/{callId}/base")
    public Map<String, Object> selectRecordDetailOne(@PathVariable("callId") Long callId) {
        logger.info("正在请求录音：" + callId + "录音详情一(录音基本信息)");
        return recordService.selectRecordDetailOne(callId);
    }

    @ApiOperation(value = "录音质检详情二（录音违规信息）")
    @GetMapping(value = "/tasks/{callId}/violation")
    public Map<String, Object> selectRecordDetailTwo(@PathVariable("callId") Long callId) {
        logger.info("正在请求录音：" + callId + "录音详情二（录音违规信息）");
        return recordService.selectRecordDetailTwo(callId);
    }

    @ApiOperation(value = "录音质检详情三（录音策略信息）")
    @GetMapping(value = "/tasks/{callId}/strategy")
    public Map<String, Object> selectRecordDetailThree(@PathVariable("callId") Long callId, @RequestParam(defaultValue = "0") Integer recType) {
        logger.info("正在请求录音：" + callId + "录音详情三（录音策略信息）");
        if (StrUtil.isNotNull(recType)) {
            return recordService.selectRecordDetailThree(callId, recType);
        }
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @ApiOperation(value = "录音质检打分保存")
    @PostMapping(value = "/tasks/{callId}/save")
    public boolean addRecordScoreToSave(@PathVariable("callId") Long callId, @RequestBody @Valid AddRecordScoreInfo addRecordScoreInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("错误信息{}", bindingResult.getFieldError().getDefaultMessage());
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY, bindingResult.getFieldError().getDefaultMessage());
        }
        logger.info("录音质检id:" + callId + "打分保存");
        Record record = addRecordScoreInfo.addRecordScoreInfo();
        record.setId(callId);
        return recordService.addRecordScoreToSave(record);
    }

    @ApiOperation(value = "录音质检打分提交")
    @PostMapping(value = "/tasks/submit")
    public boolean addRecordScoreToSubmit(@RequestBody Long[] callIds) {
        logger.info("录音质检ids:" + Arrays.toString(callIds) + "批量提交");
        return recordService.addRecordScoreToSubmit(callIds);
    }

    @ApiOperation(value = "录音质检违规提交")
    @PostMapping(value = "/tasks/{callId}/violation")
    public boolean addRecordViolation(@PathVariable("callId") Long callId, @RequestBody @Valid AddViolationInfo addViolationInfo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("错误信息{}", bindingResult.getFieldError().getDefaultMessage());
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY, bindingResult.getFieldError().getDefaultMessage());
        }
        logger.info("录音:" + callId + "提交违规信息");
        return recordService.addRecordViolation(callId, addViolationInfo);
    }

    @ApiOperation(value = "录音质检违规详情")
    @GetMapping(value = "/tasks/{callId}/violations/{violationInfoId}")
    public Map<String, Object> selectRecordViolation(@PathVariable("callId") Long callId, @PathVariable("violationInfoId") Long violationInfoId) {
        logger.info("录音:" + callId + "违规:" + violationInfoId + "查询违规信息");
        return recordService.selectRecordViolation(callId, violationInfoId);
    }

    @ApiOperation(value = "录音质检违规移除")
    @DeleteMapping(value = "/tasks/{callId}/violations/{violationInfoId}")
    public boolean deleteRecordViolation(@PathVariable("callId") Long callId, @PathVariable("violationInfoId") Long violationInfoId) {
        logger.info("录音:" + callId + "移除违规信息");
        return recordService.deleteRecordViolation(callId, violationInfoId);
    }

    @ApiOperation(value = "(工作台)录音质检日听")
    @GetMapping(value = "/statistics/dayListen")
    public Map<String, Object> selectRecordDayListen() {
        logger.info("(工作台)录音日听");
        return recordService.selectRecordDayListen();
    }

    @ApiOperation(value = "(工作台)录音质检周听")
    @GetMapping(value = "/statistics/weekListen")
    public Map<String, Object> selectRecordWeekDetail() {
        logger.info("(工作台)录音日听");
        return recordService.selectRecordWeekDetail();
    }

    @ApiOperation(value = "(工作台)录音质检听数据详细")
    @GetMapping(value = "/statistics/details")
    public List<Map<String, Object>> selectRecordListenDetail() {
        logger.info("(工作台)录音日听");
        return recordService.selectRecordListenDetail();
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }

}
