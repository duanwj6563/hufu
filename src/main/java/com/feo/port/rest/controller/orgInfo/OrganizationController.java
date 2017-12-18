package com.feo.port.rest.controller.orgInfo;

import com.feo.common.AssertUtil;
import com.feo.domain.model.enums.OrganizationLevel;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.domain.repository.orgInfo.OrgInfoRepository;
import com.feo.application.OrganizationService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/org")
@RestController
public class OrganizationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrgInfoRepository orgInfoRepository;
    @Autowired
    private OrganizationService organizationService;

    /**
     * 查询机构子节点
     *
     * @param
     * @return
     */
    @GetMapping()
    @ApiOperation(value = "查询机构子节点")
    public List<Organization> getOrgInfo(@RequestParam(name = "parentId", required = false) String parentId) {
        AssertUtil.isNotNull(parentId);
        List<Organization> obj = null;
        if ("null".equals(parentId)) {
            obj = orgInfoRepository.findByParentIdIsNullOrderByCode();
        } else {
            obj = orgInfoRepository.findByParentIdOrderByCode(Integer.valueOf(parentId));
        }
        return obj;
    }

    /**
     * 根据所选级别，查询机构列表
     *
     * @param organizationLevel
     * @return
     */
    @GetMapping(value = "/orgLevel")
    @ApiOperation(value = "根据所选级别，查询机构列表")
    public List<Organization> getOrgInfo(@RequestParam OrganizationLevel organizationLevel) {
        return organizationService.queryOrgsByLevel(organizationLevel);
    }

    /**
     * 查询机构级别枚举项
     *
     * @param organizationLevel 当前登录人机构级别
     * @return
     */
    @GetMapping(value = "/levels")
    @ApiOperation(value = "查询机构级别枚举项")
    public Map<String, String> queryOrgLevel(@RequestParam OrganizationLevel organizationLevel) {
        AssertUtil.isNotNull(organizationLevel);
        return organizationService.queryOrgLevels(organizationLevel);
    }

    @PostMapping()
    @ApiOperation(value = "创建机构")
    public Organization queryOrgLevel(@RequestBody Organization organization) {
        AssertUtil.isNotNull(organization);
        return organizationService.createOrg(organization);
    }
}

