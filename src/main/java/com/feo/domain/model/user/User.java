package com.feo.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.common.MD5Util;
import com.feo.domain.event.EntityObject;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 */
@Entity
@Table(name = "hufu_user")
public class User extends EntityObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    @JsonIgnore
    private Integer id;
    //  真实用户名
    private String name;
    //    登录用户名
    @NotBlank(message = "用户名不能为空")
    private String username;
    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    private String password;

    private String email;
    //    用户角色
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "hufu_user_role", joinColumns = @JoinColumn(name = "u_id"), inverseJoinColumns = @JoinColumn(name = "r_id"))
    Set<Role> roles = new HashSet<Role>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "org_id", referencedColumnName = "id")
    private Organization organization;

    //接收人
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hufu_user_mng_organization", joinColumns = @JoinColumn(name = "mng_user_id"), inverseJoinColumns = @JoinColumn(name = "organization_id"))
    private Set<Organization> mngOrganizations = new HashSet<Organization>();

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = MD5Util.encode(password);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Set<Organization> getMngOrganizations() {
        return mngOrganizations;
    }

    public void setMngOrganizations(Set<Organization> mngOrganizations) {
        this.mngOrganizations = mngOrganizations;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}
