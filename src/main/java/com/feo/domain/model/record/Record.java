package com.feo.domain.model.record;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feo.domain.model.enums.record.AimEnums;
import com.feo.domain.model.enums.record.GoodTypeEnums;
import com.feo.domain.model.enums.record.RecTypeEnums;
import com.feo.domain.model.user.User;
import com.feo.domain.model.user.UserInfo;
import com.feo.common.StrUtil;
import com.feo.domain.model.enums.chance.ChanceStateEnums;
import com.feo.domain.model.enums.record.RecordTypeEnums;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 录音实体
 */
@JsonIgnoreProperties(value = {"checkRecord", "chance", "suser", "userInfo"})
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "hufu_record")
public class Record implements Serializable {

    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", length = 11, nullable = false)
    private Long id;

    @ApiModelProperty("录音地址")
    private String url;

    @ApiModelProperty("录音类型 0、常规 1、违规 2、后端发起 3、语音系统")
    private Integer recordType;

    @ApiModelProperty("录音性质：1、首咨 2、回访 3、跨期")
    private Integer recType;

    @ApiModelProperty("0、未保存未提交 1，保存未提交 2，保存并提交")
    private Integer status;

    @ApiModelProperty("咨询项目：1、自考；2、教资。。。")
    private Integer itemType;

    @ApiModelProperty("录音日期")
    private Date date;

    @ApiModelProperty("录音时长")
    private Integer timeLength;

    @ApiModelProperty("质检时间")
    private Date testDate;

    @ApiModelProperty("是否优秀录音 0,不优秀 1，优秀")
    private Integer isGood;

    @ApiModelProperty("优秀类型 0，历程完整 1，推班类型明确 2，推专业灵活")
    private Integer goodType;

    @ApiModelProperty("执行总得分")
    private Integer sumScore;

    @ApiModelProperty("流程完成度 4，6，8，10")
    private Integer finishNum;

    @ApiModelProperty("录音评价（本人）")
    private String comment;

    @ApiModelProperty("录音状态 0，未选中  1，占用中")
    @Column(name = "select_status")
    private Integer selectStatus;

    @ApiModelProperty("策略表id")
    private Long strategyId;

    @ApiModelProperty("是否违规")
    private Integer isViolation;

    @ApiModelProperty("咨询师名称")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uid")
    private UserInfo userInfo;

    @ApiModelProperty("质检员")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "suid")
    private User suser;

    @ApiModelProperty("机会信息")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chance_id")
    private Chance chance;

    @ApiModelProperty("违规信息")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
    private Set<ViolationInfo> violationInfos = new HashSet<>();

    @ApiModelProperty("录音策略打分")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
    private Set<RecordStrategy> recordStrategys = new HashSet<>();

    @ApiModelProperty("问题信息")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
    private Set<Question> questions = new HashSet<>();

    //返回录音基本信息
    public Map<String, Object> selectRecordCheckInfo() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("recType", getReturnParam(recType));
        map.put("recTypeName", StrUtil.isNotNullAndNo0(recType) ? RecTypeEnums.values()[recType - 1].getName() : "");
        map.put("recordType", getReturnParam(recordType));
        map.put("recordTypeName", StrUtil.isNotNull(recordType) ? RecordTypeEnums.values()[recordType].getName() : "");
        map.put("state", getReturnParam(chance.getState()));
        map.put("stateName", StrUtil.isNotNull(chance.getState()) ? ChanceStateEnums.values()[chance.getState()].getName() : "");
        map.put("aim", getReturnParam(chance.getAim()));
        map.put("aimName", StrUtil.isNotNull(chance.getAim()) ? AimEnums.values()[chance.getAim()].getName() : "");
        map.put("finishNum", getReturnParam(finishNum));
        map.put("isGood", getReturnParam(isGood) + "");
        map.put("comment", getReturnParam(comment));
        map.put("totalScore", StrUtil.isNotNull(sumScore) ? sumScore : "0");
        map.put("isViolation", getReturnParam(isViolation));
        map.put("status", getReturnParam(status));
        map.put("strategyId", getReturnParam(strategyId));
        if (isGood == 1) {
            map.put("goodType", getReturnParam(goodType) + "");
        }
        if (StrUtil.isNotNull(goodType)) {
            map.put("goodTypeName", getReturnParam(GoodTypeEnums.values()[goodType].getName()));
        }
        //判断质检问题是否存在
        if (StrUtil.isNull(questions)) {
            List<Question> list = new ArrayList<>();
            for (Integer i = 0; i < 3; i++) {
                Question question = new Question();
                question.setType(i);
                list.add(question);
            }
            map.put("questions", list);
        } else {
            //质检问题类型排序返回
            List<Question> list = new ArrayList<>();
            Question questio = new Question();
            for (Integer i = 0; i < 3; i++) {
                list.add(questio);
            }
            for (Question question : questions) {
                list.add(question.getType(), question);
                list.remove(question.getType().intValue() + 1);
            }
            map.put("questions", list);
        }
        return map;
    }

    //返回学生和订单信息
    public Map<String, Object> selectRecordStudentAndOrder() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("stuName", getReturnParam(getChance().getStuName()));
        map.put("phoneNum", getReturnParam(getChance().getPhoneNum()));
        map.put("majorTypeOneName", getReturnParam(getChance().getMajorTypeOneName()));
        map.put("majorTypeTwoName", getReturnParam(getChance().getMajorTypeTwoName()));
        map.put("signDate", getReturnParam(getChance().getSignDate()));
        map.put("fee", getReturnParam(getChance().getFee()));
        map.put("testDate", getReturnParam(testDate));
        map.put("username", getReturnParam(getSuser().getUsername()));
        map.put("isApply", getChance().getIsApply() == 0 ? "否" : "是");
        map.put("timeLength", getReturnParam(timeLength));
        map.put("orderSonId", getReturnParam(getChance().getOrderSonId()));
        return map;
    }

    private Object getReturnParam(Object object) {
        return StrUtil.isNotNull(object) ? object : "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public Integer getRecType() {
        return recType;
    }

    public void setRecType(Integer recType) {
        this.recType = recType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public Integer getIsGood() {
        return isGood;
    }

    public void setIsGood(Integer isGood) {
        this.isGood = isGood;
    }

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public Integer getSumScore() {
        return sumScore;
    }

    public void setSumScore(Integer sumScore) {
        this.sumScore = sumScore;
    }

    public Integer getFinishNum() {
        return finishNum;
    }

    public void setFinishNum(Integer finishNum) {
        this.finishNum = finishNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(Integer selectStatus) {
        this.selectStatus = selectStatus;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Integer getIsViolation() {
        return isViolation;
    }

    public void setIsViolation(Integer isViolation) {
        this.isViolation = isViolation;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public User getSuser() {
        return suser;
    }

    public void setSuser(User suser) {
        this.suser = suser;
    }

    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    public Set<ViolationInfo> getViolationInfos() {
        return violationInfos;
    }

    public void setViolationInfos(Set<ViolationInfo> violationInfos) {
        this.violationInfos = violationInfos;
    }

    public Set<RecordStrategy> getRecordStrategys() {
        return recordStrategys;
    }

    public void setRecordStrategys(Set<RecordStrategy> recordStrategys) {
        this.recordStrategys = recordStrategys;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
