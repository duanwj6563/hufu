package com.sunlands.feo.domain.repository.report;

import com.sunlands.feo.domain.model.reportform.Commet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Commet, Long> {
    Page<Commet> findAllByAnalyzeIdAndUidOrderByCrtDateDesc(Long id, String uid, Pageable pageable);
}
