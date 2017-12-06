package com.sunlands.feo.application.boutique.impl;

import com.sunlands.feo.application.OrganizationService;
import com.sunlands.feo.application.UserService;
import com.sunlands.feo.application.boutique.BoutiqueSevice;
import com.sunlands.feo.application.strategy.StrategyLogService;
import com.sunlands.feo.common.DateUtil;
import com.sunlands.feo.common.ExcelUtil;
import com.sunlands.feo.domain.exception.ServerStatus;
import com.sunlands.feo.domain.model.boutique.BoutiqueRecord;
import com.sunlands.feo.domain.model.boutique.BoutiqueStrategy;
import com.sunlands.feo.domain.model.boutique.BoutiqueStrategyUser;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.enums.record.GoodTypeEnums;
import com.sunlands.feo.domain.model.enums.record.ItemEnums;
import com.sunlands.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.sunlands.feo.domain.model.orgInfo.Organization;
import com.sunlands.feo.domain.model.record.Record;
import com.sunlands.feo.domain.model.strategy.Strategy;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.boutique.BoutiqueRecordRepository;
import com.sunlands.feo.domain.repository.boutique.BoutiqueStrategyRepository;
import com.sunlands.feo.domain.repository.boutique.BoutiqueStrategyUserRepository;
import com.sunlands.feo.domain.repository.orgInfo.OrgInfoRepository;
import com.sunlands.feo.domain.repository.record.RecordRepository;
import com.sunlands.feo.domain.repository.strategy.StrategyRepository;
import com.sunlands.feo.domain.repository.user.UserRepository;
import com.sunlands.feo.port.rest.controller.UserController;
import com.sunlands.feo.port.rest.dto.boutique.BoutiqueDto;
import com.sunlands.feo.port.rest.dto.boutique.BoutiqueSelectCondition;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;

@Service
public class BoutiqueServiceImpl implements BoutiqueSevice {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private StrategyLogService strategyLogService;
    @Autowired
    private BoutiqueStrategyRepository boutiqueStrategyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private BoutiqueRecordRepository boutiqueRecordRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private UserService userService;
    @Autowired
    private BoutiqueStrategyUserRepository boutiqueStrategyUserRepository;

