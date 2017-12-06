package com.sunlands.feo.port.rest.controller.strategy;

import com.sunlands.feo.application.strategy.StrategyCommonService;
import com.sunlands.feo.application.strategy.StrategyService;
import com.sunlands.feo.common.AssertUtil;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.exception.UserDefinedException;
import com.sunlands.feo.domain.model.enums.AuditStatus;
import com.sunlands.feo.domain.model.strategy.*;
import com.sunlands.feo.port.rest.controller.UserController;
import com.sunlands.feo.port.rest.dto.CommonPage;
import com.sunlands.feo.port.rest.dto.FileInfo;
import com.sunlands.feo.port.rest.dto.strategy.*;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RequestMapping("/strategies")
@RestController
public class StrategyController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private StrategyService strategyService;
    @Autowired
    private StrategyCommonService strategyCommonService;

    @ApiOperation(value = "创建或修改策略")
    @PostMapping("/{strategyId}")
//    @PreAuthorize("hasRole('MANAGER')")//此方法只允许军团长访问角色访问
    public Map<String, Object> createStrategy(@PathVariable(name = "strategyId") Long strategyId) {
        AssertUtil.isNotNull(strategyId);
        Map<String, Object> map = strategyService.createStrategy(strategyId);
        return map;
    }

    @ApiOperation(value = "添加或修改策略基本信息")
    @PostMapping("/{strategyId}/base")
//    @PreAuthorize("hasRole('MANAGER')")//此方法只允许军团长访问角色访问
    public Map<String, Object> addStrategyBase(@PathVariable(name = "strategyId") Long strategyId,@RequestBody StrategyBase strategyBase) {
        logger.info("添加策略的基本信息:"+strategyBase.getName());
        logger.info("添加策略id为" + strategyBase.getId() + "的基本信息");
        Map<String, Object> map = strategyService.addStrategyBase(strategyId, strategyBase);
        return map;
    }

    @ApiOperation(value = "添加或修改首咨")
    @PostMapping("/{strategyId}/phaseOne")
    public boolean addFirstStrategyPhase(@RequestBody FirstStrategyPhase firstStrategyPhase, @PathVariable(name = "strategyId") Long oldStrategyId,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("错误信息{}", bindingResult.getFieldError().getDefaultMessage());
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY,bindingResult.getFieldError().getDefaultMessage());
        }
        logger.info("添加策略id为" + firstStrategyPhase.getStrategyId() + "的首咨信息");
        return strategyService.addFirstStrategyPhase(firstStrategyPhase, oldStrategyId);
    }

    @ApiOperation(value = "添加或修改回访")
    @PostMapping("/{strategyId}/phaseTwo")
