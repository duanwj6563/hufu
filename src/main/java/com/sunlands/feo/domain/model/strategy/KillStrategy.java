package com.sunlands.feo.domain.model.strategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "hufu_kill_strategy")
public class KillStrategy implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;
    //    截杀内容
    @NotNull(message = "截杀内容不能为空！")
    private String description;

    //    创建时间
    @Column(name = "create_date")
    private Date createDate;

    // 序号
    @NotNull(message = "优惠截杀序号不能为空！")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
