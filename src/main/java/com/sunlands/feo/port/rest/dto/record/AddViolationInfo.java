package com.sunlands.feo.port.rest.dto.record;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 创建违规信息
 * Created by huang on 2017/11/21.
 */
public class AddViolationInfo {

    @ApiModelProperty("主键id")
    private Long id;

    @NotNull(message = "是否追责不能为空")
    @ApiModelProperty("是否追责  0、否 1、是")
    private Integer isDuty;

    @NotNull(message = "违规时间不能为空")
    @ApiModelProperty("违规时间")
    private Integer badTime;

    @NotNull(message = "是否扣除流水不能为空")
    @ApiModelProperty("是否扣除流水  0、否 1、是")
    private Integer isWater;

    @NotNull(message = "罚款金额不能为空")
    @ApiModelProperty("罚款金额")
    private Integer forfeit;

    @NotNull(message = "是否可以回访申诉不能为空")
    @ApiModelProperty("是否可以回访申诉  0、否 1、是")
    private Integer isAppeal;

    @NotNull(message = "违规类型一ID不能为空")
    @ApiModelProperty("违规类型一ID")
    private Long violationTypeOneId;

    @NotNull(message = "违规类型一Name不能为空")
    @ApiModelProperty("违规类型一Name")
    private String violationTypeOneName;

    @NotNull(message = "违规类型二ID不能为空")
    @ApiModelProperty("违规类型二ID")
    private Long violationTypeTwoId;

    @NotNull(message = "违规类型二Name不能为空")
    @ApiModelProperty("违规类型二Name")
    private String violationTypeTwoName;

    @NotNull(message = "违规类型三ID不能为空")
    @ApiModelProperty("违规类型三ID")
    private Long violationTypeThreeId;

    @NotNull(message = "违规类型三Name不能为空")
    @ApiModelProperty("违规类型三Name")
    private String violationTypeThreeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIsDuty() {
        return isDuty;
    }

    public void setIsDuty(Integer isDuty) {
        this.isDuty = isDuty;
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
        return forfeit;
    }

    public void setForfeit(Integer forfeit) {
        this.forfeit = forfeit;
    }

    public Integer getIsAppeal() {
        return isAppeal;
    }

    public void setIsAppeal(Integer isAppeal) {
        this.isAppeal = isAppeal;
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
}
