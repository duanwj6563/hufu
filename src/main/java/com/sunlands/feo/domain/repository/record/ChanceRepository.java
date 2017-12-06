package com.sunlands.feo.domain.repository.record;

import com.sunlands.feo.domain.model.record.Chance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChanceRepository extends JpaRepository<Chance, Long> {

    Chance findByPhoneNum(String phoneNum);
}
