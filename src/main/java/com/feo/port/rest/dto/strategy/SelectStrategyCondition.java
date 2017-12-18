package com.feo.port.rest.dto.strategy;

import com.feo.domain.model.orgInfo.Organization;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class SelectStrategyCondition {

    private Integer area;

    private Integer status;

    private Integer auditStatus;

    private Integer firstProjectId;

    private Integer own;

    @NotNull(message = "页面大小不能为空")
    private Integer pageSize;

    @NotNull(message = "页码不能为空")
    private Integer pageNum;
    private List<Organization> organizations=new ArrayList<>();

    private Integer[] orgs;

    public Integer[] getOrgs() {
        return orgs;
    }

    public void setOrgs(Integer[] orgs) {
        this.orgs = orgs;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getFirstProjectId() {
        return firstProjectId;
    }

    public void setFirstProjectId(Integer firstProjectId) {
        this.firstProjectId = firstProjectId;
    }

    public Integer getOwn() {
        return own;
    }

    public void setOwn(Integer own) {
        this.own = own;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }
}
