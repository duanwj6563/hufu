package com.sunlands.feo.application;

import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.orgInfo.Organization;

import java.util.List;
import java.util.Map;

/**
 * 组织结构Service
 * Created by yangchao on 17/11/9.
 */
public interface OrganizationService {
    /**
     * 查询机构级别列表
     *
     * @param ol 当前登录人机构级别
     * @return 组织结构集合
     */
    Map<String, String> queryOrgLevels(OrganizationLevel ol);

    /**
     * 创建组织建构
     *
     * @param org 组织结构
     * @return 创建的组织建构
     */
    Organization createOrg(Organization org);

    /**
     * 根据所选级别，检索出机构列表
     *
     * @param ol 组织结构级别
     * @return 组织结构集合
     */
    List<Organization> queryOrgsByLevel(OrganizationLevel ol);

    /**
     * 通过id获取组织机构
     *
     * @param id 组织结构id
     * @return 组织结构
     */
    Organization queryByOrgId(Integer id);

    /**
     * 向上查出所有组织结构
     *
     * @param id 组织结构id
     * @return 组织结构集合
     */
    List<Map<String, Object>> getUpOrg(Integer id);

    /**
     * 向下查出所有销售组id
     *
     * @param id 组织结构id
     * @return 组织结构集合
     */
    List<Integer> getDowmOrg(Integer id);

}
