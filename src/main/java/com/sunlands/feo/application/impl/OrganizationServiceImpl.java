package com.sunlands.feo.application.impl;

import com.sunlands.feo.application.OrganizationService;
import com.sunlands.feo.common.AssertUtil;
import com.sunlands.feo.common.StrUtil;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.exception.UserDefinedException;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.repository.orgInfo.OrgInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 组织结构实现层
 * Created by yangchao on 17/11/9.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    //组织结构Repository
    private OrgInfoRepository orgInfoRepository;

    @Override
    public Map<String, String> queryOrgLevels(OrganizationLevel ol) {
        AssertUtil.isNotNull(ol);
        OrganizationLevel[] ols = OrganizationLevel.values();
        Map<String, String> m = new LinkedHashMap<>();
        for (OrganizationLevel o : ols) {
            //军团登录,不加载事业部类型
            if (ol.equals(OrganizationLevel.ARMY) && o.equals(OrganizationLevel.BUSINESS_UNIT))
                continue;
            m.put(o.name(), o.getName());
        }
        return m;
    }

    @Override
    public Organization createOrg(Organization org) {
        AssertUtil.isNotNull(org);
        AssertUtil.isNotNull(org.getCode());
        AssertUtil.isNotNull(org.getName());
        AssertUtil.isNotNull(org.getOrganizationLevel());
        //AssertUtil.isNotNull(org.getParentId());
        Organization temp = orgInfoRepository.findByCode(org.getCode());
        if ((StrUtil.isNull(org.getId()) && StrUtil.isNotNull(temp)) ||
                (StrUtil.isNotNull(org.getId()) && !Objects.equals(temp.getId(), org.getId())))
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED, "机构code重复");
        return orgInfoRepository.saveAndFlush(org);
    }

    @Override
    public List<Organization> queryOrgsByLevel(OrganizationLevel ol) {
        AssertUtil.isNotNull(ol);
        //根节点
        List<Organization> root = orgInfoRepository.findByParentIdIsNullOrderByCode();
        AssertUtil.isNotNull(root, ServerStatus.NO_EXIST.getMsg());
        for (Organization o : root) {
            o.setItem(null);
        }
        if (root.get(0).getOrganizationLevel().equals(ol)) return root;
        List list = new ArrayList<>();
        for (Organization o : root) {
            list.add(loopOrganization(o, ol));
        }
        return list;
    }

    @Override
    public Organization queryByOrgId(Integer id) {
        AssertUtil.isNotNull(id);
        return orgInfoRepository.findOne(id);
    }

    /**
     * 递归组织结构树
     *
     * @param org 组织结构
     * @param ol  组织结构级别
     * @return 组织结构
     */
    private Object loopOrganization(Organization org, OrganizationLevel ol) {
        if (org.getOrganizationLevel().equals(ol)) {
            org.setItem(null);
            return ol;
        }
        List<Organization> items = orgInfoRepository.findByParentIdOrderByCode(org.getId());
        if (StrUtil.isNull(items)) return items;
        Set<Organization> sets = new LinkedHashSet<>();
        sets.addAll(items);
        org.setItem(sets);

        for (Organization o : items) {
            loopOrganization(o, ol);
        }
        return org;
    }

    @Override
    public List<Map<String, Object>> getUpOrg(Integer id) {
        //查询当前等级
        Organization organization = orgInfoRepository.findOne(id);
        OrganizationLevel organizationLevel = organization.getOrganizationLevel();
        //初始化循环参数
        Integer upCount = 0;
        if (organizationLevel.equals(OrganizationLevel.SALES_GROUP)) {
            upCount = 3;
        } else if (organizationLevel.equals(OrganizationLevel.SALES_DEPARTMENT)) {
            upCount = 2;
        } else if (organizationLevel.equals(OrganizationLevel.ARMY)) {
            upCount = 1;
        }
        //组织结构最大循环次数
        Integer orgMxNum = 3;
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer i = 0; i <= orgMxNum; i++) {
            Map<String, Object> map = new HashMap<>();
            //封装所需参数
            map.put("id", orgMxNum.equals(upCount) ? organization.getId() : "");
            map.put("name", orgMxNum.equals(upCount) ? organization.getName() : "");
            list.add(map);
            //获得父级结构
            organization = orgMxNum.equals(upCount) ? organization.getParent() : organization;
            upCount = orgMxNum.equals(upCount) ? upCount : ++upCount;
        }
        return list;
    }

    @Override
    public List<Integer> getDowmOrg(Integer id) {
        //查询当前等级
        List<Integer> ids = new ArrayList<>();
        Organization items = orgInfoRepository.findOne(id);
        OrganizationLevel organizationLevel = items.getOrganizationLevel();
        //初始化循环参数
        Integer downCount = 0;
        if (organizationLevel.equals(OrganizationLevel.BUSINESS_UNIT)) {
            downCount = 3;
        } else if (organizationLevel.equals(OrganizationLevel.ARMY)) {
            downCount = 2;
        } else if (organizationLevel.equals(OrganizationLevel.SALES_DEPARTMENT)) {
            downCount = 1;
        }
        switch (downCount) {
            case 3:
                for (Organization org1 : items.getItem()) {
                    ids.add(org1.getId());
                    for (Organization org2 : org1.getItem()) {
                        ids.add(org2.getId());
                        for (Organization org3 : org2.getItem()) {
                            ids.add(org3.getId());
                        }
                    }
                }
                break;
            case 2:
                for (Organization org1 : items.getItem()) {
                    ids.add(org1.getId());
                    for (Organization org2 : org1.getItem()) {
                        ids.add(org2.getId());
                    }
                }
                break;
            case 1:
                for (Organization org1 : items.getItem()) {
                    ids.add(org1.getId());
                }
                break;
            default:
                ids.add(id);
                break;
        }
        return ids;
    }

    @Autowired
    public void setOrgInfoRepository(OrgInfoRepository orgInfoRepository) {
        this.orgInfoRepository = orgInfoRepository;
    }
}
