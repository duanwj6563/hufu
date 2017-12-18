package com.feo.domain.repository.record;

import com.feo.domain.model.record.ViolationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationInfoRepository extends JpaRepository<ViolationInfo, Long> {
}
