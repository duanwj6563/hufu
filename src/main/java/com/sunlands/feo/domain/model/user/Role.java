package com.sunlands.feo.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色
 */
@Entity
@Table(name = "hufu_role")
public class Role implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10)
    @JsonIgnore
    private Integer id;

    //角色
    @JsonIgnore
    private String rid;
    //角色名称
    private String name;
    //资源权限
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "hufu_role_resource", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
    Set<Resource> resources = new HashSet<Resource>();

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "hufu_user_role", joinColumns = @JoinColumn(name = "r_id"), inverseJoinColumns = @JoinColumn(name = "u_id"))
//    Set<User> users = new HashSet<User>();

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


    public Set<Resource> getResources() {
        return resources;
    }

    public void setResources(Set<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        this.resources.add(resource);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rid='" + rid + '\'' +
                ", name='" + name + '\'' +
                ", resources=" + resources +
                '}';
    }
}
