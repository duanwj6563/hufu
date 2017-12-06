/**
 * @describe
 * @author duanwj
 * @since 2017年9月30日 上午10:23:33
 */
package com.sunlands.feo.domain.repository.user;

import com.sunlands.feo.domain.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    List<User> findByOrganization_id(Integer id);

    @Query(value = "SELECT hur.u_id FROM hufu_user_role hur,hufu_role hr WHERE hr.id=hur.r_id AND hr.`name` IN(?1)", nativeQuery = true)
    Integer[] selectUserIdsByRole(String names);

    List<User> findAllByIdIn(Integer[] userIds);

    @Query(value = "SELECT hu.id FROM hufu_role hr,hufu_user_role hur,hufu_user hu,hufu_organization horg WHERE hur.r_id=hr.id AND hu.id=hur.u_id AND hu.org_id=horg.id AND hr.`name`= ?1 AND horg.id = ?2", nativeQuery = true)
    Integer selectMaxRoleById(String name, Integer id);

    @Query(value = "SELECT hu.id FROM hufu_role hr,hufu_user_role hur,hufu_user hu,hufu_organization horg WHERE hur.r_id=hr.id AND hu.id=hur.u_id AND hu.org_id=horg.id AND hr.`name`= ?1 AND horg.id IN ?2", nativeQuery = true)
    List<Integer> selectManagerById(String name, List<Integer> id);
}
