package com.feo.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feo.domain.model.orgInfo.Organization;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户静态信息
 */
@Entity
@Table(name = "hufu_user_info")
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue()
    @JsonIgnore
    private Integer id;
    //  真实用户名
    private String name;
    //  登录用户名
    @NotBlank(message = "用户名不能为空")
    private String username;

    private String email;
    //员工工号
    private Integer workNum;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id", referencedColumnName = "id")
    private Organization organization;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWorkNum() {
        return workNum;
    }

    public void setWorkNum(Integer workNum) {
        this.workNum = workNum;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
}
