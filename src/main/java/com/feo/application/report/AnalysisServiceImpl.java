package com.feo.application.report;

import com.feo.application.OrganizationService;
import com.feo.application.UserService;
import com.feo.application.record.RecordService;
import com.feo.common.AssertUtil;
import com.feo.common.StrUtil;
import com.feo.common.UserUtil;
import com.feo.domain.model.enums.OrganizationLevel;
import com.feo.domain.model.enums.record.ScoreStatusEnums;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.domain.model.record.Record;
import com.feo.domain.model.reportform.AnalysisProperties;
import com.feo.domain.model.reportform.Analyze;
import com.feo.domain.model.reportform.QualityWeek;
import com.feo.domain.model.reportform.WeekReport;
import com.feo.domain.model.strategy.Strategy;
import com.feo.domain.model.user.User;
import com.feo.domain.repository.record.RecordRepository;
import com.feo.domain.repository.report.AnalysisRepository;
import com.feo.domain.repository.report.QualityWeekRepository;
import com.feo.domain.repository.report.WeekReportRepository;
import com.feo.domain.repository.report.WeekWorkRepository;
import com.feo.domain.repository.strategy.StrategyRepository;
import com.feo.port.rest.dto.report.AnalyzeConditions;
import com.feo.port.rest.dto.report.ReportReturn;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by yangchao on 17/11/10.
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    private RecordService recordService;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private QualityWeekRepository qualityWeekRepository;
    @Autowired
    private WeekWorkRepository weekWorkRepository;
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private WeekReportRepository weekReportRepository;
    private static Logger logger = LoggerFactory.getLogger(AnalysisServiceImpl.class);

    /**
     * 数据对比
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param orgIds
     * @return
     */
    @Override
    public Object crossDataCompare(Date startTime, Date endTime, List<Integer> orgIds) {
        /**
         * 1.机构下所有的销售组id
         * 2.每个销售组下所有用户id
         * 3.每个用户下所有的录音记录
         * 4.将每级的录音记录合并取平均
         */
        for (Integer orgId : orgIds) {
            Organization org = organizationService.queryByOrgId(orgId);
            if (StrUtil.isNull(org)) continue;

            AnalysisProperties ap = new AnalysisProperties();
            //recordService.selectRecordDetails();


        }
        return null;
    }

    @Override
    public Page<ReportReturn> reportQualityWeekQuery(AnalyzeConditions conditions) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(conditions.getPage(), conditions.getSize(), sort);
        Specification<QualityWeek> je = new Specification<QualityWeek>() {
            @Override
            public Predicate toPredicate(Root<QualityWeek> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String startTime = conditions.getStartTime();
                String endTime = conditions.getEndTime();
                List<Predicate> predicates = new ArrayList<>();
                //时间区间
                if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                    endTime = endTime + ":23:59:59";//包括最后区间
                    predicates.add(cb.between(root.get("createDate").as(String.class), startTime, endTime));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<QualityWeek> page = qualityWeekRepository.findAll(je, pageable);
        List<ReportReturn> reportReturn=new ArrayList<>();
        Page<ReportReturn> page1=new PageImpl<ReportReturn>(reportReturn,pageable,page.getTotalPages());
        return page1;
    }

    @Override
    public Page<Analyze> reportAnalyzeQuery(AnalyzeConditions conditions) {
        List<Sort.Order> list = new ArrayList<>();
        Specification<Analyze> je = new Specification<Analyze>() {
            @Override
            public Predicate toPredicate(Root<Analyze> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                String startTime = conditions.getStartTime();
                String endTime = conditions.getEndTime();
                logger.info("【分析报表查询条件传入开始结束时间】{}$$$${}", startTime, endTime);
                List<Predicate> predicates = new ArrayList<>();
                //时间区间
                if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                    endTime = endTime + ":23:59:59";//包括最后区间
                    predicates.add(criteriaBuilder.between(root.get("createDate").as(String.class), startTime, endTime));
                }
                //是否自己
                if (StringUtils.isNotBlank(conditions.getRange()) && conditions.getRange().equals("1")) {//1是代表自己
                    //predicates.add(criteriaBuilder.equal(root.get("creater"), "111"));
                    predicates.add(criteriaBuilder.equal(root.get("creater"), UserUtil.getUserNanme()));
                }
                //军团
                if (StringUtils.isNotBlank(conditions.getLegion())) {
                    predicates.add(criteriaBuilder.equal(root.get("legion"), conditions.getLegion()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //排序方式--策略执行率由低到高 enforcedPolicies
        if (StringUtils.isNotBlank(conditions.getSort()) && conditions.getSort().equals("1")) {
            list.add(new Sort.Order(Sort.Direction.ASC, "analysisProperties.enforcedPolicies"));
        }
        //排序方式--策略执行率由高到低
        if (StringUtils.isNotBlank(conditions.getSort()) && conditions.getSort().equals("2")) {//倒叙
            list.add(new Sort.Order(Sort.Direction.DESC, "analysisProperties.enforcedPolicies"));
        }
        list.add(new Sort.Order(Sort.Direction.DESC, "modifyDate"));
        Sort sort = new Sort(list);
        Pageable pageable = new PageRequest(conditions.getPage(), conditions.getSize(), sort);
        Page<Analyze> page = analysisRepository.findAll(je, pageable);
        return page;
    }

    @Override
    public Page<WeekReport> reportWeekWorkQuery(AnalyzeConditions conditions) {
        Sort sort = new Sort(Sort.Direction.DESC, "createDate");
        Pageable pageable = new PageRequest(conditions.getPage(), conditions.getSize(), sort);
        Specification<WeekReport> je = new Specification<WeekReport>() {
            @Override
            public Predicate toPredicate(Root<WeekReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                String startTime = conditions.getStartTime();
                String endTime = conditions.getEndTime();
                List<Predicate> predicates = new ArrayList<>();
                //时间区间
                if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                    endTime = endTime + ":23:59:59";//包括最后区间
                    predicates.add(cb.between(root.get("createDate").as(String.class), startTime, endTime));
                }
                //是否自己
                if (StringUtils.isNotBlank(conditions.getRange()) && conditions.getRange().equals("1")) {//1是代表自己
                    //predicates.add(criteriaBuilder.equal(root.get("creater"), "111"));
                    predicates.add(cb.equal(root.get("creater"), UserUtil.getUserNanme()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Page<WeekReport> page = weekReportRepository.findAll(je, pageable);
        return page;
    }

    @Override
    public Page<Strategy> reportStrategyWordsQuery(AnalyzeConditions conditions) {
        List<Sort.Order> list = new ArrayList<>();
        Specification<Strategy> je = new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                String startTime = conditions.getStartTime();
                String endTime = conditions.getEndTime();
                List<Predicate> predicates = new ArrayList<>();
                // Join<Strategy, AnalysisProperties> join = root.join("analysisProperties", JoinType.LEFT);
                //时间区间
                if (StringUtils.isNotBlank(startTime) && StringUtils.isNotBlank(endTime)) {
                    endTime = endTime + ":23:59:59";//包括最后区间
                    predicates.add(criteriaBuilder.between(root.get("updateTime").as(String.class), startTime, endTime));
                }
                //是否自己
                if (StringUtils.isNotBlank(conditions.getRange()) && conditions.getRange().equals("1")) {//1是代表自己
                    //predicates.add(criteriaBuilder.equal(root.get("creater"), "111"));
                    predicates.add(criteriaBuilder.equal(root.get("uid"), UserUtil.getUserNanme()));
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        //排序方式--策略执行率由低到高 enforcedPolicies
        if (StringUtils.isNotBlank(conditions.getSort()) && conditions.getSort().equals("1")) {
            list.add(new Sort.Order(Sort.Direction.ASC, "analysisProperties.enforcedPolicies"));
        }
        //排序方式--策略执行率由高到低
        if (StringUtils.isNotBlank(conditions.getSort()) && conditions.getSort().equals("2")) {//倒叙
            list.add(new Sort.Order(Sort.Direction.DESC, "analysisProperties.enforcedPolicies"));
        }
        list.add(new Sort.Order(Sort.Direction.DESC, "updateTime"));
        Sort sort = new Sort(list);
        Pageable pageable = new PageRequest(conditions.getPage(), conditions.getSize(), sort);
        Page<Strategy> page = strategyRepository.findAll(je, pageable);

        return page;
    }


    public Object spellRecordData(Organization org) {
        if (org.getOrganizationLevel().equals(OrganizationLevel.SALES_GROUP)) {
            List<User> users = userService.queryUsersByOrgId(org.getId());
            Map<String, Integer> mUser = new HashMap<>();
            //录音相关人数量
            int recordWithUserCount = 0;
            //抽检相关人数量
            int recordCheckedWithUserCount = 0;
            for (User u : users) {
                Map<String, Object> mRecord = new HashMap<>();
                //录音记录数量
                //Integer recordSumByUser = recordRepository.countAllByUser(u.getId());
                ///todo 增加方法
                Integer recordSumByUser = 1;
                mRecord.put("recordSumByUser", recordSumByUser);
                if (!Objects.equals(recordSumByUser, 0)) recordWithUserCount++;
                //抽检录音数量 = 质检抽检数
                List<Record> recordCheckeds = recordRepository.findAllBySuserAndSelectStatus(u, Integer.valueOf(ScoreStatusEnums.SAVE_SUBMIT.getCode()));
                mRecord.put("recordSumByChecked", recordCheckeds.size());
                if (!Objects.equals(recordCheckeds.size(), 0)) recordCheckedWithUserCount++;
                if (StrUtil.isNull(recordCheckeds)) continue;
                mRecord.putAll(calculateRecordData(recordCheckeds, recordCheckeds.size()));
            }
            mUser.put("recordWithUserCount", recordWithUserCount);
            mUser.put("recordCheckedWithUserCount", recordCheckedWithUserCount);

        }
        return null;
    }

    /*
    //todo 与鹰眼对接数据交互细节
    十二个指标:  销转,RPA，执行排名,抽检比例,违规率，
                探需,专业,班型,截杀,流程完整度,策略执行率,质检抽检数
    */
    /*
    探需,专业,班型,截杀
    录音记录数量，录音相关人数量，抽检录音数量，抽检相关人数量
    流程完整度
     */

    /**
     * 以人为单位统计各个指标
     *
     * @param rs
     * @return
     */
    public Map<String, Object> calculateRecordData(List<Record> rs, int recordChecked) {
        AssertUtil.isNotNull(rs);
        //流程完整度
        int finishNumHight = 0;
        int agentsScore = 0, majorsScore = 0, classTypeScore = 0, putAwayScore = 0;
        Map<String, Object> m = new HashMap<>();
        m.put("recordSum", rs.size());
        for (Record r : rs) {
            if (Objects.equals(r.getFinishNum(), 8) || Objects.equals(r.getFinishNum(), 10)) finishNumHight++;
            //探需,专业,班型,截杀 - 得分
            agentsScore++;
            majorsScore++;
            classTypeScore++;
            putAwayScore++;
        }
        m.put("finishNumHight", finishNumHight);

        m.put("agentsScore", agentsScore / recordChecked);//探需执行占比
        m.put("majorsRate", majorsScore / recordChecked);//主推专业执行占比
        m.put("classTypeRate", classTypeScore / recordChecked);//主推班型执行占比
        m.put("putAwayRate", putAwayScore / recordChecked);//截杀策略执行占比
        //策略执行率：四个指标的加和平均（主推专业执行占比、主推班型执行占比、截杀策略执行占比、流程完整性执行占比）
        double enforcedPolicies = (majorsScore / recordChecked + classTypeScore / recordChecked + putAwayScore / recordChecked + finishNumHight / recordChecked) / 4;
        m.put("enforcedPolicies", enforcedPolicies);
        return m;
    }
}
