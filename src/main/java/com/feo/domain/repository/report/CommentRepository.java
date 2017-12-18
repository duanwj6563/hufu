package com.feo.domain.repository.report;

import com.feo.domain.model.reportform.Commet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Commet, Long> {
    Page<Commet> findAllByAnalyzeIdAndUidOrderByCrtDateDesc(Long id, String uid, Pageable pageable);
}
