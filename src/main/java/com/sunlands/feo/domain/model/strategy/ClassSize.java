package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 班型
 */

@Entity
@Table(name = "hufu_class_size")
public class ClassSize implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //    班型名称
    private String name;
    //    班型描述
    @NotNull(message = "主推班型不能为空")
    private String description;
    //    班型结束时间
    @Column(name = "over_date")
    private Date overDate;

    // 序号
    @NotNull(message = "主推班型序号不能为空！")
    private Integer number;

    @Column(name = "strategy_phase_id")
    private Long strategyPhaseId;

    public Long getStrategyPhaseId() {
        return strategyPhaseId;
    }

    public void setStrategyPhaseId(Long strategyPhaseId) {
        this.strategyPhaseId = strategyPhaseId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getOverDate() {
        return overDate;
    }

    public void setOverDate(Date overDate) {
        this.overDate = overDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
