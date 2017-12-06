package com.sunlands.feo.domain.repository.record;

import com.sunlands.feo.port.rest.dto.record.SelectRecodCondition;

import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2017/11/5.
 */
public interface RecordSqlRepository {

    /**
     * 录音列表总数
     *
     * @param map
     * @param ids
     * @return
     */
    Integer selectRecordListCount(SelectRecodCondition map, List<Integer> ids);

    /**
     * 录音列表
     *
     * @param map
     * @param ids
     * @return
     */
    List<Map<String, Object>> selectRecordList(SelectRecodCondition map, List<Integer> ids);

    /**
     * 录音详情(录音违规信息)
     *
     * @param recordId
     * @return
     */
    List<Map<String, Object>> selectRecordViolationInfo(Long recordId, Integer isViolation);

    /**
     * (工作台)录音日听
     *
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String, Object>> selectRecordDayListen(Integer userId, String startDate, String endDate);

    /**
     * (工作台)录音周听
     *
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    Integer selectRecordWeekListen(Integer userId, String startDate, String endDate);

    /**
     * (工作台)录音详细数据
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> selectRecordListenDetail(Integer userId);

    /**
     * 查询录音问题打分列表
     *
     * @param scoreType
     * @param recordId
     * @return
     */
    List<Map<String, Object>> selectRecordStrategyScore(Integer scoreType, Long recordId);

    /**
     * 查询任务列表
     *
     * @param userId
     * @return
     */
    List<Map<String, Object>> selelctTaskList(Integer userId);

}
