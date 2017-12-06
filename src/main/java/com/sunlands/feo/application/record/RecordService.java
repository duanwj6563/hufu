package com.sunlands.feo.application.record;

import com.sunlands.feo.domain.model.record.Record;
import com.sunlands.feo.port.rest.dto.record.AddViolationInfo;
import com.sunlands.feo.port.rest.dto.record.SelectRecodCondition;

import java.util.List;
import java.util.Map;

/**
 * 质检Service
 * Created by huang on 2017/11/2.
 */
public interface RecordService {
    /**
     * 质检列表
     *
     * @param selectRecodCondition 质检筛选条件
     * @return 质检分页数据
     */
    Map<String, Object> selectRecordList(SelectRecodCondition selectRecodCondition);

    /**
     * 质检详情一(质检基本信息)
     *
     * @param recordId 质检id
     * @return 质检基本信息
     */
    Map<String, Object> selectRecordDetailOne(Long recordId);

    /**
     * 质检详情二（质检违规信息）
     *
     * @param recordId 质检id
     * @return 质检违规信息
     */
    Map<String, Object> selectRecordDetailTwo(Long recordId);

    /**
     * 质检详情三（质检策略信息）
     *
     * @param recordId 质检id
     * @param recType  质检类型（首咨，七天，库存）
     * @return 质检策略信息
     */
    Map<String, Object> selectRecordDetailThree(Long recordId, Integer recType);

    /**
     * 质检违规查询
     *
     * @param recordId        质检id
     * @param violationInfoId 质检违规id
     * @return 质检违规详情
     */
    Map<String, Object> selectRecordViolation(Long recordId, Long violationInfoId);

    /**
     * 质检违规提交
     *
     * @param recordId         质检id
     * @param addViolationInfo 违规信息
     * @return 是否提交成功
     */
    boolean addRecordViolation(Long recordId, AddViolationInfo addViolationInfo);

    /**
     * 质检任务创建
     *
     * @param callIds 质检ids
     * @return 质检任务创建成功ids
     */
    List<Long> addRecordShoppingCart(Long[] callIds);

    /**
     * 质检任务列表
     *
     * @return 质检任务列表
     */
    List<Map<String, Object>> selectRecordShoppingCartList();

    /**
     * 质检质检打分保存
     *
     * @param record 质检实体类
     * @return 质检是否保存成功
     */
    boolean addRecordScoreToSave(Record record);

    /**
     * 质检打分提交
     *
     * @param callIds 质检ids
     * @return 质检打分提交是否成功
     */
    boolean addRecordScoreToSubmit(Long[] callIds);

    /**
     * 质检清空打包
     *
     * @return 质检清空打包
     */
    String deleteRecordShoppingCart();


    /**
     * (工作台)质检日听
     *
     * @return 质检日听详情
     */
    Map<String, Object> selectRecordDayListen();

    /**
     * (工作台)质检周听
     *
     * @return 质检周听详情
     */
    Map<String, Object> selectRecordWeekDetail();

    /**
     * (工作台)质检听数据
     *
     * @return 质检听数据详情
     */
    List<Map<String, Object>> selectRecordListenDetail();

    /**
     * 质检违规移除
     *
     * @param callId          质检ids
     * @param violationInfoId 质检违规id
     * @return 质检违规移除是否成功
     */
    boolean deleteRecordViolation(Long callId, Long violationInfoId);

}