    @Override
    public boolean setBoutiqueStrategy(Long strategyId) {
        /*  将策略设为精品 */
        Strategy strategy = strategyRepository.findOne(strategyId);
        Organization organization=getGroup(userRepository.findOne(strategy.getUid()).getOrganization());
        BoutiqueStrategy boutiqueStrategy = boutiqueStrategyRepository.findByStrategyId(strategyId);
        User user = userService.getUser();
        if (boutiqueStrategy == null) {
            boutiqueStrategy = new BoutiqueStrategy();
            boutiqueStrategy.setStrategyId(strategyId);
            boutiqueStrategy.setName(strategy.getName());
            boutiqueStrategy.setGroupName(organization.getName());
            boutiqueStrategy.setGroupCode(organization.getCode());
            boutiqueStrategy.setCreateDate(strategy.getCreateDate());
            boutiqueStrategy.setJoinDate(new Date());
            boutiqueStrategy.setStatus(strategy.getStatus());
        }
        List<User> users = boutiqueStrategy.getUsers();
        for (int i = 0; i < users.size(); i++ ) {
            if (users.get(i).getId().equals(user.getId())) {
                return false;
            }
        }
        boutiqueStrategy.setUpdateDate(new Date());
        boutiqueStrategy.setUsers(new ArrayList<User>());
        boutiqueStrategyRepository.saveAndFlush(boutiqueStrategy);
        boutiqueStrategy.getUsers().add(user);
        boutiqueStrategyRepository.saveAndFlush(boutiqueStrategy);
        strategy.getStrategyLogs().add(strategyLogService.newStrategyLog(user.getId(), "将策略" + strategy.getName() + "设为精品", StrategyLogOperationType.BOUTIQUE));
        strategyRepository.saveAndFlush(strategy);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteBoutiqueStrategy(Long strategyId) {
        /* 取消精品策略 */
        Strategy strategy = strategyRepository.findOne(strategyId);
        BoutiqueStrategy boutiqueStrategy = boutiqueStrategyRepository.findByStrategyId(strategyId);
        User user = userService.getUser();
        if (boutiqueStrategy == null) {
            return false;
        }
        List<User> users = boutiqueStrategy.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.remove(i);
                strategy.getStrategyLogs().add(strategyLogService.newStrategyLog(user.getId(), "将策略" + strategy.getName() + "取消精品", StrategyLogOperationType.BOUTIQUE));
                i--;
            }
        }
        boutiqueStrategyUserRepository.deleteBoutiqueStrategyUserByStrategyIdAndAndUid(strategyId,user.getId());
        List<BoutiqueStrategyUser> boutiqueStrategyUsers = boutiqueStrategyUserRepository.findAllByStrategyId(strategyId);
        if (boutiqueStrategyUsers==null||boutiqueStrategyUsers.size() == 0) {
            boutiqueStrategyRepository.delete(boutiqueStrategy);
        }
        strategyRepository.saveAndFlush(strategy);
        return true;
    }

    @Override
    public boolean setBoutiqueRecord(Long recordId) {
        /*  设为精品 */
        BoutiqueRecord boutiqueRecord =boutiqueRecordRepository.findOne(recordId);
        if (boutiqueRecord==null){
            Record record = recordRepository.findOne(recordId);
            Organization organization=getGroup(record.getUserInfo().getOrganization());
            boutiqueRecord = new BoutiqueRecord();
            boutiqueRecord.setId(recordId);
            boutiqueRecord.setRecordId(recordId);
            boutiqueRecord.setUrl(record.getUrl());
            boutiqueRecord.setCreateDate(record.getDate());
            boutiqueRecord.setJoinDate(new Date());
            boutiqueRecord.setGoodType(record.getGoodType());
            boutiqueRecord.setCounselorId(record.getUserInfo().getId());
            boutiqueRecord.setCounselorName(record.getUserInfo().getName());
            boutiqueRecord.setGroupName(organization.getName());
            boutiqueRecord.setGroupCode(organization.getCode());
            boutiqueRecord.setItemType(record.getItemType());
        }
        User user =userService.getUser();
        boutiqueRecord.getUsers().add(user);
        boutiqueRecordRepository.saveAndFlush(boutiqueRecord);
        return true;
    }

    @Override
    public boolean deleteBoutiqueRecord(Long recordId) {
        /*  取消精品录音 */
        BoutiqueRecord boutiqueRecord = boutiqueRecordRepository.findOne(recordId);
        List<User> users = boutiqueRecord.getUsers();
        User user = userService.getUser();
        for (int i = 0; i < users.size(); i++
                ) {
            if (users.get(i).getId() == user.getId()) {
                users.remove(i);
            }
            i--;
        }
        boutiqueRecordRepository.saveAndFlush(boutiqueRecord);
        if (users.size() == 0) {
            boutiqueRecordRepository.delete(boutiqueRecord);
        }
        return true;
    }

    @Override
    @Transactional
    public Map<String,Object> queryBoutiqueStrategy(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException {
        /* 获取精品策略列表 */
        List<BoutiqueStrategy> contents = new ArrayList<>();
        Sort sort = new Sort(Sort.Direction.DESC, "updateDate");
        Pageable pageable = new PageRequest(boutiqueSelectCondition.getPageNum()-1, boutiqueSelectCondition.getPageSize(), sort);
        Page<BoutiqueStrategy> page =null;
        Page<BoutiqueStrategyUser> page1 =null;
        User user=userService.getUser();
        handleBoutiqueSelectCondition(boutiqueSelectCondition);
        Date startTime=DateUtil.stringToDate(boutiqueSelectCondition.getStartTime());
        Date endTime=null;
        if (boutiqueSelectCondition.getEndTime()==null){
            endTime=new Date();
        }else {
            endTime=DateUtil.stringToDate(boutiqueSelectCondition.getEndTime());
        }
        Integer[] orgs=getOrgs(boutiqueSelectCondition);
        if (boutiqueSelectCondition.getOwn()==null||boutiqueSelectCondition.getOwn()==1){
            long[] ids=boutiqueStrategyRepository.getStrategyIds(orgs);
            if (orgs==null||orgs.length==0){
                page = boutiqueStrategyRepository.findAllByStatusAndUpdateDateAfterAndUpdateDateBefore(2, startTime, endTime, pageable);
            }else {
                page= boutiqueStrategyRepository.findAllByStrategyIdInAndStatusAndUpdateDateAfterAndUpdateDateBefore(ids,2,startTime, endTime, pageable);
            }
            contents = page.getContent();
        }else {
            Long[] ids;
            Sort sort1 = new Sort(Sort.Direction.DESC, "joinDate");
            Pageable pageable1 = new PageRequest(boutiqueSelectCondition.getPageNum()-1, boutiqueSelectCondition.getPageSize(), sort1);
            if (orgs==null||orgs.length==0){
                page1=boutiqueStrategyUserRepository.findAllByUidAndJoinDateAfterAndJoinDateBefore(user.getId(), startTime, endTime,pageable1);
            }else {
                ids = boutiqueStrategyRepository.getStrategyIds1(orgs,user.getId());
                page1=boutiqueStrategyUserRepository.findAllByStrategyIdInAndJoinDateAfterAndJoinDateBefore(ids, startTime, endTime,pageable1);
            }
            List<BoutiqueStrategyUser> boutiqueStrategyUsers = page1.getContent();
            contents=new ArrayList<>();
            for (int i=0;i<boutiqueStrategyUsers.size();i++){
                BoutiqueStrategyUser boutiqueStrategyUser = boutiqueStrategyUsers.get(i);
                BoutiqueStrategy bou=boutiqueStrategyRepository.findByStrategyId(boutiqueStrategyUser.getStrategyId());
                if (bou!=null) {
                    bou.setUpdateDate(boutiqueStrategyUser.getJoinDate());
                    contents.add(bou);
                }
            }
        }
        List<BoutiqueDto> content=new ArrayList<>();
        for (int i=0;i<contents.size();i++){
            BoutiqueStrategy boutiqueStrategy = contents.get(i);
            if (boutiqueStrategy.getName()==null||boutiqueStrategy.getName().equals("")){
                boutiqueStrategy=boutiqueStrategyRepository.findOne(boutiqueStrategy.getId());
            }
            BoutiqueDto bou=new BoutiqueDto();
            bou.setId(boutiqueStrategy.getStrategyId());
            bou.setGroupName(boutiqueStrategy.getGroupName());
            bou.setName(boutiqueStrategy.getName());
            bou.setStrategyId(boutiqueStrategy.getStrategyId());
            bou.setDate(boutiqueStrategy.getUpdateDate());
            bou.setCreateDate(boutiqueStrategy.getCreateDate());
            content.add(bou);
        }
        if (boutiqueSelectCondition.getOwn()==null||boutiqueSelectCondition.getOwn()==1){
            return getPageHashMap(page,content);
        }
        return getPageHashMap(page1,content);
    }

    @Override
    @Transactional
    public Map<String,Object> queryBoutiqueRecord(BoutiqueSelectCondition boutiqueSelectCondition) throws ParseException, InvocationTargetException, IllegalAccessException {
        /* 获取精品录音列表 */
        Sort sort = new Sort(Sort.Direction.DESC, "joinDate");
        Pageable pageable = new PageRequest(boutiqueSelectCondition.getPageNum()-1, boutiqueSelectCondition.getPageSize(), sort);
        Page<BoutiqueRecord> page =null;
        handleBoutiqueSelectCondition(boutiqueSelectCondition);
        Date startTime=DateUtil.stringToDate(boutiqueSelectCondition.getStartTime());
        Date endTime=null;
        if (boutiqueSelectCondition.getEndTime()==null){
            endTime=new Date();
        }else {
            endTime=DateUtil.stringToDate(boutiqueSelectCondition.getEndTime());
        }
        List<Map<String, Object>> goodTypeList = GoodTypeEnums.getIdAndNameList();
        List<Map<String, Object>> recTypeList = ItemEnums.getIdAndNameList();
        int[] goodTypes=null;
        if (boutiqueSelectCondition.getGoodType()==null){
            goodTypes=new int[goodTypeList.size()];
            for (int i=0;i<goodTypeList.size();i++){
                goodTypes[i]= Integer.parseInt( goodTypeList.get(i).get("id")+"");
            }
        }else {
            goodTypes=new int[1];
            goodTypes[0]=boutiqueSelectCondition.getGoodType();
        }
        int[] itemTypes;
        if (boutiqueSelectCondition.getItemType()==null){
            itemTypes=new int[recTypeList.size()];
            for (int i=0;i<recTypeList.size();i++){
                itemTypes[i]= Integer.parseInt(""+recTypeList.get(i).get("id")) ;
            }
        }else {
            itemTypes=new int[1];
            itemTypes[0]=boutiqueSelectCondition.getItemType();
        }

        Integer[] orgs=getOrgs(boutiqueSelectCondition);

        if (boutiqueSelectCondition.getOwn()==null||boutiqueSelectCondition.getOwn()==1){
            if (orgs==null||orgs.length==0){
                page = boutiqueRecordRepository.findAllByJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndAndItemTypeIn(startTime, endTime,goodTypes,itemTypes,pageable);
            }else {
                int[] c=boutiqueRecordRepository.getUserInfoIds(orgs);
                page=boutiqueRecordRepository.findAllByCounselorIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(c,startTime, endTime,goodTypes,itemTypes,pageable);
            }
        }else {
            long[] ids = boutiqueRecordRepository.getIds(userService.getUser().getId());
            if (orgs==null||orgs.length==0){
                page = boutiqueRecordRepository.findAllByIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(ids,startTime, endTime,goodTypes,itemTypes,pageable);
            }else {
                int[] c=boutiqueRecordRepository.getUserInfoIds(orgs);
                page= boutiqueRecordRepository.findAllByIdInAndCounselorIdInAndJoinDateAfterAndJoinDateBeforeAndGoodTypeInAndItemTypeIn(ids,c,startTime, endTime,goodTypes,itemTypes,pageable);
            }
        }
        List<BoutiqueRecord> contents = page.getContent();
        List<BoutiqueDto> content=new ArrayList<>();
        for (int i=0;i<contents.size();i++){
            BoutiqueRecord boutiqueRecord = contents.get(i);
            BoutiqueDto bou=new BoutiqueDto();
//            BeanUtils.copyProperties(boutiqueRecord,bou);
            bou.setName(boutiqueRecord.getName());
            bou.setGroupName(boutiqueRecord.getGroupName());
            bou.setId(boutiqueRecord.getId());
            bou.setDate(boutiqueRecord.getJoinDate());
            bou.setGoodType(GoodTypeEnums.values()[boutiqueRecord.getGoodType()].getName());
            bou.setCounselorName(boutiqueRecord.getCounselorName());
            bou.setItemType(ItemEnums.values()[boutiqueRecord.getItemType()].getName());
            bou.setCreateDate(bou.getCreateDate());
            content.add(bou);
        }
        return getPageHashMap(page,content);
    }

    @Override
    public Boolean decideBoutiqueRecord(Long recordId) {
        User user=userService.getUser();
        long[] longs = boutiqueRecordRepository.decideBoutiqueRecord(user.getId(), recordId);
        if (longs==null||longs.length<=0){
            return false;
        }
        return true;
    }

    @Override
    public Boolean decideBoutiqueStrategy(Long strategyId) {
        User user=userService.getUser();
        long[] longs = boutiqueStrategyRepository.decideBoutiqueStrategy(user.getId(), strategyId);
        if (longs==null||longs.length<=0){
            return false;
        }
       return true;
    }

    @Override
    public List<Map<String, Object>> getItems() {
        return ItemEnums.getIdAndNameList();
    }

    @Override
    public ServerStatus batchDownloadRecords(Long[] records, HttpServletResponse response, HttpServletRequest request) throws IOException {
        BoutiqueRecord[] boutiqueRecords = boutiqueRecordRepository.findAllByIdIn(records);
        ExcelUtil.downloadRecordExcel(boutiqueRecords,response,request);
        return ServerStatus.SUCCESS;
    }

    //根据员工工号获取其所属军团组织
    public Organization getGroup(Organization organization){
        if (organization.getOrganizationLevel()== OrganizationLevel.ARMY){
            return organization;
        }else {
            return getGroup(organization.getParent());
        }
    }

    public void handleBoutiqueSelectCondition(BoutiqueSelectCondition boutiqueSelectCondition){
        if (boutiqueSelectCondition.getStartTime()==null||boutiqueSelectCondition.getStartTime().equals("")){
            boutiqueSelectCondition.setStartTime("2016-11-11");
        }
    }

    public Integer[] getOrgs(BoutiqueSelectCondition boutiqueSelectCondition){
        Integer[] orgs;
        List<Integer> dowmOrg=new ArrayList<>();
        if (boutiqueSelectCondition.getLevelFour()!=null){
            dowmOrg = organizationService.getDowmOrg(boutiqueSelectCondition.getLevelFour());
        }else if (boutiqueSelectCondition.getLevelThree()!=null){
            dowmOrg = organizationService.getDowmOrg(boutiqueSelectCondition.getLevelThree());
        }else if (boutiqueSelectCondition.getLevelTwo()!=null){
            dowmOrg = organizationService.getDowmOrg(boutiqueSelectCondition.getLevelTwo());
        }else if (boutiqueSelectCondition.getLevelOne()!=null){
            dowmOrg = organizationService.getDowmOrg(boutiqueSelectCondition.getLevelOne());
        }
        orgs=new Integer[dowmOrg.size()];
        dowmOrg.toArray(orgs);
        return orgs;
    }

    public  Map<String,Object>  getPageHashMap(Page page,List<BoutiqueDto> list){
        Map<String,Object> map=new HashMap<>();
        map.put("content",list);
        map.put("totalElements",page.getTotalElements());
        map.put("totalPages",page.getTotalPages());
        map.put("number",page.getNumber()+1);
        map.put("size",page.getSize());
        return map;
    }
}
