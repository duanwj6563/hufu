package com.feo.application.report;

import com.feo.domain.model.reportform.Analyze;
import com.feo.domain.model.reportform.QualityWeek;
import com.feo.domain.model.reportform.WeekReport;
import com.feo.domain.model.strategy.Strategy;
import com.feo.port.rest.dto.report.AnalyzeConditions;
import com.feo.port.rest.dto.report.ReportReturn;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 * Created by yangchao on 17/11/10.
 */
public interface AnalysisService {
    /**
     * 数据对比
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param orgId     对比的机构id
     * @return
     */
    Object crossDataCompare(Date startTime, Date endTime, List<Integer> orgId);

    /**
     * 虎符质检综合报表
     *
     * @param conditions
     * @return
     */
    Page<ReportReturn> reportQualityWeekQuery(AnalyzeConditions conditions);
    /**
     * 102-虎符质检抽查打分表
     *
     * @param conditions
     * @return
     */
    //Page<QualityWeek> reportQualityWeekQuery(AnalyzeConditions conditions);

    /**
     * 103-军团策略与分析报告
     *
     * @param conditions
     * @return
     */
    Page<Analyze> reportAnalyzeQuery(AnalyzeConditions conditions);

    /**
     * 104-工作量报表
     *
     * @param conditions
     * @return
     */
    Page<WeekReport> reportWeekWorkQuery(AnalyzeConditions conditions);

    /**
     * 105策略话术
     *
     * @param conditions
     * @return
     */
    Page<Strategy> reportStrategyWordsQuery(AnalyzeConditions conditions);


}
