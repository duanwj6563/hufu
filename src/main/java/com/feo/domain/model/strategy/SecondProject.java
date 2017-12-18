package com.feo.domain.model.strategy;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 二级项目
 */
@Entity
@Table(name = "hufu_second_project")
public class SecondProject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //    项目名称
//    private String name;
//    项目类型：主推专业、主推院校、主推项目
    private String type;

    //  一级项目名称
    @Column(name = "first_project_name")
    private String firstProjectName;

    //    项目描述
    @NotNull(message = "主推专业/院校/项目不能为空！")
    private String description;

    // 序号
    @NotNull(message = "主推专业/院校/项目序号不能为空！")
    private Integer number;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstProjectName() {
        return firstProjectName;
    }

    public void setFirstProjectName(String firstProjectName) {
        this.firstProjectName = firstProjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
