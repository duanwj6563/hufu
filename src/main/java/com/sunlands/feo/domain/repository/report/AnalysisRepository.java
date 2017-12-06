package com.sunlands.feo.domain.repository.report;

import com.sunlands.feo.domain.model.reportform.Analyze;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AnalysisRepository extends JpaRepository<Analyze, Long>, JpaSpecificationExecutor<Analyze> {

    @Query("select a.reportName,a.createDate,a.strategyId,a.id from Analyze a order by a.createDate desc ")
    Page<Analyze> findAllByTypeOrderByCreateDateDesc(Pageable pageable);

    //Page<Analyze> findAll(Specification<Analyze> spec, Pageable pageable);

    Analyze findById(Long id);

    Analyze findByStrategyId(Long strategyId);
}
