package com.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 首咨探需：需求问题
 */
@Entity
@Table(name = "hufu_need")
public class Need implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //需求问题
    @NotNull(message = "探需问题不能为空！")
    @Column(name = "need_name")
    private String needName;

    // 序号
    @NotNull(message = "探需问题序号不能为空！")
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

    public String getNeedName() {
        return needName;
    }

    public void setNeedName(String needName) {
        this.needName = needName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
