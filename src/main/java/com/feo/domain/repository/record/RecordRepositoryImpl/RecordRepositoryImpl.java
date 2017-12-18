package com.feo.domain.repository.record.RecordRepositoryImpl;

import com.feo.common.EntityManagerUtil;
import com.feo.common.StrUtil;
import com.feo.domain.repository.record.RecordSqlRepository;
import com.feo.port.rest.dto.record.SelectRecodCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 质检原生sql
 * Created by huang on 2017/11/5.
 */
@Repository
public class RecordRepositoryImpl implements RecordSqlRepository {

    //持久化工具
    private EntityManagerUtil entityManagerUtil;

    //通用分页查询方法
    private static int setIndeByPage(int page, int pageCount) {
        return (page - 1) * pageCount;
    }

    @Override
    public Integer selectRecordListCount(SelectRecodCondition selectRecodCondition, List<Integer> ids) {
        //获取筛选条件
        String status = selectRecodCondition.getStatus() + "";
        Integer workNum = selectRecodCondition.getWorkNum();
        String name = selectRecodCondition.getName();
        Integer startTimeLength = selectRecodCondition.getStartTimeLength();
        Integer endTimeLength = selectRecodCondition.getEndTimeLength();
        Integer sumScore = selectRecodCondition.getSumScore();
        Date date = selectRecodCondition.getDate();
        Integer recordType = selectRecodCondition.getRecordType();
        Integer recType = selectRecodCondition.getRecType();
        Integer selectStatus = selectRecodCondition.getSelectStatus();
        Integer isViolation = selectRecodCondition.getIsViolation();
        Integer state = selectRecodCondition.getState();
        //非空校验
        StringBuilder temp = new StringBuilder();
        if (StrUtil.isNotNull(isViolation)) {
            temp.append(" AND hr.is_violation= ").append(isViolation);
        }
        if (StrUtil.isNotNullAndNo0(startTimeLength)) {
            temp.append(" AND hr.time_length> ").append(startTimeLength);
        }
        if (StrUtil.isNotNullAndNo0(endTimeLength) && endTimeLength < 1800) {
            temp.append(" AND hr.time_length< ").append(endTimeLength);
        }
        if (StrUtil.isNotNull(recType)) {
            temp.append(" AND hr.rec_type = ").append(recType);
        }
        if (StrUtil.isNotNull(state)) {
            temp.append(" AND hc.state = ").append(state);
        }
        if (StrUtil.isNotNull(recordType)) {
            temp.append(" AND hr.record_type = ").append(recordType);
        }
        if (StrUtil.isNotNull(selectStatus)) {
            temp.append(" AND hr.select_status = ").append(selectStatus);
        }
        if (StrUtil.isNotNull(date)) {
            temp.append(" AND DATE_FORMAT(hr.test_date,'%Y-%m-%d')= '").append(date).append("'");
        }
        if (StrUtil.isNotNull(sumScore)) {
            temp.append(" AND hr.sum_score = ").append(sumScore);
        }
        if (StrUtil.isNotNull(workNum)) {
            temp.append(" AND hui.work_num LIKE concat('%','").append(workNum).append("','%') ");
        }
        if (StrUtil.isNotNull(name)) {
            temp.append(" AND hs.`name` LIKE concat('%','").append(name).append("','%') ");
        }
        if (StrUtil.isNotNull(ids)) {
            String replace = (ids + "").replace("[", "(").replace("]", ")");
            temp.append(" AND hui.org_id IN ").append(replace).append(" ");
        }
        if (Integer.parseInt(status) != 2) {
            temp.append(" AND hr.`status`= (0 OR hr.status=1) ");
        }
        //sql部分
        String sql = "SELECT count(hr.id) count \n" +
                "FROM hufu_record hr,hufu_chance hc,hufu_user_info hui,hufu_strategy hs\n" +
                "WHERE hr.chance_id=hc.id AND hui.id=hr.uid AND hr.strategy_id=hs.id";
        sql += temp.toString();
        //执行结果
        BigInteger count = (BigInteger) entityManagerUtil.getMap(sql).get("count");
        return count.intValue();
    }

