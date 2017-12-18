package com.feo.domain.model.strategy;

import javax.persistence.*;

@Entity
@Table(name = "hufu_content_detail")
public class ContentDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    //    追加类型
    private Integer type;
    //    追加类型名
    private String typeName;
    //    追加描述
    private String content;

    //策略阶段id
    @Column(name = "strategy_phase_id")
    private Long strategyPhaseId;

    public Long getStrategyPhaseId() {
        return strategyPhaseId;
    }

    public void setStrategyPhaseId(Long strategyPhaseId) {
        this.strategyPhaseId = strategyPhaseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
