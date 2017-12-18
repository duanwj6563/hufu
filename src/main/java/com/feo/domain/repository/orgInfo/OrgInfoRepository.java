package com.feo.domain.repository.orgInfo;

import com.feo.domain.model.orgInfo.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrgInfoRepository extends JpaRepository<Organization, Integer> {
    List<Organization> findAllByParent(Organization organization);

    List<Organization> findAllByParentIsNull();

    Organization findByCode(String code);

    List<Organization> findByParentIdOrderByCode(Integer parentId);

    List<Organization> findByParentIdIsNullOrderByCode();

    @Query(value = "SELECT hu.id FROM hufu_user hu,hufu_role hr,hufu_user_role hur WHERE hr.id=hur.r_id AND hur.u_id=hu.id AND hr.`name` =?1 AND hu.org_id IN (?2)", nativeQuery = true)
    List<Integer> selectUserIdByNameAndOrgId(String name, List<Integer> ids);

    @Query(value = "SELECT humo.mng_user_id FROM hufu_user_mng_organization humo,hufu_user_role hur,hufu_role hr WHERE humo.mng_user_id=hur.u_id AND hr.id=hur.r_id AND hr.`name`=?1 AND humo.organization_id IN(?2) GROUP BY humo.mng_user_id", nativeQuery = true)
    List<Integer> selectSopUserIdByNameAndOrgId(String name, List<Integer> ids);
}