    @Override
    public List<Map<String, Object>> selectRecordList(SelectRecodCondition selectRecodCondition, List<Integer> ids) {
        //获取筛选条件
        String status = selectRecodCondition.getStatus() + "";
        Integer workNum = selectRecodCondition.getWorkNum();
        String name = selectRecodCondition.getName();
        Integer startTimeLength = selectRecodCondition.getStartTimeLength();
        Integer endTimeLength = selectRecodCondition.getEndTimeLength();
        Integer sumScore = selectRecodCondition.getSumScore();
        Date date = selectRecodCondition.getDate();
        Integer recordType = selectRecodCondition.getRecordType();
        Integer recType = selectRecodCondition.getRecType();
        Integer selectStatus = selectRecodCondition.getSelectStatus();
        Integer isViolation = selectRecodCondition.getIsViolation();
        Integer state = selectRecodCondition.getState();
        //获取分页
        Integer page = selectRecodCondition.getPage();
        Integer size = selectRecodCondition.getSize();
        page = setIndeByPage(page, size);
        //非空校验
        StringBuilder temp = new StringBuilder();
        if (StrUtil.isNotNull(isViolation)) {
            temp.append(" AND hr.is_violation= ").append(isViolation);
        }
        if (StrUtil.isNotNullAndNo0(startTimeLength)) {
            temp.append(" AND hr.time_length> ").append(startTimeLength);
        }
        if (StrUtil.isNotNullAndNo0(endTimeLength) && endTimeLength < 1800) {
            temp.append(" AND hr.time_length< ").append(endTimeLength);
        }
        if (StrUtil.isNotNull(recType)) {
            temp.append(" AND hr.rec_type = ").append(recType);
        }
        if (StrUtil.isNotNull(state)) {
            temp.append(" AND hc.state = ").append(state);
        }
        if (StrUtil.isNotNull(recordType)) {
            temp.append(" AND hr.record_type = ").append(recordType);
        }
        if (StrUtil.isNotNull(selectStatus)) {
            temp.append(" AND hr.select_status = ").append(selectStatus);
        }
        if (StrUtil.isNotNull(date)) {
            temp.append(" AND DATE_FORMAT(hr.test_date,'%Y-%m-%d')= '").append(date).append("'");
        }
        if (StrUtil.isNotNull(sumScore)) {
            temp.append(" AND hr.sum_score = ").append(sumScore);
        }
        if (StrUtil.isNotNull(workNum)) {
            temp.append(" AND hui.work_num LIKE concat('%','").append(workNum).append("','%') ");
        }
        if (StrUtil.isNotNull(name)) {
            temp.append(" AND hs.`name` LIKE concat('%','").append(name).append("','%') ");
        }
        if (StrUtil.isNotNull(ids)) {
            String replace = (ids + "").replace("[", "(").replace("]", ")");
            temp.append(" AND hui.org_id IN ").append(replace).append(" ");
        }
        if (Integer.parseInt(status) != 2) {
            temp.append(" AND hr.`status`= (0 OR hr.status=1) ");
        }
        //sql部分
        String sql = "SELECT hui.org_id orgId,hr.id recordId,hui.`name` sellName,if(hr.select_status=0,'未占用','已占用') selectStatus,\n" +
                "hr.record_type recordType,hr.time_length timeLength,hr.rec_type recType,\n" +
                "if(hc.is_apply=0,\"否\",\"是\") isApply,(SELECT hu.username FROM hufu_user hu WHERE hu.id=hr.suid) examineName,\n" +
                "if(hr.`status`=0,'未保存','已保存') 'status',hr.sum_score sumScore,if(hr.is_violation=0,\"是\",\"否\") isViolation,hui.work_num workNum\n" +
                "FROM hufu_record hr,hufu_chance hc,hufu_user_info hui,hufu_strategy hs\n" +
                "WHERE hr.chance_id=hc.id AND hui.id=hr.uid AND hr.strategy_id=hs.id";
        sql += temp.toString() + "\nORDER BY hr.date DESC\n" + "LIMIT " + page + "," + size;
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Override
    public List<Map<String, Object>> selectRecordViolationInfo(Long recordId, Integer isViolation) {
        StringBuilder temp = new StringBuilder("AND hvi.is_violation=1 ");
        if (isViolation != 1) {
            temp = new StringBuilder("AND hvi.is_system=1 AND hvi.is_violation=0");
        }
        //sql部分
        String sql = "SELECT hvi.id,hvi.bad_time badTime,\n" +
                "CONCAT('一级分类: ',hvi.violation_type_one_name,'; 二级分类: ',hvi.violation_type_two_name,'; 三级分类: ',hvi.violation_type_three_name) detail\n" +
                "FROM hufu_record hr,hufu_violation_info hvi\n" +
                "WHERE hvi.record_id=hr.id ";
        sql += temp.toString() + " AND hr.id=" + recordId + "\nORDER BY hvi.bad_time";
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Override
    public List<Map<String, Object>> selectRecordDayListen(Integer userId, String startDate, String endDate) {
        //sql部分
        String sql = "SELECT count(hr.id) dayCount,DATE_FORMAT(test_date,'%Y-%m-%d') date\n" +
                "FROM hufu_record hr\n" +
                "WHERE hr.`status`=2 AND hr.suid=";
        sql += userId + "\nAND hr.test_date>'" + startDate + "'\nAND hr.test_date<'" + endDate + "'\nGROUP BY DATE_FORMAT(test_date,'%Y-%m-%d')";
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Override
    public Integer selectRecordWeekListen(Integer userId, String startDate, String endDate) {
        //sql部分
        String sql = "SELECT count(hr.id) count\n" +
                "FROM hufu_record hr\n" +
                "WHERE hr.`status`=2 AND hr.suid=";
        sql += userId + "\nAND hr.test_date<'" + endDate + "'\nAND hr.test_date>'" + startDate + "'";
        //执行结果
        Map<String, Object> map = entityManagerUtil.getMap(sql);
        BigInteger count = (BigInteger) map.get("count");
        return count.intValue();
    }

    @Override
    public List<Map<String, Object>> selectRecordListenDetail(Integer userId) {
        //sql部分
        String sql = "SELECT count(hr.id) weekCount,SUM(hr.time_length) sumTimeLength\n" +
                "FROM hufu_record hr,hufu_user hu\n" +
                "WHERE hr.suid = hu.id AND DateDiff(hr.test_date,NOW()) < 7 AND hr.`status`=2\n" +
                "AND hr.suid = ";
        sql += userId + "\nGROUP BY hr.suid";
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    //    0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具
    @Override
    public List<Map<String, Object>> selectRecordStrategyScore(Integer scoreType, Long recordId) {
        String sql = "";
        if (scoreType == 0 || scoreType == 2 || scoreType == 3 || scoreType == 4 || scoreType == 5) {
            String table = "";
            if (scoreType == 0 || scoreType == 2 || scoreType == 3) {
                table = "hufu_second_project";
            }
            if (scoreType == 4) {
                table = "hufu_class_size";
            }
            if (scoreType == 5) {
                table = "hufu_kill_strategy";
            }
            sql = "SELECT hrss.id,if(if(hrss.is_select=0,'0','1')=0,'0','1') isSelect,hsp.description name,hrss.score_type_id scoreTypeId,hsp.number\n" +
                    "FROM hufu_record hr,hufu_record_strategy hrs,hufu_record_strategy_score hrss," + table + " hsp\n" +
                    "WHERE hrss.record_strategy_id=hrs.id AND hrs.record_id=hr.id AND hrss.score_type_id=hsp.id\n" +
                    "AND hr.id=" + recordId + " AND hrs.score_type=" + scoreType + "\n" +
                    "ORDER BY hsp.number ASC";
        }
        if (scoreType == 1) {
            sql = "SELECT hrss.id,if(hrss.is_select=0,'0','1') isSelect,hn.need_name name,hrss.score_type_id scoreTypeId,hn.number\n" +
                    "FROM hufu_record hr,hufu_record_strategy hrs,hufu_record_strategy_score hrss,hufu_need hn\n" +
                    "WHERE hrss.record_strategy_id=hrs.id AND hrs.record_id=hr.id AND hn.id=hrss.score_type_id\n" +
                    "AND hr.id=" + recordId + " AND hrs.score_type=" + scoreType + "\n" +
                    "ORDER BY hn.number ASC";
        }
        if (scoreType == 6) {
            sql = "SELECT hrss.id,if(hrss.is_select=0,'0','1') isSelect,hks.solve name,hrss.score_type_id scoreTypeId,hks.number\n" +
                    "FROM hufu_record hr,hufu_record_strategy hrs,hufu_record_strategy_score hrss,hufu_solve_first_problem hks\n" +
                    "WHERE hrss.record_strategy_id=hrs.id AND hrs.record_id=hr.id AND hrss.score_type_id=hks.id\n" +
                    "AND hr.id=" + recordId + " AND hrs.score_type=" + scoreType + "\n" +
                    "ORDER BY hks.number ASC";
        }
        if (scoreType == 7) {
            sql = "SELECT hrss.id,if(hrss.is_select=0,'0','1') isSelect,hks.trigger_open name,hrss.score_type_id scoreTypeId,hks.number\n" +
                    "FROM hufu_record hr,hufu_record_strategy hrs,hufu_record_strategy_score hrss,hufu_trigger_open hks\n" +
                    "WHERE hrss.record_strategy_id=hrs.id AND hrs.record_id=hr.id AND hrss.score_type_id=hks.id\n" +
                    "AND hr.id=" + recordId + " AND hrs.score_type=" + scoreType + "\n" +
                    "ORDER BY hks.number ASC";
        }
        if (scoreType == 8) {
            sql = "SELECT hrss.id,if(hrss.is_select=0,'0','1') isSelect,hks.tool name,hrss.score_type_id scoreTypeId,hks.number\n" +
                    "FROM hufu_record hr,hufu_record_strategy hrs,hufu_record_strategy_score hrss,hufu_assist_tool hks\n" +
                    "WHERE hrss.record_strategy_id=hrs.id AND hrs.record_id=hr.id AND hrss.score_type_id=hks.id\n" +
                    "AND hr.id=" + recordId + " AND hrs.score_type=" + scoreType + "\n" +
                    "ORDER BY hks.number ASC";

        }
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Override
    public List<Map<String, Object>> selelctTaskList(Integer userId) {
        //sql
        String sql = "SELECT hr.id,hr.`status`\n" +
                "FROM hufu_record hr\n" +
                "WHERE hr.select_status=1\n" +
                "AND hr.`status` !=2\n" +
                "AND hr.suid=";
        sql += userId;
        //执行结果
        return entityManagerUtil.getList(sql);
    }

    @Autowired
    public void setEntityManagerUtil(EntityManagerUtil entityManagerUtil) {
        this.entityManagerUtil = entityManagerUtil;
    }
}
