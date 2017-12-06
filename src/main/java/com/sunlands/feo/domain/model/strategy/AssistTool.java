package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 库存跨期辅助咨询工具
 */
@Entity
@Table(name = "hufu_assist_tool")
public class AssistTool implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //    库存跨期辅助咨询工具
    @NotNull(message = "辅助咨询工具不能为空！")
    private String tool;

    // 序号
    @NotNull(message = "辅助咨询工具序号不能为空！")
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

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

}
