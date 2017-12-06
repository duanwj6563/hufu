package com.sunlands.feo.domain.repository.report;

import com.sunlands.feo.domain.model.reportform.WeekReport;
import com.sunlands.feo.domain.model.reportform.WeekWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeekReportRepository extends JpaRepository<WeekReport, Long>, JpaSpecificationExecutor<WeekReport> {
}
