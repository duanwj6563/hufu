package com.feo.application.record.impl;

import com.feo.application.OrganizationService;
import com.feo.application.UserService;
import com.feo.application.boutique.BoutiqueSevice;
import com.feo.application.record.RecordService;
import com.feo.common.DateUtil;
import com.feo.common.StrUtil;
import com.feo.domain.exception.ServerStatus;
import com.feo.domain.exception.UserDefinedException;
import com.feo.domain.model.enums.record.RecTypeEnums;
import com.feo.domain.model.enums.record.RecordTypeEnums;
import com.feo.domain.model.enums.record.ScoreStatusEnums;
import com.feo.domain.model.enums.strategy.AreaEnums;
import com.feo.domain.model.enums.strategy.ProjectEnums;
import com.feo.domain.model.enums.strategy.ScoreTypeEnums;
import com.feo.domain.model.record.*;
import com.feo.domain.model.strategy.*;
import com.feo.domain.model.user.User;
import com.feo.domain.repository.record.*;
import com.feo.domain.repository.strategy.StrategyPhaseRepository;
import com.feo.domain.repository.strategy.StrategyRepository;
import com.feo.port.rest.dto.record.AddRecordScoreInfo;
import com.feo.port.rest.dto.record.AddViolationInfo;
import com.feo.port.rest.dto.record.SelectRecodCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * 质检业务层
 * Created by huang on 2017/11/2.
 */
@Service
public class RecordServiceImpl implements RecordService {

    //组织结构Service
    private OrganizationService organizationService;

    //质检Repository
    private RecordRepository recordRepository;

    //质检违规Repository
    private ViolationInfoRepository violationInfoRepository;

    //策略阶段Repository
    private StrategyPhaseRepository strategyPhaseRepository;

    //录音原生sqlRepository
    private RecordSqlRepository recordSqlRepository;

    //策略Repository
    private StrategyRepository strategyRepository;

    //质检策略打分Repository
    private RecordStrategyRepository recordStrategyRepository;

    //质检问题Repository
    private QuestionRepository questionRepository;

    //精品库Sevice
    private BoutiqueSevice boutiqueSevice;

    //用户Service
    private UserService userService;

    @Override
    public Map<String, Object> selectRecordList(SelectRecodCondition selectRecodCondition) {
        Map<String, Object> flag = new HashMap<>();
        Map<String, List<Map<String, Object>>> temp = new HashMap<>();
        Integer num = 0;
        //分页查询录音质检列表
        List<Integer> ids = getOrgIdsUtil(selectRecodCondition);
        List<Map<String, Object>> list = recordSqlRepository.selectRecordList(selectRecodCondition, ids);
        for (Map<String, Object> map : list) {
            //分页回显组织结构
            flag.put(map.get("orgId") + "", null);
            if (flag.size() > num) {
                List<Map<String, Object>> upOrg = organizationService.getUpOrg((Integer) map.get("orgId"));
                temp.put(map.get("orgId") + "", upOrg);
                num = flag.size();
                map.put("enterpriseName", upOrg.get(3).get("name"));
                map.put("groupName", upOrg.get(2).get("name"));
                map.put("sellAndGroupName", upOrg.get(1).get("name") + "" + upOrg.get(0).get("name"));
            } else {
                List<Map<String, Object>> listTemp = temp.get(map.get("orgId") + "");
                map.put("enterpriseName", listTemp.get(3).get("name"));
                map.put("groupName", listTemp.get(2).get("name"));
                map.put("sellAndGroupName", listTemp.get(1).get("name") + "" + listTemp.get(0).get("name"));
            }
            map.put("recType", RecTypeEnums.values()[(Integer) map.get("recType") - 1].getName());
            map.put("recordType", RecordTypeEnums.values()[(Integer) map.get("recordType")].getName());
        }
        //调用分页工具
        Integer totalRecord = recordSqlRepository.selectRecordListCount(selectRecodCondition, ids);
        List<Map<String, Object>> selelctCount = selectRecordShoppingCartList();
        return totalPages(list, totalRecord, selectRecodCondition.getSize(), selectRecodCondition.getPage(), selelctCount.size());
    }

