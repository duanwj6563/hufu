package com.feo.domain.repository.report;

import com.feo.domain.model.reportform.WeekWork;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeekWorkRepository extends JpaRepository<WeekWork, Long>, JpaSpecificationExecutor<WeekWork> {
    //List<WeekWork> findAllByIdOrderByRank(String id);
    List<WeekWork> findByWeekIdOrderByRankAsc(Long id);

    @Query("select a.reportName from WeekWork a order by a.createDate desc ")
    Page<WeekWork> findAllByOrderByCreateDateDesc(Pageable pageable);
}
