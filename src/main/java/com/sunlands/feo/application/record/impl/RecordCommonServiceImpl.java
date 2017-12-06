package com.sunlands.feo.application.record.impl;

import com.sunlands.feo.application.UserService;
import com.sunlands.feo.application.record.RecordCommonService;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.exception.UserDefinedException;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.model.record.QuestionType;
import com.sunlands.feo.domain.model.record.ViolationType;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.orgInfo.OrgInfoRepository;
import com.sunlands.feo.domain.repository.record.QuestionTypeRepository;
import com.sunlands.feo.domain.repository.record.ViolationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 质检通用实现层
 * Created by huang on 2017/11/9.
 */
@Service
public class RecordCommonServiceImpl implements RecordCommonService {

    //质检类型Repository
    private QuestionTypeRepository questionTypeRepository;

    //质检违规Repository
    private ViolationTypeRepository violationTypeRepository;

    //用户Service
    private UserService userService;

    //组织结构Repository
    private OrgInfoRepository orgInfoRepository;

    @Override
    public List<QuestionType> selectRecordQuestionGanged(Integer type, Long id) {
        //查询问题二级联动
        List<QuestionType> list = questionTypeRepository.findAllByParentIdAndType(id, type);
        if (list.size() > 0) {
            return list;
        }
        //数据提交失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Override
    public List<ViolationType> selectRecordViolationGanged(Long id) {
        //查询违规三级联动
        List<ViolationType> list = violationTypeRepository.findAllByParentId(id);
        if (list.size() > 0) {
            return list;
        }
        //数据提交失败，服务器繁忙
        throw new UserDefinedException(ServerStatus.DEFAULT_EXCEPTION);
    }

    @Override
    public Set<Map<String, Object>> selectOrgs(Integer id) {
        //sop负责四级组织结构
        Set<Map<String, Object>> resultList = new HashSet<>();
        Set<Organization> mngOrganizations = getUser().getMngOrganizations();
        //判断是否初级结构
        if (id == 0) {
            for (Organization organization : mngOrganizations) {
                if (organization.getOrganizationLevel().equals(OrganizationLevel.BUSINESS_UNIT)) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", organization.getId());
                    m.put("name", organization.getName());
                    resultList.add(m);
                }
            }
        } else {
            Organization one = orgInfoRepository.findOne(id);
            Integer temp = 0;
            for (Organization org : mngOrganizations) {
                if (org.getId().equals(id)) {
                    temp = 1;
                    break;
                }
            }
            if (temp == 1 && one.getOrganizationLevel().equals(OrganizationLevel.BUSINESS_UNIT)) {
                Organization organization = new Organization();
                organization.setId(id);
                List<Organization> allByParentId = orgInfoRepository.findAllByParent(organization);
                for (Organization org1 : mngOrganizations) {
                    for (Organization org2 : allByParentId) {
                        if (org1.getId().equals(org2.getId())) {
                            Map<String, Object> m = new HashMap<>();
                            m.put("id", org1.getId());
                            m.put("name", org1.getName());
                            resultList.add(m);
                        }
                    }
                }
            }
            if (temp == 1) {
                Organization organization = new Organization();
                organization.setId(id);
                List<Organization> allByParentId = orgInfoRepository.findAllByParent(organization);
                for (Organization org : allByParentId) {
                    Map<String, Object> m = new HashMap<>();
                    m.put("id", org.getId());
                    m.put("name", org.getName());
                    resultList.add(m);
                }
            }
        }
        return resultList;
    }

    @Override
    public List<Map<String, Object>> selectAllOrgs(Integer id) {
        //sop负责四级组织结构
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Organization> orgList;
        //查询
        if (id == 0) {
            orgList = orgInfoRepository.findAllByParentIsNull();
        } else {
            Organization organization = new Organization();
            organization.setId(id);
            orgList = orgInfoRepository.findAllByParent(organization);
        }
        for (Organization org : orgList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", org.getName());
            map.put("id", org.getId());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户实体
     */
    public User getUser() {
        return userService.getUser();
    }

    @Autowired
    public void setQuestionTypeRepository(QuestionTypeRepository questionTypeRepository) {
        this.questionTypeRepository = questionTypeRepository;
    }

    @Autowired
    public void setViolationTypeRepository(ViolationTypeRepository violationTypeRepository) {
        this.violationTypeRepository = violationTypeRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrgInfoRepository(OrgInfoRepository orgInfoRepository) {
        this.orgInfoRepository = orgInfoRepository;
    }
}
