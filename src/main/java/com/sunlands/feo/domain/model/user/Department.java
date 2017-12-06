package com.sunlands.feo.domain.model.user;

import javax.persistence.*;
import java.io.Serializable;

/**
 *部门
 */
@Entity
@Table(name = "hufu_depart")
public class Department  implements Serializable {
    //    部门id
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name="id",length=10)
    private Integer id;
    //    部门名称
    private String name;


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

}
