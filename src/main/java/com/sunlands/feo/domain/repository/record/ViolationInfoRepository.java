package com.sunlands.feo.domain.repository.record;

import com.sunlands.feo.domain.model.record.ViolationInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViolationInfoRepository extends JpaRepository<ViolationInfo, Long> {
}
