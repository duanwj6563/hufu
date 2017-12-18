package com.feo.domain.model.reportform;

import com.feo.domain.model.record.Record;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hufu_synthetical_quality")
public class SyntheticalQuality {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column (name="id",length=11,nullable = false)
    private Long id;

    private Integer mouth;

//    private Set<QualityTest> qualityTests=new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "synthetical_quality_id")
    private Set<Record> records=new HashSet<Record>();

}
