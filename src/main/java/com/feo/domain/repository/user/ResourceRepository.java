package com.feo.domain.repository.user;

import com.feo.domain.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Role,Integer> {

}
