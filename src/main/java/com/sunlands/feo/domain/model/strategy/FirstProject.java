package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 一级项目
 */
@Entity
@Table(name = "hufu_first_project")
public class FirstProject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //    项目名称
    private String name;
    //    二级项目名称
    private String secName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }
}
