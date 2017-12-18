package com.feo.domain.model.record;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 机会信息
 */
@Entity
@Table(name = "hufu_chance")
public class Chance implements Serializable {
    @Id
    @ApiModelProperty("主键id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 11)
    private Long id;

    @ApiModelProperty("学生姓名")
    private String stuName;

    @ApiModelProperty("学生电话")
    private String phoneNum;

    @ApiModelProperty("性别 0、男 1、女")
    private Integer sex;

    @ApiModelProperty("取证目的 0、个人提升 1、评职称 2、找工作...")
    private Integer aim;

    @ApiModelProperty("学员情况 0、企业普通员工 1、政府事业单位 3、...")
    private Integer state;

    @ApiModelProperty("是否报名")
    private Integer isApply;

    @ApiModelProperty("报名时间")
    private Date signDate;

    @ApiModelProperty("子订单id")
    private String orderSonId;

    @ApiModelProperty("系统订单编号")
    private String orderParentId;

    @ApiModelProperty("专业类型一Name")
    private String majorTypeOneName;

    @ApiModelProperty("专业类型二Name")
    private String majorTypeTwoName;

    @ApiModelProperty("缴费金额")
    private String fee;

    @ApiModelProperty("录音")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "chance_id")
    private Set<Record> records = new HashSet<Record>();

    @ApiModelProperty("未匹配录音")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "chance_id")
    private Set<UnMatchRecord> unMatchRecords = new HashSet<UnMatchRecord>();

    public Set<UnMatchRecord> getUnMatchRecords() {
        return unMatchRecords;
    }

    public void setUnMatchRecords(Set<UnMatchRecord> unMatchRecords) {
        this.unMatchRecords = unMatchRecords;
    }

    public Set<Record> getRecords() {
        return records;
    }

    public void setRecords(Set<Record> records) {
        this.records = records;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getAim() {
        return aim;
    }

    public void setAim(Integer aim) {
        this.aim = aim;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getMajorTypeOneName() {
        return majorTypeOneName;
    }

    public void setMajorTypeOneName(String majorTypeOneName) {
        this.majorTypeOneName = majorTypeOneName;
    }

    public String getMajorTypeTwoName() {
        return majorTypeTwoName;
    }

    public void setMajorTypeTwoName(String majorTypeTwoName) {
        this.majorTypeTwoName = majorTypeTwoName;
    }

    public Integer getIsApply() {
        return isApply;
    }

    public void setIsApply(Integer isApply) {
        this.isApply = isApply;
    }

    public String getOrderSonId() {
        return orderSonId;
    }

    public void setOrderSonId(String orderSonId) {
        this.orderSonId = orderSonId;
    }

    public String getOrderParentId() {
        return orderParentId;
    }

    public void setOrderParentId(String orderParentId) {
        this.orderParentId = orderParentId;
    }
}
