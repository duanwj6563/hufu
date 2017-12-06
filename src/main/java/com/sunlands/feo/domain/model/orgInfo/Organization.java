package com.sunlands.feo.domain.model.orgInfo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "hufu_organization")
public class Organization implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String code;
    private String name;//机构名字

    /**
     * 机构级别
     */
    @Enumerated(value = EnumType.STRING)
    private OrganizationLevel organizationLevel;

    // 父级模块
    @ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.REMOVE }, optional=true)
    @JoinColumn(name="parent_id",referencedColumnName = "id")
    // optional关联字段是否可以为空
    @JsonBackReference
    private Organization parent;
    // 子级模块
    @JsonManagedReference
    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE },fetch = FetchType.LAZY, mappedBy="parent")
    private Set<Organization> item;
    /**
     * 是否包含质检数据:false - 未包含 true - 包含
     */
    @Transient
    private boolean hasQualityData;

    //@JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrganizationLevel getOrganizationLevel() {
        return organizationLevel;
    }

    public Organization setOrganizationLevel(OrganizationLevel organizationLevel) {
        this.organizationLevel = organizationLevel;
        return this;
    }

    public Organization getParent() {
        return parent;
    }

    public Organization setParent(Organization parent) {
        this.parent = parent;
        return this;
    }

    public Set<Organization> getItem() {
        return item;
    }

    public Organization setItem(Set<Organization> item) {
        this.item = item;
        return this;
    }

    public boolean isHasQualityData() {
        return hasQualityData;
    }

    public Organization setHasQualityData(boolean hasQualityData) {
        this.hasQualityData = hasQualityData;
        return this;
    }
}
