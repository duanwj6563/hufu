package com.feo.domain.repository.record;

import com.feo.domain.model.record.ViolationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ViolationTypeRepository extends JpaRepository<ViolationType,Long> {

    List<ViolationType> findAllByParentId(Long id);
}
