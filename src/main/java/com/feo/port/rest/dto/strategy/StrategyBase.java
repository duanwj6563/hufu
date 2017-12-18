package com.feo.port.rest.dto.strategy;

import com.feo.domain.model.orgInfo.Organization;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StrategyBase {
    private Long id;

    private Integer uid;

    /**
     * 策略审核状态 0-新建,1-待审核,2-审核通过,3-驳回,
     */
    private Integer status;

    //    创建时间
    private Date createDate;

    //    提交审核时间
    private Date submitDate;

    //    策略名称
    @NotNull(message = "策略名称不能为空")
    private String name;

    //    适用军团名称
    private String groupName;

    //    项目：1、自考；2、教资；3、其他
    @NotNull(message = "项目不能为空")
    private Integer firstProjectId;

    //    适用部门
    // @NotNull(message = "部门列表不能为空")
    private List<Organization> applydeparts = new ArrayList<>();

//    private int[] orgs;

    //    适用地域：1、一线城市（北上广深武）2、全国APP 3、全国SEM 4、二三线城市（外地）
    @NotNull(message = "适用地域不能为空")
    private Integer area;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getFirstProjectId() {
        return firstProjectId;
    }

    public void setFirstProjectId(Integer firstProjectId) {
        this.firstProjectId = firstProjectId;
    }

//    public List<Organization> getApplydeparts() {
//        List<Organization> organizations=new ArrayList<>();
//        if (getOrgs()!=null){
//            for (int orgid:getOrgs()){
//                Organization organization=new Organization();
//                organization.setId(orgid);
//                organizations.add(organization);
//            }
//        }
//        setApplydeparts(organizations);
//        return organizations;
//    }

    public List<Organization> getApplydeparts() {
        return applydeparts;
    }

    public void setApplydeparts(List<Organization> applydeparts) {
        this.applydeparts = applydeparts;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

//    public int[] getOrgs() {
//        return orgs;
//    }
//    public void setOrgs(int[] orgs) {
//        this.orgs = orgs;
//    }
}
