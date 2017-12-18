package com.feo.domain.repository.report;

import com.feo.domain.model.reportform.AnalysisProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisPropertiesRepository extends JpaRepository<AnalysisProperties, Long> {
    AnalysisProperties findByStrategyId(Long stategyId);
}
