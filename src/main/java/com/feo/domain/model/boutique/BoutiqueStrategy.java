package com.feo.domain.model.boutique;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.feo.domain.model.user.User;
import com.feo.domain.event.EntityObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(value = "users")
@Entity
@Table(name = "hufu_boutique_strategy")
public class BoutiqueStrategy  extends EntityObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name="id",length=10)
    private Long id;

    @Column(name = "strategy_id")
    private Long strategyId;

    //精品用户所属用户
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "hufu_boutique_strategy_user",joinColumns ={ @JoinColumn(name = "strategy_id",referencedColumnName="strategy_id"), @JoinColumn(name = "join_date",referencedColumnName="update_date")},inverseJoinColumns = @JoinColumn(name = "uid"))
    private List<User> users =new ArrayList<>();

    //策略军团名称
    private String groupName;
    //    军团code
    private String groupCode;

    private Integer status;

    //策略名称
    private String name;

    private Date createDate;

    private Date joinDate;

    @Column(name = "update_date")
    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }
}
