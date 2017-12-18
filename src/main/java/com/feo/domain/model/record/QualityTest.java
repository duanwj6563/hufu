package com.feo.domain.model.record;

import com.feo.domain.model.strategy.Strategy;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = "hufu_quality_test")
public class QualityTest {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE)
    @Column (name="id",length=11,nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "strategy_id")
    private Strategy strategy;

    @OneToMany
    @JoinColumn(name = "quality_test_id")
    private Set<Record> records=new HashSet<>();


}
