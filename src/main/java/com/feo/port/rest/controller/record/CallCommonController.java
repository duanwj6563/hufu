package com.feo.port.rest.controller.record;

import com.feo.application.record.RecordCommonService;
import com.feo.common.EnumsUtil;
import com.feo.domain.model.enums.chance.ChanceStateEnums;
import com.feo.domain.model.enums.record.*;
import com.feo.domain.model.record.QuestionType;
import com.feo.domain.model.record.ViolationType;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 录音通用控制层
 * Created by huang on 2017/11/9.
 */
@RestController
@RequestMapping("/calls/commons")
public class CallCommonController {

    //质检日志
    private final Logger logger = LoggerFactory.getLogger(QCController.class);

    //质检Service
    private RecordCommonService recordCommonService;

    @ApiOperation(value = "录音问题二级联动")
    @GetMapping(value = "/questions/ganged")
    public List<QuestionType> selectRecordQuestionGanged(@RequestParam(value = "type") Integer type, @RequestParam(value = "id", defaultValue = "0") Long id) {
        logger.info("录音问题类型:" + type + "二级联动id:" + id);
        return recordCommonService.selectRecordQuestionGanged(type, id);
    }

    @ApiOperation(value = "录音违规三级联动")
    @GetMapping(value = "/violations/ganged")
    public List<ViolationType> selectRecordViolationGanged(@RequestParam(value = "id", defaultValue = "0") Long id) {
        logger.info("录音违规三级联动id:" + id);
        return recordCommonService.selectRecordViolationGanged(id);
    }

    @ApiOperation(value = "所有四级组织结构")
    @GetMapping(value = "/orgs")
    public List<Map<String, Object>> selectOrgs(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        logger.info("录音类型查询");
        return recordCommonService.selectAllOrgs(id);
    }

    @ApiOperation(value = "sop负责四级组织结构")
    @GetMapping(value = "/orgs/sop")
    public Set<Map<String, Object>> selectSopOrgs(@RequestParam(value = "id", defaultValue = "0") Integer id) {
        logger.info("录音类型查询");
        return recordCommonService.selectOrgs(id);
    }

    @ApiOperation(value = "录音类型枚举查询")
    @GetMapping(value = "/types")
    public List<Map<String, Object>> selectRecordTypeEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(RecordTypeEnums.getIdAndNameList());
    }

    @ApiOperation(value = "录音性质枚举查询")
    @GetMapping(value = "/recTypes")
    public List<Map<String, Object>> selectRecTypeEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(RecTypeEnums.getIdAndNameList());
    }

    @ApiOperation(value = "录音优秀类型枚举查询")
    @GetMapping(value = "/goodTypes")
    public List<Map<String, Object>> selectGoodTypeEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(GoodTypeEnums.getIdAndNameList());
    }

    @ApiOperation(value = "录音流程完整度枚举查询")
    @GetMapping(value = "/finishNums")
    public List<Map<String, Object>> selectFinishNumEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(FinishNumEnums.getIdAndNameList());
    }

    @ApiOperation(value = "录音学生现状枚举查询")
    @GetMapping(value = "/states")
    public List<Map<String, Object>> selectStateEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(ChanceStateEnums.getIdAndNameList());
    }

    @ApiOperation(value = "录音取证目的枚举查询")
    @GetMapping(value = "/aims")
    public List<Map<String, Object>> selectAimEnums() {
        logger.info("录音类型查询");
        return EnumsUtil.getIdAndNameList(AimEnums.getIdAndNameList());
    }

    @Autowired
    public void setRecordCommonService(RecordCommonService recordCommonService) {
        this.recordCommonService = recordCommonService;
    }
}