//    @PreAuthorize("hasRole('MANAGER')")//此方法只允许军团长访问角色访问
    public boolean addSecStrategyPhase(@RequestBody SecStrategyPhase secStrategyPhase, @PathVariable(name = "strategyId") Long strategyId,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("错误信息{}", bindingResult.getFieldError().getDefaultMessage());
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY,bindingResult.getFieldError().getDefaultMessage());
        }
        AssertUtil.isNotNull(strategyId);
        AssertUtil.isNotNull(secStrategyPhase.getStrategyId());
        logger.info("添加策略id为" + secStrategyPhase.getStrategyId() + "的回访信息");
        return strategyService.addSecStrategyPhase(secStrategyPhase, strategyId);
    }

    @ApiOperation(value = "添加或修改库存")
    @PostMapping("/{strategyId}/phaseThree")
    public boolean addFinalStrategyPhase(@PathVariable(name = "strategyId") Long srategyId,@RequestBody @Valid FinalStrategyModel finalStrategyModel, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("错误信息{}", bindingResult.getFieldError().getDefaultMessage());
            throw new UserDefinedException(ServerStatus.PARAM_EMPTY,bindingResult.getFieldError().getDefaultMessage());
        }
       return strategyService.addFinalStrategyPhase(srategyId,
                                                                finalStrategyModel.getFinalStrategyPhase(),
                                                                finalStrategyModel.getCompetitor(),
                                                                finalStrategyModel.getCompareContent(),
                                                                finalStrategyModel.getComparePrice(),
                                                                finalStrategyModel.getCompareKill(),
                                                                finalStrategyModel.getAnalyzeAdvantage());
    }
    @ApiOperation(value = "获取策略阶段（上一步）")
    @GetMapping("/{strategyId}/phase")
    public Map<String, Object> getStrategyPhase( @PathVariable(name = "strategyId") Long strategyId,@RequestParam(required = true,name = "newStrategyId") Long newStrategyId,@RequestParam Integer type) {
        Map<String, Object> map = strategyService.getStrategyPhase(strategyId, newStrategyId,type);
        return map;
    }

    //    策略建议
    @ApiOperation(value = "策略建议")
    @PostMapping("/{strategyId}/advice")
    public boolean addAdvice( @PathVariable(name = "strategyId") Long strategyId,@RequestBody Advise advise) {
        AssertUtil.isNotNull(advise.getStrategyId());
        AssertUtil.isNotNull(advise.getContent());
        return strategyService.addAdvice(advise);
    }

    //    追加策略建议及内容  type: 0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具
    @ApiOperation(value = "策略追加")
    @PostMapping("/{strategyId}/appendices")
    public boolean addStrategy( @PathVariable("strategyId") Long strategyId,@RequestBody List<AddStrategyPhaseModel> addStrategyPhaseModels) {
        logger.info("追加策略");
        return strategyService.addStrategy(strategyId,addStrategyPhaseModels);
    }

    @ApiOperation(value = "获取策略操作记录")
    @GetMapping("/{strategyId}/logs")
    public Map<String,Object> getStrategyLogDetails(@PathVariable("strategyId") Long strategyId,@RequestParam("number") Integer number,@RequestParam("type") Integer type){
        return strategyService.getStrategyLogDetails(strategyId,number, type);
    }


    @ApiOperation(value = "获取策略追加详情")
    @GetMapping("/{strategyId}/appendlogs")
    public Map<String,Object> getStrategyAddDetails(@PathVariable("strategyId") Long strategyId,@RequestParam("number") Integer number){
        return strategyService.getStrategyAddDetails(strategyId, number);
    }

    @ApiOperation(value = "获取策略内容")
    @GetMapping("/{strategyId}")
    public Map<String,Object> getStrategy(@PathVariable("strategyId") Long strategyId){
        Map<String,Object> map= strategyService.getStrategy(strategyId);
        return map;
    }

    @ApiOperation(value = "查询策略列表")
    @GetMapping()
    public CommonPage queryStrategy(SelectStrategyCondition selectStrategyCondition){
        AssertUtil.isNotNull(selectStrategyCondition.getPageSize());
        AssertUtil.isNotNull(selectStrategyCondition.getPageNum());
        CommonPage page = strategyService.queryStrategy(selectStrategyCondition);
        return page;
    }

    @ApiOperation(value = "修改策略审核状态")
    @PutMapping("/{strategyId}/status")
    public boolean updateStrategyStatus(@PathVariable("strategyId") Long strategyId,@RequestParam(required = true,name = "auditStatus",value = "auditStatus") AuditStatus auditStatus,@RequestParam(required = false) String logContent){
        AssertUtil.isNotNull(strategyId);
        AssertUtil.isNotNull(auditStatus);
        return strategyService.updateStrategyStatus(strategyId, auditStatus,logContent);
    }

    @GetMapping("/areas")
    public List<Map<String, Object>> getAreas(){
        List<Map<String, Object>> areas = strategyCommonService.getAreas();
        return areas;
    }

    @GetMapping("/projects")
    public List<Map<String, Object>> getProjects(){
        List<Map<String, Object>> projects = strategyCommonService.getProjects();
        return projects;
    }

    @GetMapping("/scoretypes")
    public List<Map<String, Object>> getScoreTypes(){
        List<Map<String, Object>> scoreTypes = strategyCommonService.getScoreTypes();
        return scoreTypes;
    }

    @GetMapping("/{strategyId}/download")
    public ServerStatus downloadStrategy(@PathVariable("strategyId") Long strategyId, HttpServletResponse response,HttpServletRequest request) throws Exception{
        return strategyService.downloadStrategy(strategyId,response,request);
    }

    @GetMapping("/batchDownload")
    public ServerStatus batchDownloadStrategys(@RequestParam long[] strategyIds, HttpServletResponse response, HttpServletRequest request) throws Exception{
        return strategyService.batchDownloadStrategy(strategyIds,response,request);
    }

    @PostMapping("/{strategyPhaseId}/upload")
    public boolean upload(@RequestBody MultipartFile file,@PathVariable("strategyPhaseId") Long strategyPhaseId) throws Exception{
       return strategyService.upload(file,strategyPhaseId);
    }

    /**
     * 策略话术下载
     * @param strategyPhaseId
     * @param response
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping("/{strategyPhaseId}/verbalTrickDownload")
    public boolean downloadStrategyVerbalTrick(@PathVariable("strategyPhaseId") Long strategyPhaseId, HttpServletResponse response,HttpServletRequest request) throws Exception{
        return strategyService.downloadStrategyVerbalTrick(strategyPhaseId,response,request);
    }
}
