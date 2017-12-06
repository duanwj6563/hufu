/**
 * @describe
 * @author duanwj
 * @since 2017年9月30日 上午10:23:33
 */
package com.sunlands.feo.domain.repository.user;

import com.sunlands.feo.domain.model.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