    @Override
    public Map<String, Object> selectRecordDetailOne(Long recordId) {
        Map<String, Object> resultMap = new HashMap<>();
        //查询录音报名信息&订单信息
        Record record = recordRepository.findOne(recordId);
        //录音下拉框选择项(录音质检信息)
        Map<String, Object> checkInfo = record.selectRecordCheckInfo();
        //返回组织架构
        Map<String, Object> orgMap = new HashMap<>();
        List<Map<String, Object>> upOrg = organizationService.getUpOrg(record.getUserInfo().getOrganization().getId());
        orgMap.put("userName", record.getUserInfo().getUsername());
        orgMap.put("enterpriseName", upOrg.get(3).get("name"));
        orgMap.put("groupName", upOrg.get(2).get("name"));
        orgMap.put("sellAndGroupName", upOrg.get(1).get("name") + "" + upOrg.get(0).get("name"));
        orgMap.put("userBelong", record.getUserInfo().getEmail());
        //违规信息
        List<Map<String, Object>> errors = recordSqlRepository.selectRecordViolationInfo(recordId, 1);
        if (StrUtil.isNotNull(record)) {
            resultMap.put("studentAndOrder", record.selectRecordStudentAndOrder());
            resultMap.put("checkInfo", checkInfo);
            resultMap.put("errorBasic", orgMap);
            resultMap.put("errors", errors);
            return resultMap;
        }
        //数据获取失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Override
    public Map<String, Object> selectRecordDetailTwo(Long recordId) {
        Map<String, Object> resultMap = new HashMap<>();
        //查询录音地址&时长
        Record record = recordRepository.findOne(recordId);
        //查询对应违规属性
        List<Map<String, Object>> errors = recordSqlRepository.selectRecordViolationInfo(recordId, 0);
        //返回数据
        if (StrUtil.isNotNull(record)) {
            resultMap.put("url", record.getUrl());
            resultMap.put("timeLength", record.getTimeLength());
            resultMap.put("errors", errors);
            return resultMap;
        }
        //数据获取失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Override
    public Map<String, Object> selectRecordDetailThree(Long recordId, Integer recType) {
        Map<String, Object> resultTwoMap = new HashMap<>();
        Map<String, Object> resultOneMap = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        //查询录音，获取参数
        Record record = recordRepository.findOne(recordId);
        if (recType == 0) {
            recType = record.getRecType();
        }
        //策略阶段回显信息
        List<Map<String, Object>> upOrg = organizationService.getUpOrg(record.getUserInfo().getOrganization().getId());
        Strategy strategy = strategyRepository.findOne(record.getStrategyId());
        resultOneMap.put("area", AreaEnums.values()[strategy.getArea()].getName());
        resultOneMap.put("groupName", upOrg.get(2).get("name"));
        resultOneMap.put("firstProject", ProjectEnums.values()[strategy.getFirstProjectId()].getName());
        resultOneMap.put("applydeparts", upOrg.get(1).get("name") + "" + upOrg.get(0).get("name"));
        resultOneMap.put("name", RecTypeEnums.values()[recType - 1].getName());
        //判断打分表是否存在&没有改变录音性质
        if (StrUtil.isNotNull(record.getRecordStrategys()) && recType.equals(record.getRecType())) {
            //对打分类型排序
            List<RecordStrategy> recordStrategys = getOrderList(record.getRecordStrategys());
            //查询问题打分勾选框
            for (RecordStrategy recordStrategy : recordStrategys) {
                Map<String, Object> resultMap = new HashMap<>();
                Integer scoreType = recordStrategy.getScoreType();
                List<Map<String, Object>> list = recordSqlRepository.selectRecordStrategyScore(scoreType, recordId);
                resultMap.put("id", recordStrategy.getId());
                resultMap.put("score", recordStrategy.getScore());
                resultMap.put("scoreNum", recordStrategy.getScoreNum());
                resultMap.put("scoreType", recordStrategy.getScoreType());
                resultMap.put("scoreName", recordStrategy.getScoreName());
                resultMap.put("recordStrategyScores", list);
                resultList.add(resultMap);
            }
        } else {
            resultList = selectRecordStrategyUtil(recType, record.getStrategyId());
        }
        //返回数据
        if (StrUtil.isNotNull(record)) {
            resultTwoMap.put("recordStrategy", resultList);
            resultTwoMap.put("strategyPhaseInfo", resultOneMap);
            return resultTwoMap;
        }
        //数据获取失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Override
    public Map<String, Object> selectRecordViolation(Long recordId, Long violationInfoId) {
        //查询违规详情
        ViolationInfo violationInfo = violationInfoRepository.findOne(violationInfoId);
        Record record = recordRepository.findOne(recordId);
        Map<String, Object> map = violationInfo.selectViolationInfoDatails();
        //返回组织架构
        List<Map<String, Object>> upOrg = organizationService.getUpOrg(record.getUserInfo().getOrganization().getId());
        map.put("userName", record.getUserInfo().getUsername());
        map.put("enterpriseName", upOrg.get(3).get("name"));
        map.put("groupName", upOrg.get(2).get("name"));
        map.put("sellAndGroupName", upOrg.get(1).get("name") + "" + upOrg.get(0).get("name"));
        map.put("userBelong", record.getUserInfo().getEmail());
        if (StrUtil.isNotNull(violationInfo)) {
            return map;
        }
        //数据获取失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Transactional
    @Override
    public boolean addRecordViolation(Long recordId, AddViolationInfo addViolationInfo) {
        //查询录音详情
        Record record = recordRepository.findOne(recordId);
        ViolationInfo violationInfo = new ViolationInfo();
        //判断是否本人操作
        if (getUser().getId().equals(record.getSuser().getId())) {
            //判断违规信息是否违规
            if (record.getIsViolation() == 0) {
                record.setIsViolation(1);
            }
            //判断违规详情是否存在
            if (addViolationInfo.getId() == null || addViolationInfo.getId() == 0) {
                violationInfo = violationInfo.changeViolationInfo(addViolationInfo, violationInfo);
                violationInfo.setIsSystem(0);
                violationInfo.setUserInfo(record.getUserInfo());
                record.getViolationInfos().add(violationInfo);
                Record save = recordRepository.saveAndFlush(record);
                if (StrUtil.isNotNull(save)) {
                    return true;
                }
            } else {
                //查询原违规信息
                ViolationInfo older = violationInfoRepository.findOne(addViolationInfo.getId());
                //更新违规信息
                older = violationInfo.changeViolationInfo(addViolationInfo, older);
                ViolationInfo save = violationInfoRepository.save(older);
                if (StrUtil.isNotNull(save)) {
                    return true;
                }
            }
            //数据提交失败，服务器繁忙
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
        }
        //非本人操作
        throw new UserDefinedException(ServerStatus.FORBIDDEN);
    }

    @Transactional
    @Override
    public List<Long> addRecordShoppingCart(Long[] recordIdArrays) {
        //用户最多添加的次数
        Integer flag = 30;
        List<Record> records = new ArrayList<>();
        List<Long> resultList = new ArrayList<>();
        //查询登录用户信息
        User user = getUser();
        //判断用户是否打包超过上限(30次)
        Integer integer = recordRepository.countAllBySuserAndStatusOrStatusAndSelectStatus(user.getId(), 1, Integer.parseInt(ScoreStatusEnums.SAVE_SUBMIT.getCode()));
        if (integer < flag) {
            //判断可循环次数
            List<Record> allList = recordRepository.findAllByIdIn(recordIdArrays);
            Integer index = allList.size();
            if (index > (flag - integer)) {
                index = (flag - integer);
            }
            for (Integer i = 0; i < index; i++) {
                //判断录音此时状态是否可以选定
                Record record = allList.get(i);
                if (record.getSelectStatus() == 0) {
                    record.setTestDate(new Date());
                    record.setSelectStatus(1);
                    record.setSuser(user);
                    //添加符合条件录音集合
                    records.add(record);
                }
            }
            //更新录音状态（已占用）
            List<Record> save = recordRepository.save(records);
            for (Record record : save) {
                resultList.add(record.getId());
            }
            return resultList;
        }
        //超过可添加的质检打分数量
        throw new UserDefinedException(ServerStatus.OPERATION_FAILED, "超过可添加的质检打分数量");
    }

    @Override
    public List<Map<String, Object>> selectRecordShoppingCartList() {
        //查询录音任务列表
        return recordSqlRepository.selelctTaskList(getUser().getId());
    }

    @Transactional
    @Override
    public boolean addRecordScoreToSave(Record record) {
        //获取用户信息和录音质检员
        Record recordTemp = recordRepository.findOne(record.getId());
        User user = getUser();
        //判断是否本人操作
        if (user.getId().equals(recordTemp.getSuser().getId())) {
            //删除更新的级联信息
            Set<RecordStrategy> recordStrategys = recordTemp.getRecordStrategys();
            recordStrategyRepository.delete(recordStrategys);
            Set<Question> questions = recordTemp.getQuestions();
            questionRepository.delete(questions);
            //更新或者保存质检打分
            AddRecordScoreInfo addRecordScoreInfo = new AddRecordScoreInfo();
            record = addRecordScoreInfo.changeRecordScore(record, recordTemp);
            //根据规则进行打分
            record = addRecordScoreLogic(record);
            Record recordResult = recordRepository.saveAndFlush(record);
            if (StrUtil.isNotNull(recordResult)) {
                return true;
            }
            //数据提交失败，服务器繁忙
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
        }
        //非本人操作
        throw new UserDefinedException(ServerStatus.FORBIDDEN);
    }

    @Transactional
    @Override
    public boolean addRecordScoreToSubmit(Long[] recordIdArrays) {
        //获取用户信息&录音信息
        List<Record> recordList = recordRepository.findAllByIdIn(recordIdArrays);
        List<Record> errors = new ArrayList<>();
        User user = getUser();
        //判断是否本人操作&是否保存
        Iterator<Record> iterator = recordList.iterator();
        while (iterator.hasNext()) {
            Record r = iterator.next();
            //判断是否本人操作
            if (!user.getId().equals(r.getSuser().getId())) {
                throw new UserDefinedException(ServerStatus.FORBIDDEN);
            }
            //改变已保存质检状态&移除未保存的质检
            if (r.getStatus() == Integer.parseInt(ScoreStatusEnums.SAVE_NOSUBMIT.getCode())) {
                r.setTestDate(new Date());
                r.setStatus(Integer.parseInt(ScoreStatusEnums.SAVE_SUBMIT.getCode()));
            } else {
                iterator.remove();
                errors.add(r);
            }
        }
        List<Record> save = recordRepository.save(recordList);
        //优秀类型自动设为精品
        for (Record record : recordList) {
            if (record.getIsGood() == 1 && record.getGoodType() != null) {
                boutiqueSevice.setBoutiqueRecord(record.getId());
            }
        }
        if (StrUtil.isNotNull(save)) {
            return true;
        }
        if (StrUtil.isNotNull(errors)) {
            //没有已保存的质检，没有可提交的项
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION, "没有已保存的质检，没有可提交的项");
        }
        //数据提交失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Transactional
    @Override
    public String deleteRecordShoppingCart() {
        //批量查询所有未提交的质检打分
        Integer[] statusArr = {Integer.parseInt(ScoreStatusEnums.NOSAVE_NOSUBMIT.getCode()), Integer.parseInt(ScoreStatusEnums.SAVE_NOSUBMIT.getCode())};
        List<Record> recordList = recordRepository.findAllBySelectStatusAndStatusIn(1, statusArr);
        //清空录音可填选数据
        AddRecordScoreInfo addRecordScoreInfo = new AddRecordScoreInfo();
        recordList = addRecordScoreInfo.deleteRecordScore(recordList);
        List<Record> saveList = recordRepository.save(recordList);
        if (saveList.size() == recordList.size()) {
            return ServerStatus.success;
        }
        //数据清空失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }


    @Override
    public Map<String, Object> selectRecordDayListen() {
        Map<String, Object> map = new HashMap<>();
        //查询登录用户信息
        User user = getUser();
        //查询五天前时间
        String startDate = DateUtil.getRollDate(5);
        String endDate = DateUtil.dateToString(new Date());
        //查询时间区间内数据
        List<Map<String, Object>> list = recordSqlRepository.selectRecordDayListen(user.getId(), endDate, startDate);
        //封装数据
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("recordDay", list);
        return map;
    }

    @Override
    public Map<String, Object> selectRecordWeekDetail() {
        Map<String, Object> map = new HashMap<>();
        //查询登录用户信息
        User user = getUser();
        //查询第一周信息
        String startOneWeek = DateUtil.getRollDate(7);
        String endOneWeek = DateUtil.dateToString(new Date());
        Integer oneWeekCount = recordSqlRepository.selectRecordWeekListen(user.getId(), startOneWeek, endOneWeek);
        map.put("oneWeekCount", oneWeekCount);
        //查询第二周信息
        String startTwoWeek = DateUtil.getRollDate(14);
        Integer twoWeekCount = recordSqlRepository.selectRecordWeekListen(user.getId(), startTwoWeek, startOneWeek);
        map.put("twoWeekCount", twoWeekCount);
        //查询第三周信息
        String startThreeWeek = DateUtil.getRollDate(21);
        Integer threeWeekCount = recordSqlRepository.selectRecordWeekListen(user.getId(), startThreeWeek, startTwoWeek);
        map.put("threeWeekCount", threeWeekCount);
        return map;
    }

    @Override
    public List<Map<String, Object>> selectRecordListenDetail() {
        //查询登录用户信息
        User user = getUser();
        //查询工作台详细数据
        //TODO 数据暂时2条，需要不全
        return recordSqlRepository.selectRecordListenDetail(user.getId());
    }

    @Transactional
    @Override
    public boolean deleteRecordViolation(Long callId, Long violationInfoId) {
        //查询登录用户信息
        User user = getUser();
        Record record = recordRepository.findOne(callId);
        //判断是否本人操作
        if (user.getId().equals(record.getSuser().getId())) {
            Set<ViolationInfo> violationInfos = record.getViolationInfos();
            //遍历移除
            Iterator it = violationInfos.iterator();
            while (it.hasNext()) {
                ViolationInfo v = (ViolationInfo) it.next();
                if (v.getId().equals(violationInfoId)) {
                    it.remove();
                }
            }
            Record save = recordRepository.save(record);
            if (StrUtil.isNotNull(save)) {
                return true;
            }
            //数据清空失败，服务器繁忙
            throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
        }
        //非本人操作
        throw new UserDefinedException(ServerStatus.FORBIDDEN);
    }

    /**
     * 获取当前登录用户
     *
     * @return 返回用户实体类
     */
    public User getUser() {
        return userService.getUser();
    }

    /**
     * 封装分页数据
     *
     * @param list                 数据
     * @param totalRecord          总条数
     * @param size                 每页多少条
     * @param page                 当前第几页
     * @param currentSelectedCount 用户选中的条数
     * @return 返回分页数据
     */
    private Map<String, Object> totalPages(List<Map<String, Object>> list, Integer totalRecord, Integer size, Integer page, Integer currentSelectedCount) {
        //
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> countMap = new HashMap<>();
        countMap.put("content", list);
        countMap.put("currentSelectedCount", currentSelectedCount);
        Integer totalPage = totalRecord % size == 0 ? totalRecord / size : totalRecord / size + 1;
        resultMap.put("content", countMap);
        resultMap.put("totalElements", totalRecord);
        resultMap.put("size", size);
        resultMap.put("number", page);
        resultMap.put("totalPages", totalPage);
        return resultMap;
    }

    /**
     * 打分类型表排序
     *
     * @param recordStrategys 质检打分条件
     * @return 排序质检打分类型表
     */
    private List<RecordStrategy> getOrderList(Set<RecordStrategy> recordStrategys) {
        List<RecordStrategy> list = new ArrayList<>();
        RecordStrategy rs = new RecordStrategy();
        for (Integer i = 0; i < recordStrategys.size(); i++) {
            list.add(i, rs);
        }
        for (RecordStrategy recordStrategy : recordStrategys) {
            Integer scoreNum = recordStrategy.getScoreNum();
            list.add(scoreNum, recordStrategy);
            list.remove(scoreNum + 1);
        }
        return list;
    }

    /**
     * 根据组织获得旗下销售部id
     *
     * @param selectRecodCondition 质检查询条件
     * @return 返回所有销售部id
     */
    private List<Integer> getOrgIdsUtil(SelectRecodCondition selectRecodCondition) {
        List<Integer> ids = new ArrayList<>();
        Integer id = selectRecodCondition.getEnterpriseId();
        if (StrUtil.isNotNullAndNo0(id)) {
            Integer groupId = selectRecodCondition.getGroupId();
            if (StrUtil.isNotNullAndNo0(groupId)) {
                id = groupId;
                Integer sellId = selectRecodCondition.getSellId();
                if (StrUtil.isNotNullAndNo0(sellId)) {
                    id = sellId;
                    Integer sellGroupId = selectRecodCondition.getSellGroupId();
                    if (StrUtil.isNotNullAndNo0(sellGroupId)) {
                        id = sellGroupId;
                    }
                }
            }
            ids = organizationService.getDowmOrg(id);
        }
        return ids;
    }

    /**
     * 打分类型表排序
     *
     * @param older 未排序的集合
     * @return 排序的集合
     */
    private List<Map<String, Object>> getOrderListByMap(List<Map<String, Object>> older) {
        List<Map<String, Object>> newer = new ArrayList<>();
        Map<String, Object> rs;
        for (Integer i = 0; i < older.size(); i++) {
            rs = new HashMap<>();
            newer.add(i, rs);
        }
        for (Map<String, Object> map : older) {
            Integer number = (Integer) map.get("number");
            newer.add(number, map);
            newer.remove(number + 1);
        }
        return newer;
    }

    /**
     * 质检打分类型问题表计算规则
     *
     * @param record 质检实体类
     * @return 质检总得分
     */
    private Record addRecordScoreLogic(Record record) {
        int sumSorce = 0;
        Set<RecordStrategy> recordStrategys = record.getRecordStrategys();
        for (RecordStrategy recordStrategy : recordStrategys) {
            //查询勾选中数量
            Set<RecordStrategyScore> recordStrategyScores = recordStrategy.getRecordStrategyScores();
            //初始化选中数量
            Integer temp = 0;
            //初始化应该选中数量
            Integer LogicNum = 0;
            for (RecordStrategyScore recordStrategyScore : recordStrategyScores) {
                if ("1".equals(recordStrategyScore.getIsSelect() + "")) {
                    temp++;
                }
            }
            //第一列规则（5中3）
            Integer scoreType = recordStrategy.getScoreType();
            if (scoreType.equals(ScoreTypeEnums.NEED.getCode()) || scoreType.equals(ScoreTypeEnums.TRIGGER_OPEN.getCode())) {
                LogicNum = 3;
            }
            //第二,三列规则（1中1）
            if (scoreType.equals(ScoreTypeEnums.MAIN_PROJECT.getCode()) || scoreType.equals(ScoreTypeEnums.MAIN_MAJOR.getCode()) || scoreType.equals(ScoreTypeEnums.MAIN_SCHOOL.getCode()) || scoreType.equals(ScoreTypeEnums.FIRST_ASK_QUESTION.getCode()) || scoreType.equals(ScoreTypeEnums.ASSIST_TOOL.getCode()) || scoreType.equals(ScoreTypeEnums.MAIN_CLASS.getCode())) {
                LogicNum = 1;
            }
            //第四列规则（2中2）
            if (scoreType.equals(ScoreTypeEnums.KILL.getCode())) {
                LogicNum = 2;
            }
            //最后判断得分
            if (temp >= LogicNum) {
                recordStrategy.setScore(1);
            } else {
                recordStrategy.setScore(0);
            }
            //总得分
            sumSorce = sumSorce + recordStrategy.getScore();
        }
        record.setSumScore(sumSorce);
        return record;
    }

    /**
     * 打分类型表初始化查询
     *
     * @param recType    质检性质（售资1，回访2，库存3）
     * @param strategyId 策略id
     * @return 打分类型表初始化详情
     */
    private List<Map<String, Object>> selectRecordStrategyUtil(Integer recType, Long strategyId) {
        //获取策略阶段
        StrategyPhase strategyPhase = strategyPhaseRepository.findByPhaseAndStrategyId(recType, strategyId);
        //初始化问题打分容器
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> resultList;
        //首咨阶段
        if (strategyPhase.getPhase() == 1) {
            resultList = new ArrayList<>();
            Map<String, Object> needMap = new HashMap<>();
            FirstStrategyPhase firstStrategyPhase = (FirstStrategyPhase) strategyPhase;
            //探需问题
            Set<Need> needs = firstStrategyPhase.getNeeds();
            for (Need need : needs) {
                Map<String, Object> map = new HashMap<>();
                map.put("scoreTypeId", need.getId());
                map.put("id", null);
                map.put("name", need.getNeedName());
                map.put("isSelect", "0");
                map.put("number", need.getNumber());
                resultList.add(map);
            }
            needMap.put("recordStrategyScores", getOrderListByMap(resultList));
            needMap.put("scoreName", ScoreTypeEnums.NEED.getName());
            needMap.put("scoreType", ScoreTypeEnums.NEED.getCode());
            needMap.put("scoreNum", 0);
            needMap.put("id", null);
            needMap.put("score", 0);
            result.add(needMap);
            //主推专业/主推院校/主推项目
            resultList = new ArrayList<>();
            Map<String, Object> secondProjectsMap = new HashMap<>();
            Set<SecondProject> secondProjects = firstStrategyPhase.getSecondProjects();
            String name = "";
            Integer code = 0;
            for (SecondProject secondProject : secondProjects) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", null);
                map.put("scoreTypeId", secondProject.getId());
                map.put("name", secondProject.getDescription());
                map.put("isSelect", "0");
                map.put("number", secondProject.getNumber());
                resultList.add(map);
                name = secondProject.getType();
            }
            if (name.equals(ScoreTypeEnums.MAIN_MAJOR.getName())) {
                code = ScoreTypeEnums.MAIN_MAJOR.getCode();
            }
            if (name.equals(ScoreTypeEnums.MAIN_SCHOOL.getName())) {
                code = ScoreTypeEnums.MAIN_SCHOOL.getCode();
            }
            if (name.equals(ScoreTypeEnums.MAIN_PROJECT.getName())) {
                code = ScoreTypeEnums.MAIN_PROJECT.getCode();
            }
            secondProjectsMap.put("recordStrategyScores", getOrderListByMap(resultList));
            secondProjectsMap.put("scoreName", name);
            secondProjectsMap.put("scoreType", code);
            secondProjectsMap.put("scoreNum", 1);
            secondProjectsMap.put("id", null);
            secondProjectsMap.put("score", 0);
            result.add(secondProjectsMap);
        }
        //回访阶段
        if (strategyPhase.getPhase() == 2) {
            resultList = new ArrayList<>();
            Map<String, Object> needMap = new HashMap<>();
            SecStrategyPhase secStrategyPhase = (SecStrategyPhase) strategyPhase;
            //探需问题
            Set<Need> needs = secStrategyPhase.getNeeds();
            for (Need need : needs) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", need.getNeedName());
                map.put("id", null);
                map.put("scoreTypeId", need.getId());
                map.put("isSelect", "0");
                map.put("number", need.getNumber());
                resultList.add(map);
            }
            needMap.put("recordStrategyScores", getOrderListByMap(resultList));
            needMap.put("scoreName", ScoreTypeEnums.NEED.getName());
            needMap.put("scoreType", ScoreTypeEnums.NEED.getCode());
            needMap.put("scoreNum", 0);
            needMap.put("id", null);
            needMap.put("score", 0);
            result.add(needMap);
            //解决首咨遗留问题
            resultList = new ArrayList<>();
            Map<String, Object> solveFirstProblemsMap = new HashMap<>();
            Set<SolveFirstProblem> solveFirstProblems = secStrategyPhase.getSolveFirstProblems();
            for (SolveFirstProblem solveFirstProblem : solveFirstProblems) {
                Map<String, Object> map = new HashMap<>();
                map.put("scoreTypeId", solveFirstProblem.getId());
                map.put("name", solveFirstProblem.getSolve());
                map.put("isSelect", "0");
                map.put("id", null);
                map.put("number", solveFirstProblem.getNumber());
                resultList.add(map);
            }
            solveFirstProblemsMap.put("recordStrategyScores", getOrderListByMap(resultList));
            solveFirstProblemsMap.put("scoreName", ScoreTypeEnums.FIRST_ASK_QUESTION.getName());
            solveFirstProblemsMap.put("scoreType", ScoreTypeEnums.FIRST_ASK_QUESTION.getCode());
            solveFirstProblemsMap.put("scoreNum", 1);
            solveFirstProblemsMap.put("id", null);
            solveFirstProblemsMap.put("score", 0);
            result.add(solveFirstProblemsMap);
        }
        //库存阶段
        if (strategyPhase.getPhase() == 3) {
            resultList = new ArrayList<>();
            Map<String, Object> finalStrategyPhaseMap = new HashMap<>();
            FinalStrategyPhase finalStrategyPhase = (FinalStrategyPhase) strategyPhase;
            //库存跨期触发式开场
            Set<TriggerOpen> triggerOpens = finalStrategyPhase.getTriggerOpens();
            for (TriggerOpen triggerOpen : triggerOpens) {
                Map<String, Object> map = new HashMap<>();
                map.put("scoreTypeId", triggerOpen.getId());
                map.put("name", triggerOpen.getTriggerOpen());
                map.put("id", null);
                map.put("isSelect", "0");
                map.put("number", triggerOpen.getNumber());
                resultList.add(map);
            }
            finalStrategyPhaseMap.put("recordStrategyScores", getOrderListByMap(resultList));
            finalStrategyPhaseMap.put("scoreName", ScoreTypeEnums.TRIGGER_OPEN.getName());
            finalStrategyPhaseMap.put("scoreType", ScoreTypeEnums.TRIGGER_OPEN.getCode());
            finalStrategyPhaseMap.put("scoreNum", 0);
            finalStrategyPhaseMap.put("id", null);
            finalStrategyPhaseMap.put("score", 0);
            result.add(finalStrategyPhaseMap);
            //库存跨期辅助咨询工具
            resultList = new ArrayList<>();
            Map<String, Object> assistToolsMap = new HashMap<>();
            Set<AssistTool> assistTools = finalStrategyPhase.getAssistTools();
            for (AssistTool solveFirstProblem : assistTools) {
                Map<String, Object> map = new HashMap<>();
                map.put("scoreTypeId", solveFirstProblem.getId());
                map.put("name", solveFirstProblem.getTool());
                map.put("id", null);
                map.put("isSelect", "0");
                map.put("number", solveFirstProblem.getNumber());
                resultList.add(map);
            }
            assistToolsMap.put("recordStrategyScores", getOrderListByMap(resultList));
            assistToolsMap.put("scoreName", ScoreTypeEnums.ASSIST_TOOL.getName());
            assistToolsMap.put("scoreType", ScoreTypeEnums.ASSIST_TOOL.getCode());
            assistToolsMap.put("scoreNum", 1);
            assistToolsMap.put("id", null);
            assistToolsMap.put("score", 0);
            result.add(assistToolsMap);
        }
        //主推班型
        resultList = new ArrayList<>();
        Map<String, Object> mainClassSizesMap = new HashMap<>();
        Set<ClassSize> mainClassSizes = strategyPhase.getMainClassSizes();
        for (ClassSize classSize : mainClassSizes) {
            Map<String, Object> map = new HashMap<>();
            map.put("scoreTypeId", classSize.getId());
            map.put("isSelect", "0");
            map.put("id", null);
            map.put("name", classSize.getDescription());
            map.put("number", classSize.getNumber());
            resultList.add(map);
        }
        mainClassSizesMap.put("recordStrategyScores", getOrderListByMap(resultList));
        mainClassSizesMap.put("scoreName", ScoreTypeEnums.MAIN_CLASS.getName());
        mainClassSizesMap.put("scoreType", ScoreTypeEnums.MAIN_CLASS.getCode());
        mainClassSizesMap.put("scoreNum", 2);
        mainClassSizesMap.put("id", null);
        mainClassSizesMap.put("score", 0);
        result.add(mainClassSizesMap);
        //截杀策略
        resultList = new ArrayList<>();
        Map<String, Object> killStrategiesMap = new HashMap<>();
        Set<KillStrategy> killStrategies = strategyPhase.getKillStrategies();
        for (KillStrategy killStrategy : killStrategies) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", killStrategy.getDescription());
            map.put("scoreTypeId", killStrategy.getId());
            map.put("isSelect", "0");
            map.put("id", null);
            map.put("number", killStrategy.getNumber());
            resultList.add(map);
        }
        killStrategiesMap.put("recordStrategyScores", getOrderListByMap(resultList));
        killStrategiesMap.put("scoreName", ScoreTypeEnums.KILL.getName());
        killStrategiesMap.put("scoreType", ScoreTypeEnums.KILL.getCode());
        killStrategiesMap.put("scoreNum", 3);
        killStrategiesMap.put("id", null);
        killStrategiesMap.put("score", 0);
        result.add(killStrategiesMap);
        //返回数据
        return result;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setViolationInfoRepository(ViolationInfoRepository violationInfoRepository) {
        this.violationInfoRepository = violationInfoRepository;
    }

    @Autowired
    public void setStrategyPhaseRepository(StrategyPhaseRepository strategyPhaseRepository) {
        this.strategyPhaseRepository = strategyPhaseRepository;
    }

    @Autowired
    public void setRecordSqlRepository(RecordSqlRepository recordSqlRepository) {
        this.recordSqlRepository = recordSqlRepository;
    }

    @Autowired
    public void setStrategyRepository(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Autowired
    public void setRecordStrategyRepository(RecordStrategyRepository recordStrategyRepository) {
        this.recordStrategyRepository = recordStrategyRepository;
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Autowired
    public void setBoutiqueSevice(BoutiqueSevice boutiqueSevice) {
        this.boutiqueSevice = boutiqueSevice;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
