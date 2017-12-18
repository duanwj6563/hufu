package com.feo.port.rest.dto.strategy;

import com.feo.domain.model.orgInfo.Organization;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryPageStrategy implements Serializable {

    private Long id;

    private Integer status;

    private Integer readStatus;

    private Date createDate;

    private String groupName;

    private String isAdd;

    private String firstProjectName;

    private String area;

    private String name;
//    是否是本人创建的策略 0、否 1、是
    private Integer own;

    public Integer getOwn() {
        return own;
    }

    public void setOwn(Integer own) {
        this.own = own;
    }

    private List<Organization> applydeparts=new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(String isAdd) {
        this.isAdd = isAdd;
    }

    public String getFirstProjectName() {
        return firstProjectName;
    }

    public void setFirstProjectName(String firstProjectName) {
        this.firstProjectName = firstProjectName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Organization> getApplydeparts() {
        return applydeparts;
    }

    public void setApplydeparts(List<Organization> applydeparts) {
        this.applydeparts = applydeparts;
    }

}
