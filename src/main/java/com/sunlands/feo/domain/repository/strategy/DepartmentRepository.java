package com.sunlands.feo.domain.repository.strategy;

import com.sunlands.feo.domain.model.user.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {

}
