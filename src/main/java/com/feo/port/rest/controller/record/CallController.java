package com.feo.port.rest.controller.record;

import com.feo.application.record.RecordService;
import com.feo.port.rest.dto.record.SelectRecodCondition;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 录音控制层
 * Created by huang on 2017/11/15.
 */
@RestController
@RequestMapping("/calls")
public class CallController {

    //质检日志
    private final Logger logger = LoggerFactory.getLogger(CallController.class);

    //质检Service
    private RecordService recordService;

    @ApiOperation(value = "录音列表")
    @GetMapping()
    public Map<String, Object> selectRecordList(SelectRecodCondition selectRecodCondition) {
        logger.info("用户：正在请求录音列表");
        return recordService.selectRecordList(selectRecodCondition);
    }

    @Autowired
    public void setRecordService(RecordService recordService) {
        this.recordService = recordService;
    }
}
