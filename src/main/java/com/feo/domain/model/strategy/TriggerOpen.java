package com.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 库存跨期触发式开场
 */
@Entity
@Table(name = "hufu_trigger_open")
public class TriggerOpen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //    库存跨期触发式开场
    @NotNull(message = "库存跨期触发式开场不能为空！")
    private String triggerOpen;

    // 序号
    @NotNull(message = "库存跨期触发式开场序号不能为空！")
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

    public String getTriggerOpen() {
        return triggerOpen;
    }

    public void setTriggerOpen(String triggerOpen) {
        this.triggerOpen = triggerOpen;
    }

}
