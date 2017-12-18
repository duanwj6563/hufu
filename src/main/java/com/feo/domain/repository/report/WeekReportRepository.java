package com.feo.domain.repository.report;

import com.feo.domain.model.reportform.WeekReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface WeekReportRepository extends JpaRepository<WeekReport, Long>, JpaSpecificationExecutor<WeekReport> {
}
