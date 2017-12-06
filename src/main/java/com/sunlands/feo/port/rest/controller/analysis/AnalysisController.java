/**
 * @describe
 * @author duanwj
 * @since 2017年9月29日 上午11:09:58
 */
package com.sunlands.feo.port.rest.controller.analysis;

import com.fasterxml.jackson.annotation.JsonView;
import com.sunlands.feo.application.report.AnalysisService;
import com.sunlands.feo.application.report.ExcelExportService;
import com.sunlands.feo.common.AssertUtil;
import com.sunlands.feo.common.UserUtil;
import com.sunlands.feo.domain.model.reportform.*;
import com.sunlands.feo.domain.model.strategy.Strategy;
import com.sunlands.feo.domain.repository.report.*;
import com.sunlands.feo.domain.repository.user.UserRepository;
import com.sunlands.feo.port.rest.dto.report.AnalyzeConditions;
import com.sunlands.feo.port.rest.dto.report.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reports")
public class AnalysisController {
    private final Logger logger = LoggerFactory.getLogger(AnalysisController.class);
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private AnalysisPropertiesRepository analysisPropertiesRepository;
    @Autowired
    private WeekWorkRepository workRepository;
    @Autowired
    private QualityWeekRepository qualityWeekRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AnalysisService analysisService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private UserRepository userRepository;


    /**
     * 查询属性报表属性信息
     *
     * @param
     * @return
     */
    @GetMapping("/strategys/{id}/properties")
    public AnalysisProperties getAnalysisProperties(@PathVariable("id") Long id) {
        return analysisPropertiesRepository.findByStrategyId(id);
    }

    /**
     * 分析报告创建
     *
     * @return
     */
    @PostMapping("/strategys")
    @JsonView(Analyze.analyzeSimpleView.class)
    //@PreAuthorize("hasRole('SOP')")//专员可以创建
    @Transactional
    public Analyze analysisAdd(@RequestBody @Valid Analyze strategyAnalyze, BindingResult bindingResult) {
        AssertUtil.bindingResult(bindingResult);
        strategyAnalyze.setCreateDate(new Date());
        strategyAnalyze.setModifyDate(new Date());
        strategyAnalyze.setId(strategyAnalyze.getStrategyId());
        analysisRepository.save(strategyAnalyze);
        return strategyAnalyze;
    }

    /**
     * 添加分析报告备注
     *
     * @return
     */
    @PostMapping("/{id}/comments")
    @Transactional
    public Commet analysisComment(@RequestBody @Valid Commet commet, BindingResult bindingResult) {
        AssertUtil.bindingResult(bindingResult);
        commet.setCrtDate(new Date());
        commet.setUid(UserUtil.getUserNanme());
        commet.setName(userRepository.findByUsername(UserUtil.getUserNanme()).getName());
        commentRepository.save(commet);
        return commet;
    }

    /**
     * 查询分析报告所有备注
     *
     * @param
     * @return
     */
    @GetMapping(value = "/{id}/comments")
    public Page<Commet> getCommentInfo(@PathVariable("id") Long id, PageParameters pageParameters) {
        String uid = UserUtil.getUserNanme();//登陆人的uid
        //String uid = "88";
        Pageable pageable = new PageRequest(pageParameters.getPage(), pageParameters.getSize());
        return commentRepository.findAllByAnalyzeIdAndUidOrderByCrtDateDesc(id, uid, pageable);
    }

    /**
     * 虎符质检综合报表---101
     *
     * @param conditions
     * @return
     */
    @GetMapping("/qualityWeeks")
    public Page<QualityWeek> qualityWeekQuery(AnalyzeConditions conditions) {
        return analysisService.reportQualityWeekQuery(conditions);
    }

    /**
     * 查询质检周报详情
     *
     * @param
     * @return
     */
    @GetMapping(value = "/qualityWeeks/{id}")
    public List<QualityWeek> getQualityWeekInfo(@PathVariable("id") Long qualityWeekId) {
        return qualityWeekRepository.findAllById(qualityWeekId);
    }

    /**
     * 质检综合报表下载
     *
     * @param
     * @return
     */
    @GetMapping(value = "/qualityWeeks/download")
    public void qualityWeeksDownload(@RequestParam(value = "ids", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AssertUtil.isNotNull(ids, "请选择下载文件");
        excelExportService.qualityWeeks(ids, request, response, out);
    }
    /**
     * 102-虎符质检抽查打分表
     *
     * @param conditions
     * @return
     */
   /* @GetMapping("/qualityGrades")
    public Page<ReportReturn> analysisQuery(@Valid AnalyzeConditions conditions) {
        return analysisService.reportQuery(conditions);
    }*/

    /**
     * 虎符质检抽查打分表下载
     *
     * @param
     * @return
     */
    @GetMapping(value = "/record/download")
    public void recordDownload(@RequestParam(value = "ids", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AssertUtil.isNotNull(ids, "请选择下载文件");
        excelExportService.record(ids, request, response, out);
    }

    /**
     * 103-军团策略与分析报告
     *
     * @param conditions
     * @return
     */
    @GetMapping("/analyzes")
    public Page<Analyze> analyzeQuery(AnalyzeConditions conditions) {
        return analysisService.reportAnalyzeQuery(conditions);
    }

    /**
     * 查询分析报告详情
     *
     * @param
     * @return
     */
    @GetMapping(value = "strategys/{id}")
    public Analyze getAnalysisInfo(@PathVariable("id") Long id) {
        return analysisRepository.findById(id);
    }

    /**
     * 军团策略与分析报告下载
     *
     * @param
     * @return
     */
    @GetMapping(value = "/analyzes/download")
    public void analyzesDownload(@RequestParam(value = "ids", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AssertUtil.isNotNull(ids, "请选择下载文件！");
        excelExportService.analyzes(ids, request, response, out);
    }

    /**
     * 105 策略话术 -详情和下载复用
     *
     * @param conditions
     * @return
     */
    @GetMapping("/strategyWords")
    public Page<Strategy> strategyQuery(AnalyzeConditions conditions) {
        return analysisService.reportStrategyWordsQuery(conditions);
    }

    /**
     * 104-工作量报表
     *
     * @param conditions
     * @return
     */
    @GetMapping("/weeks")
    public Page<WeekReport> weekWorkQuery(AnalyzeConditions conditions) {
        return analysisService.reportWeekWorkQuery(conditions);
    }

    /**
     * 查询周工作量详情
     *
     * @param
     * @return
     */
    @GetMapping(value = "/week/{id}")
    public List<WeekWork> getWeekWorkInfo(@PathVariable("id") Long weekWorkId) {
        //return workRepository.findAllByIdOrderByRank(weekWorkId);
        return workRepository.findByWeekIdOrderByRankAsc(weekWorkId);
    }

    /**
     * 周工作量下载
     *
     * @param
     * @return
     */
    @GetMapping(value = "/week/download")
    public void weekDownload(@RequestParam(value = "ids", required = false) Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        AssertUtil.isNotNull(ids, "请选择下载文件");
        excelExportService.weekWeek(ids, request, response, out);
    }

}
