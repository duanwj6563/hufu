package com.feo.domain.model.strategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 策略阶段
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class StrategyPhase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 10, nullable = false)
    private Long id;

    private String fileName;

    private String url;

    private Integer phase;

    //    主推班型
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<ClassSize> mainClassSizes = new HashSet<ClassSize>();

    //    截杀策略
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_phase_id")
    @OrderBy(value = "number ASC")
    private Set<KillStrategy> killStrategies = new HashSet<KillStrategy>();


    //    策略
    @Column(name = "strategy_id")
    private Long strategyId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "strategy_phase_id")
    @OrderBy(value = "number ASC ")
    private Set<Need> needs = new HashSet<>();

    public Set<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(Set<Need> needs) {
        this.needs = needs;
    }


    public Set<ClassSize> getMainClassSizes() {
        return mainClassSizes;
    }

    public void setMainClassSizes(Set<ClassSize> mainClassSizes) {
        this.mainClassSizes = mainClassSizes;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }


    public Long getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Long strategyId) {
        this.strategyId = strategyId;
    }

    public Set<KillStrategy> getKillStrategies() {
        return killStrategies;
    }

    public void setKillStrategies(Set<KillStrategy> killStrategies) {
        this.killStrategies = killStrategies;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
