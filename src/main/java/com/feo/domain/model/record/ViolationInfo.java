package com.feo.domain.model.record;

import com.feo.domain.model.user.UserInfo;
import com.feo.port.rest.dto.record.AddViolationInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 违规信息
 */
@Entity
@Table(name = "hufu_violation_info")
public class ViolationInfo implements Serializable {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("是否追责  0、否 1、是")
    @Column(name = "is_duty")
    private Integer isDuty;

    @ApiModelProperty("是否违规  0、否 1、是")
    private Integer isViolation;

    @ApiModelProperty("是否系统判定违规  0，否  1，是")
    private Integer isSystem;

    @ApiModelProperty("违规开始时间")
    private Integer badTime;

    @ApiModelProperty("违规结束时间")
    private Integer endTime;

    @ApiModelProperty("质检判定违规时间")
    private Date testTime;

    @ApiModelProperty("是否扣除流水  0、否 1、是")
    private Integer isWater;

    @ApiModelProperty("罚款金额")
    private Integer Forfeit;

    @ApiModelProperty("是否可以回访申诉  0、否 1、是")
    private Integer isAppeal;

    @ApiModelProperty("申诉是否成功  0、否 1、是")
    private Integer isSuccess;

    @ApiModelProperty("申诉结果")
    private String result;

    @ApiModelProperty("补充说明")
    private String instruction;

    @ApiModelProperty("责任人")
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "uid")
    private UserInfo userInfo;

    @ApiModelProperty("违规类型一ID")
    private Long violationTypeOneId;

    @ApiModelProperty("违规类型一Name")
    private String violationTypeOneName;

    @ApiModelProperty("违规类型二ID")
    private Long violationTypeTwoId;

    @ApiModelProperty("违规类型二Name")
    private String violationTypeTwoName;

    @ApiModelProperty("违规类型三ID")
    private Long violationTypeThreeId;

    @ApiModelProperty("违规类型三Name")
    private String violationTypeThreeName;
    //    违规内容
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDuty() {
        return isDuty;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public void setIsDuty(Integer isDuty) {
        this.isDuty = isDuty;
    }

    public Integer getIsViolation() {
        return isViolation;
    }

    public void setIsViolation(Integer isViolation) {
        this.isViolation = isViolation;
    }

    public Integer getBadTime() {
        return badTime;
    }

    public void setBadTime(Integer badTime) {
        this.badTime = badTime;
    }

    public Integer getIsWater() {
        return isWater;
    }

    public void setIsWater(Integer isWater) {
        this.isWater = isWater;
    }

    public Integer getForfeit() {
        return Forfeit;
    }

    public void setForfeit(Integer forfeit) {
        Forfeit = forfeit;
    }

    public Integer getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Integer isAppeal) {
        this.isAppeal = isAppeal;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Integer isSystem) {
        this.isSystem = isSystem;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Long getViolationTypeOneId() {
        return violationTypeOneId;
    }

    public void setViolationTypeOneId(Long violationTypeOneId) {
        this.violationTypeOneId = violationTypeOneId;
    }

    public String getViolationTypeOneName() {
        return violationTypeOneName;
    }

    public void setViolationTypeOneName(String violationTypeOneName) {
        this.violationTypeOneName = violationTypeOneName;
    }

    public Long getViolationTypeTwoId() {
        return violationTypeTwoId;
    }

    public void setViolationTypeTwoId(Long violationTypeTwoId) {
        this.violationTypeTwoId = violationTypeTwoId;
    }

    public String getViolationTypeTwoName() {
        return violationTypeTwoName;
    }

    public void setViolationTypeTwoName(String violationTypeTwoName) {
        this.violationTypeTwoName = violationTypeTwoName;
    }

    public Long getViolationTypeThreeId() {
        return violationTypeThreeId;
    }

    public void setViolationTypeThreeId(Long violationTypeThreeId) {
        this.violationTypeThreeId = violationTypeThreeId;
    }

    public String getViolationTypeThreeName() {
        return violationTypeThreeName;
    }

    public void setViolationTypeThreeName(String violationTypeThreeName) {
        this.violationTypeThreeName = violationTypeThreeName;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    //查询违规详情
    public Map<String, Object> selectViolationInfoDatails() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("isDuty", isDuty);
        map.put("badTime", badTime);
        map.put("isWater", isWater);
        map.put("forfeit", Forfeit);
        map.put("isAppeal", isAppeal);
        map.put("violationTypeOneId", violationTypeOneId);
        map.put("violationTypeOneName", violationTypeOneName);
        map.put("violationTypeTwoId", violationTypeTwoId);
        map.put("violationTypeTwoName", violationTypeTwoName);
        map.put("violationTypeThreeId", violationTypeThreeId);
        map.put("violationTypeThreeName", violationTypeThreeName);
        return map;
    }

    //更新违规操作提交
    public ViolationInfo changeViolationInfo(AddViolationInfo newer, ViolationInfo older) {

        older.setIsDuty(newer.getIsDuty());
        older.setBadTime(newer.getBadTime());
        older.setIsWater(newer.getIsWater());
        older.setForfeit(newer.getForfeit());
        older.setIsAppeal(newer.getIsAppeal());
        older.setIsViolation(1);
        older.setTestTime(new Date());
        older.setViolationTypeOneId(newer.getViolationTypeOneId());
        older.setViolationTypeOneName(newer.getViolationTypeOneName());
        older.setViolationTypeTwoId(newer.getViolationTypeTwoId());
        older.setViolationTypeTwoName(newer.getViolationTypeTwoName());
        older.setViolationTypeThreeId(newer.getViolationTypeThreeId());
        older.setViolationTypeThreeName(newer.getViolationTypeThreeName());

        return older;
    }
}
