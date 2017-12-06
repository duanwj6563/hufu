package com.sunlands.feo.domain.repository.report;

import com.sunlands.feo.domain.model.reportform.QualityWeek;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface QualityWeekRepository extends JpaRepository<QualityWeek, Long>, JpaSpecificationExecutor<QualityWeek> {
    List<QualityWeek> findAllById(Long id);

    Page<QualityWeek> findAllByOrderByCreateDateDesc(Pageable pageable);

}
