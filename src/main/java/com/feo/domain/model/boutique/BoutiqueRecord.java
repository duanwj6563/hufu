package com.feo.domain.model.boutique;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feo.domain.model.user.User;
import com.feo.domain.event.EntityObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = {"users"})
@Entity
@Table(name = "hufu_boutique_record")
public class BoutiqueRecord  extends EntityObject implements Serializable {
    @Id
    @Column (name="id",length=10,nullable = false)
    private Long id;

    private Long recordId;

    //录音的URL
    private String url;

    //录音名称
    private String name;

    //策略军团名称
    private String groupName;

    //    军团code
    private String groupCode;

    // 咨询师姓名
    private String counselorName;

    // 咨询师id
    private Integer counselorId;

    //优秀类型 0，历程完整 1，推班类型明确 2，推专业灵活
    private Integer goodType;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "hufu_boutique_record_user",joinColumns = @JoinColumn(name = "record_id"),inverseJoinColumns = @JoinColumn(name = "uid"))
    private List<User> users =new ArrayList<>();

    private Date createDate;

    private Date joinDate;

    private Integer itemType;

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getCounselorName() {
        return counselorName;
    }

    public void setCounselorName(String counselorName) {
        this.counselorName = counselorName;
    }

    public Integer getCounselorId() {
        return counselorId;
    }

    public void setCounselorId(Integer counselorId) {
        this.counselorId = counselorId;
    }

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
