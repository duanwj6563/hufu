package com.feo.application.strategy.impl;


import com.feo.application.OrganizationService;
import com.feo.application.UserService;
import com.feo.application.strategy.StrategyLogService;
import com.feo.application.strategy.StrategyService;
import com.feo.common.*;
import com.feo.domain.exception.ServerStatus;
import com.feo.domain.exception.UserDefinedException;
import com.feo.domain.model.boutique.BoutiqueStrategy;
import com.feo.domain.model.enums.AuditStatus;
import com.feo.domain.model.enums.OrganizationLevel;
import com.feo.domain.model.enums.strategy.AreaEnums;
import com.feo.domain.model.enums.strategy.ProjectEnums;
import com.feo.domain.model.enums.strategy.ScoreTypeEnums;
import com.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.feo.domain.model.enums.user.RoleEnums;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.domain.model.reportform.Analyze;
import com.feo.domain.model.strategy.*;
import com.feo.domain.model.user.Role;
import com.feo.domain.model.user.User;
import com.feo.domain.model.user.UserInfo;
import com.feo.domain.repository.boutique.BoutiqueStrategyRepository;
import com.feo.domain.repository.orgInfo.OrgInfoRepository;
import com.feo.domain.repository.report.AnalysisRepository;
import com.feo.domain.repository.strategy.*;
import com.feo.domain.repository.user.UserRepository;
import com.feo.port.rest.controller.UserController;
import com.feo.port.rest.dto.CommonPage;
import com.feo.port.rest.dto.FileInfo;
import com.feo.port.rest.dto.strategy.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
public class StrategyServiceImpl implements StrategyService {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private StrategyRepository strategyRepository;
    @Autowired
    private StrategyPhaseRepository strategyPhaseRepository;
    @Autowired
    private AdditionContentRepository additionContentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdviseRepository adviseRepository;
    @Autowired
    private StrategyLogService strategyLogService;
    @Autowired
    private OrgInfoRepository orgInfoRepository;
    @Autowired
    private StrategyLogRepository strategyLogRepository;
    @Autowired
    private BoutiqueStrategyRepository boutiqueStrategyRepository;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private UserService userService;

    @Override
    public Strategy findById(Long id) {
        return strategyRepository.findOne(id);
    }


    @Override
    @Transactional
    public Map<String, Object> createStrategy(Long strategyId) {
        Map<String, Object> map = new HashMap<>();
        if (strategyId == null || strategyId == 0) {
             /* 返回最新通过策略用于回显 */
            User user = userService.getUser();
            Strategy strategy = strategyRepository.getTopDateStrategy(user.getId());
            StrategyBase strategyBase = new StrategyBase();
            if (strategy!=null){
                BeanUtils.copyProperties(strategy, strategyBase);
            }
            strategyBase.setId(0l);
            map.put("strategyBase", strategyBase);
        } else {
            /* 查询修改策略基本信息，用于回显*/
            Strategy strategy = strategyRepository.findOne(strategyId);
//            List<Organization> applydeparts = getOrgTree(strategy.getApplydeparts());
            //TODO 用户验证（是否是策略创建者在修改）
            isUser(strategy.getUid());
            StrategyBase strategyBase = new StrategyBase();
            BeanUtils.copyProperties(strategy, strategyBase);
//            strategyBase.setApplydeparts(applydeparts);
            map.put("strategyBase", strategyBase);
        }
        return map;
    }

    @Override
    public Map<String, Object> addStrategyBase(Long oldStrategyId, StrategyBase strategyBase) {
        /* 添加策略基本信息 */
        //TODO 组织结构添加
        //TODO 有效期验证
        Strategy newStrategy = new Strategy();
        BeanUtils.copyProperties(strategyBase, newStrategy);
        logger.info("添加策略id为" + newStrategy.getId() + "基本信息");
        Long id = newStrategy.getId();
        Map<String, Object> map = new HashMap<>();
        /* 如果id为空创建新策略，如果不为空.修改策略基本信息 */
        if (id == null || id == 0) {
            String msg = validityPeriod(strategyBase.getApplydeparts());
            if (msg!=null && msg!=""){
                map.put("msg",msg);
                return map;
            }
            map.put("msg",null);
            // 为新建策略获取基本信息
            newStrategy.setStatus(0);
            User user = userService.getUser();
            newStrategy.setUid(user.getId());
            newStrategy.setCreateDate(new Date());
            newStrategy.setUpdateTime(new Date());
            newStrategy.setStatus(AuditStatus.CREATED.getCode());
            //TODO 添加军团名称
            newStrategy.setGroupName(user.getOrganization().getName());
            StrategyLog strategyLog = strategyLogService.newStrategyLog(newStrategy.getUid(), "创建并添加策略基本信息", StrategyLogOperationType.CREATED);
            newStrategy.getStrategyLogs().add(strategyLog);
            newStrategy.setId(null);
            newStrategy.setStartTime(lastMonday());
            FirstStrategyPhase firstStrategyPhase1=new FirstStrategyPhase();
            firstStrategyPhase1.setPhase(1);
            SecStrategyPhase secStrategyPhase=new SecStrategyPhase();
            secStrategyPhase.setPhase(2);
            FinalStrategyPhase finalStrategyPhase=new FinalStrategyPhase();
            finalStrategyPhase.setPhase(3);
            newStrategy.getStrategyPhases().add(firstStrategyPhase1);
            newStrategy.getStrategyPhases().add(secStrategyPhase);
            newStrategy.getStrategyPhases().add(finalStrategyPhase);
            Strategy save=strategyRepository.save(newStrategy);
            if (save==null){
                //TODO 抛异常
                throw new UserDefinedException(ServerStatus.OPERATION_FAILED,"创建策略失败");
            }
            //获取最新审核通过策略的首咨策略阶段，封装到map中
            Strategy oldStrategy = strategyRepository.getTopDateStrategy(user.getId());
            if (oldStrategy != null) {
                StrategyPhase s = findByStrategy(oldStrategy, 1);
                if (s == null) {
                    FirstStrategyPhase firstStrategyPhase = new FirstStrategyPhase();
                    firstStrategyPhase.setStrategyId( oldStrategy.getId());
                    map.put("firstStrategyPhase", firstStrategyPhase);
                } else {
                    s.setStrategyId(newStrategy.getId());
                    map.put("firstStrategyPhase", (FirstStrategyPhase) s);
                }
            }else {
                findByStrategy(newStrategy,1).setStrategyId(newStrategy.getId());
                map.put("firstStrategyPhase", (FirstStrategyPhase) findByStrategy(newStrategy,1));
            }
        } else {
            Strategy strategy = strategyRepository.findOne(id);
            strategy.setApplydeparts(new ArrayList<Organization>());
            strategyRepository.saveAndFlush(strategy);
            //TODO 用户验证
            isUser(strategy.getUid());
            strategy.setName(newStrategy.getName());
            strategy.setArea(newStrategy.getArea());
            strategy.setUpdateTime(new Date());
            strategy.setFirstProjectId(newStrategy.getFirstProjectId());
            String msg = validityPeriod(strategyBase.getApplydeparts());
            if (msg!=null && msg!=""){
                map.put("msg",msg);
                return map;
            }
            map.put("msg",null);
            strategy.setApplydeparts(newStrategy.getApplydeparts());
            // 添加策略修改日志
            StrategyLog strategyLog=new StrategyLog();
            strategyLog.setUser(userService.getUser());
            strategyLog.setContent(userService.getUser().getName()+"修改了该策略基本信息");
            strategyLog.setDate(new Date());
            strategyLog.setStrategyId(strategy.getId());
            strategyLog.setOperationType(StrategyLogOperationType.CREATED.getCode());
            Strategy strategy1=strategyRepository.saveAndFlush(strategy);
            if (strategy1==null){
                throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
            }
            strategy.getStrategyLogs().add(strategyLog);
            StrategyPhase s = findByStrategy(strategy, 1);
            // 返回首咨阶段
            if (s != null) {
                map.put("firstStrategyPhase", (FirstStrategyPhase) s);
            } else {
                Strategy oldstrategy = strategyRepository.getTopDateStrategy(strategy.getUid());
                StrategyPhase oldStrategyPhase = findByStrategy(strategy, 1);
                if (oldStrategyPhase != null) {
                    map.put("firstStrategyPhase", (FirstStrategyPhase) oldStrategyPhase);
                } else {
                    FirstStrategyPhase firstStrategyPhase = new FirstStrategyPhase();
                    firstStrategyPhase.setStrategyId(oldstrategy.getId());
                    map.put("firstStrategyPhase", firstStrategyPhase);
                }
            }
        }
        map.put("strategyId", newStrategy.getId());
        return map;
    }

    @Override
    public boolean addFirstStrategyPhase(FirstStrategyPhase firstStrategyPhase, Long oldStrategyId) {
        /* 添加策略首咨 */
        logger.info("添加策略id为" + firstStrategyPhase.getStrategyId() + "的首咨信息");
        Strategy newStrategy = strategyRepository.findOne(firstStrategyPhase.getStrategyId());
        //TODO 用户验证
        isUser(newStrategy.getUid());
        String str="";
        if (oldStrategyId==newStrategy.getId()){
            str="修改策略首咨信息";
        }else {
            str="添加策略首咨信息";
        }
        Set<SecondProject> secondProjects = firstStrategyPhase.getSecondProjects();
        for (SecondProject s: secondProjects ) {
            s.setType(getType(newStrategy));
        }
        // 添加策略日志
        StrategyLog strategyLog = strategyLogService.newStrategyLog(newStrategy.getUid(), str, StrategyLogOperationType.CREATED);
        newStrategy.getStrategyLogs().add(strategyLog);
        newStrategy.setUpdateTime(new Date());
        Set<StrategyPhase> strategyPhases = newStrategy.getStrategyPhases();
        firstStrategyPhase.setPhase(1);
        /* 如果当前策略对应首咨阶段不为空，则更新，如果为空添加 */
        if (!isHasStrategyPhase(strategyPhases, 1)) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 1);
            firstStrategyPhase.setFileName(strategyPhase.getFileName());
            firstStrategyPhase.setUrl(strategyPhase.getUrl());
            strategyPhases.remove(strategyPhase);
            strategyPhaseRepository.delete(strategyPhase);
        }
        strategyPhases.add(firstStrategyPhase);
        Strategy save=strategyRepository.saveAndFlush(newStrategy);
        if (save==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        return true;
    }

    @Override
    public boolean addSecStrategyPhase(SecStrategyPhase secStrategyPhase, Long oldStrategyId) {
        /* 添加策略回访 */
        logger.info("添加策略id为" + secStrategyPhase.getStrategyId() + "的回访信息");
        Strategy newStrategy = strategyRepository.findOne(secStrategyPhase.getStrategyId());
        //TODO 用户验证
        isUser(newStrategy.getUid());
        // 添加策略日志
        String str="";
        if (oldStrategyId==newStrategy.getId()){
            str="修改策略回访信息";
        }else {
            str="添加策略回访信息";
        }
        StrategyLog strategyLog = strategyLogService.newStrategyLog(newStrategy.getUid(), str, StrategyLogOperationType.CREATED);
        newStrategy.getStrategyLogs().add(strategyLog);
        secStrategyPhase.setPhase(2);
        newStrategy.setUpdateTime(new Date());
        Set<StrategyPhase> strategyPhases = newStrategy.getStrategyPhases();
        /* 如果当前策略对应回访阶段不为空，则更新，如果为空添加 */
        if (!isHasStrategyPhase(strategyPhases, 2)) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 2);
            secStrategyPhase.setFileName(strategyPhase.getFileName());
            secStrategyPhase.setUrl(strategyPhase.getUrl());
            strategyPhases.remove(strategyPhase);
            strategyPhaseRepository.delete(strategyPhase);
        }
        strategyPhases.add(secStrategyPhase);
        Strategy save=strategyRepository.saveAndFlush(newStrategy);
        if (save==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        return true;
    }

    @Override
    public boolean addFinalStrategyPhase(Long srategyId,FinalStrategyPhase finalStrategyPhase, String competitor, String compareContent, String comparePrice, String compareKill, String analyzeAdvantage) {
        /* 添加策略回访 */
        logger.info("添加策略id为" + finalStrategyPhase.getStrategyId() + "的库存信息");
        Strategy newStrategy = strategyRepository.findOne(finalStrategyPhase.getStrategyId());
        //TODO 用户验证
        isUser(newStrategy.getUid());
        Map<String, Object> map = new HashMap<>();
        // 添加策略日志
        String str="";
        if (srategyId==newStrategy.getId()){
            str="修改策略库存信息";
        }else {
            str="添加策略库存信息";
        }
        StrategyLog strategyLog = strategyLogService.newStrategyLog(newStrategy.getUid(), str, StrategyLogOperationType.CREATED);
        newStrategy.getStrategyLogs().add(strategyLog);
        finalStrategyPhase.setPhase(3);
        newStrategy.setUpdateTime(new Date());
        Set<StrategyPhase> strategyPhases = newStrategy.getStrategyPhases();
        /* 如果当前策略对应库存跨期阶段不为空，则更新，如果为空添加 */
        if (!isHasStrategyPhase(strategyPhases, 3)) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 3);
            finalStrategyPhase.setFileName(strategyPhase.getFileName());
            finalStrategyPhase.setUrl(strategyPhase.getUrl());
            strategyPhases.remove(strategyPhase);
            strategyPhaseRepository.delete(strategyPhase);
        }
        strategyPhases.add(finalStrategyPhase);
        // 设置策略竞争信息
        newStrategy.setCompetitor(competitor);
        newStrategy.setCompareContent(compareContent);
        newStrategy.setCompareKill(compareKill);
        newStrategy.setAnalyzeAdvantage(analyzeAdvantage);
        newStrategy.setComparePrice(comparePrice);
        // 保存提交策略
        newStrategy.setStatus(1);
        newStrategy.setSubmitDate(new Date());
        StrategyLog strategyLog1 = new StrategyLog();
        //TODO 有效期验证
        if (newStrategy.getSubmitNum()==null){
            newStrategy.setSubmitNum(1);
        }
        newStrategy.setSubmitNum(newStrategy.getSubmitNum()+1);
        Strategy save=strategyRepository.saveAndFlush(newStrategy);
        if (save==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean addStrategy(Long strategyId, List<AddStrategyPhaseModel> addStrategyPhaseModels) {
        /* 追加策略 */
        Strategy newStrategy = strategyRepository.findOne(strategyId);
        newStrategy.setUpdateTime(new Date());
        //TODO 策略是否能追加
//        validityAdd(newStrategy);
        isUser(newStrategy.getUid());
        Set<StrategyPhase> strategyPhases = newStrategy.getStrategyPhases();
        StrategyLog strategyLog = new StrategyLog();
        AdditionContent additionContent = new AdditionContent();
        additionContent.setStrategyId(strategyId);
        for (AddStrategyPhaseModel addStrategyPhaseModel : addStrategyPhaseModels) {
            AssertUtil.isNotNull(addStrategyPhaseModel.getType());
            ContentDetail contentDetail = new ContentDetail();
            int type = addStrategyPhaseModel.getType();
            String str = addStrategyPhaseModel.getContent();
            // 根据策略阶段id和追加类型匹配,追加策略详情
            for (StrategyPhase s : strategyPhases) {
                long id = s.getId();
                long strategyPhaseId = addStrategyPhaseModel.getStrategyPhaseId();
                if (id==strategyPhaseId) {
                    //  0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具
                    switch (type) {
                        case 0:
                            FirstStrategyPhase firstStrategyPhase = (FirstStrategyPhase) s;
                            logger.info("追加策略id为" + s.getStrategyId() + "的主推项目");
                            SecondProject secondProject = new SecondProject();
                            secondProject.setNumber(addStrategyPhaseModel.getNumber());
                            secondProject.setDescription(addStrategyPhaseModel.getContent());
                            secondProject.setFirstProjectName("其他");
                            secondProject.setType("主推项目");
                            firstStrategyPhase.getSecondProjects().add(secondProject);
                            break;
                        case 1:
                            logger.info("追加策略id为" + s.getStrategyId() + "的探需问题");
                            Need need = new Need();
                            need.setNumber(addStrategyPhaseModel.getNumber());
                            need.setNeedName(addStrategyPhaseModel.getContent());
                            s.getNeeds().add(need);
                            break;
                        case 2:
                            logger.info("追加策略id为" + s.getStrategyId() + "的主推专业");
                            FirstStrategyPhase firstStrategyPhase1 = (FirstStrategyPhase) s;
                            SecondProject secondProject1 = new SecondProject();
                            secondProject1.setNumber(addStrategyPhaseModel.getNumber());
                            secondProject1.setDescription(addStrategyPhaseModel.getContent());
                            secondProject1.setFirstProjectName("自考");
                            secondProject1.setType("主推专业");
                            firstStrategyPhase1.getSecondProjects().add(secondProject1);
                            break;
                        case 3:
                            logger.info("追加策略id为" + s.getStrategyId() + "的主推院校");
                            FirstStrategyPhase firstStrategyPhase2 = (FirstStrategyPhase) s;
                            SecondProject secondProject2 = new SecondProject();
                            secondProject2.setNumber(addStrategyPhaseModel.getNumber());
                            secondProject2.setDescription(addStrategyPhaseModel.getContent());
                            secondProject2.setFirstProjectName("自考");
                            secondProject2.setType("主推专业");
                            firstStrategyPhase2.getSecondProjects().add(secondProject2);
                            break;
                        case 4:
                            logger.info("追加策略id为" + s.getStrategyId() + "的主推班型");
                            ClassSize classSize = new ClassSize();
                            classSize.setNumber(addStrategyPhaseModel.getNumber());
                            classSize.setDescription(addStrategyPhaseModel.getContent());
                            s.getMainClassSizes().add(classSize);
                            break;
                        case 5:
                            logger.info("追加策略id为" + s.getStrategyId() + "的截杀策略/优惠活动");
                            KillStrategy killStrategy = new KillStrategy();
                            killStrategy.setNumber(addStrategyPhaseModel.getNumber());
                            killStrategy.setDescription(addStrategyPhaseModel.getContent());
                            s.getKillStrategies().add(killStrategy);
                            break;
                        case 6:
                            logger.info("追加策略id为" + s.getStrategyId() + "的回访阶段深入探需,解决首咨遗留问题");
                            SecStrategyPhase secStrategyPhase = (SecStrategyPhase) s;
                            SolveFirstProblem solveFirstProblem = new SolveFirstProblem();
                            solveFirstProblem.setNumber(addStrategyPhaseModel.getNumber());
                            solveFirstProblem.setSolve(addStrategyPhaseModel.getContent());
                            secStrategyPhase.getSolveFirstProblems().add(solveFirstProblem);
                            break;
                        case 7:
                            logger.info("追加策略id为" + s.getStrategyId() + "的库存跨期触发式开场");
                            FinalStrategyPhase finalStrategyPhase = (FinalStrategyPhase) s;
                            TriggerOpen triggerOpen = new TriggerOpen();
                            triggerOpen.setNumber(addStrategyPhaseModel.getNumber());
                            triggerOpen.setTriggerOpen(addStrategyPhaseModel.getContent());
                            finalStrategyPhase.getTriggerOpens().add(triggerOpen);
                            break;
                        case 8:
                            logger.info("追加策略id为" + s.getStrategyId() + "的库存跨期辅助咨询工具");
                            FinalStrategyPhase finalStrategyPhase1 = (FinalStrategyPhase) s;
                            AssistTool assistTool = new AssistTool();
                            assistTool.setNumber(addStrategyPhaseModel.getNumber());
                            assistTool.setTool(addStrategyPhaseModel.getContent());
                            finalStrategyPhase1.getAssistTools().add(assistTool);
                            break;
                    }
                    // 设置追加细节内容
                    contentDetail.setTypeName(ScoreTypeEnums.values()[type].getName());
                    strategyLog.setContent("追加" + ScoreTypeEnums.values()[type].getName());
                    StrategyPhase strategyPhase=strategyPhaseRepository.saveAndFlush(s);
                    if (strategyPhase==null){
                        throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
                    }
                    contentDetail.setStrategyPhaseId(s.getId());
                }
            }
            contentDetail.setType(type);
            contentDetail.setContent(str);
            additionContent.getContentDetails().add(contentDetail);
        }
        User u = userRepository.findOne(newStrategy.getUid());
        //  添加追加日志
        strategyLog.setDate(new Date());
        strategyLog.setOperationType(StrategyLogOperationType.ADDED.getCode());
        strategyLog.setUser(u);
        newStrategy.getStrategyLogs().add(strategyLog);
        newStrategy.setStatus(AuditStatus.UN_AUDIT.getCode());
        newStrategy.setSubmitDate(new Date());
        //  设置追加基本信息
        additionContent.setDate(new Date());
        additionContent.setUid(newStrategy.getUid());
        additionContent.setUsername(u.getName());
        additionContent.setStatus(AuditStatus.UN_AUDIT.getCode());
        // 保存追加信息
        AdditionContent additionContent1=additionContentRepository.save(additionContent);
        if (additionContent1==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        if (newStrategy.getSubmitNum()==null){
            newStrategy.setSubmitNum(1);
        }
        newStrategy.setSubmitNum(newStrategy.getSubmitNum()+1);
        // 更新策略
        Strategy save=strategyRepository.saveAndFlush(newStrategy);
        if (save==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }else {
            BoutiqueStrategy boutiqueStrategy=boutiqueStrategyRepository.findOne(save.getId());
            if (boutiqueStrategy!=null){
                boutiqueStrategy.setStatus(AuditStatus.UN_AUDIT.getCode());
                BoutiqueStrategy boutiqueStrategy1 = boutiqueStrategyRepository.saveAndFlush(boutiqueStrategy);
                if (boutiqueStrategy1==null){
                    throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
                }
            }
        }
        return true;
    }

    @Override
    public boolean addAdvice(Advise advise) {
        /* 追加策略建议 */
        User u = userService.getUser();
        logger.info("用户" + u.getUsername() + "为策略：" + advise.getStrategyId() + "添加建议");
        advise.setUid(u.getId());
        Set<Role> roles = u.getRoles();
        advise.setType(0);
        for (Role role : roles) {
            if (role.getId() == 1000) {
                advise.setType(1);
            }
        }
        // 保存策略建议并添加策略日志
        advise.setDate(new Date());
        adviseRepository.save(advise);
        StrategyLog strategyLog = new StrategyLog();
        strategyLog.setUser(u);
        strategyLog.setDate(new Date());
        strategyLog.setStrategyId(advise.getStrategyId());
        strategyLog.setContent(u.getUsername() + "为此策略添加建议");
        Strategy strategy = strategyRepository.findOne(advise.getStrategyId());
        strategy.getStrategyLogs().add(strategyLog);
        Strategy save=strategyRepository.saveAndFlush(strategy);
        if (save==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        return true;
    }

    @Override
    public Map<String, Object> getStrategy(Long strategyId) {
        /* 获取策略内容 */
        Map<String, Object> map = new HashMap<>();
        Strategy strategy = strategyRepository.findOne(strategyId);
        User user = userRepository.findOne(strategy.getUid());
        Organization organization = user.getOrganization().getParent();
        User user1=userService.getUser();
        Organization organization1 = user1.getOrganization();
        if (organization1.getId()==organization.getId()){
            StrategyLog strategyLog = strategyLogService.newStrategyLog(user1.getId(), user1.getName() + "已查看过该策略", StrategyLogOperationType.READ);
            strategy.getStrategyLogs().add(strategyLog);
            Strategy strategy1 = strategyRepository.saveAndFlush(strategy);
            if (strategy1==null){
                throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
            }
        }
        StrategyReturn strategyReturn = new StrategyReturn();
        Analyze analysis= analysisRepository.findByStrategyId(strategyId);
        if (analysis==null){
            strategyReturn.setAnalyze(0);
        }else {
            strategyReturn.setAnalyze(1);
        }
        BeanUtils.copyProperties(strategy, strategyReturn);
        strategyReturn.setApplydeparts(getOrgTree(strategy.getApplydeparts()));
        strategyReturn.setFirstProjectName(ProjectEnums.values()[strategy.getFirstProjectId()].getName());
        strategyReturn.setAreaName(AreaEnums.values()[strategy.getArea()].getName());
        strategyReturn.setProjectType(getType(strategy));
        map.put("strategy", strategyReturn);
        // 获取策略被追加的次数以及被批注的次数
        List<AdditionContent> additionContents = additionContentRepository.findByStrategyId(strategyId);
        List<Advise> advises = adviseRepository.findByStrategyIdAndTypeOrderByDateDesc(strategyId, 1);
        if (additionContents != null) {
            strategyReturn.setAdditionNum(additionContents.size());
        } else {
            strategyReturn.setAdditionNum(0);
        }
        if (advises != null) {
            strategyReturn.setPostilNum(advises.size());
        } else {
            strategyReturn.setPostilNum(0);
        }
        strategyReturn.setBoutiqueNum(strategyRepository.selectBoutiqueStrategyNum(strategyId));
        //  获取策略阶段内容
        FirstStrategyPhase firstStrategyPhase = (FirstStrategyPhase) findByStrategy(strategy, 1);
        SecStrategyPhase secStrategyPhase = (SecStrategyPhase) findByStrategy(strategy, 2);
        FinalStrategyPhase finalStrategyPhase = (FinalStrategyPhase) findByStrategy(strategy, 3);
        map.put("firstStrategyPhase", firstStrategyPhase);
        map.put("secStrategyPhase", secStrategyPhase);
        map.put("finalStrategyPhase", finalStrategyPhase);
        return map;
    }

    @Override
    public CommonPage queryStrategy(SelectStrategyCondition selectStrategyCondition) {
        /* 获取策略列表 */
        User user = userService.getUser();
        Set<Role> roles = user.getRoles();
        boolean roleflag=true;
        for (Role role:roles){
            if (role.getName().equals(RoleEnums.DSOP.getName())||role.getName().equals(RoleEnums.SOP.getName())||role.getName().equals(RoleEnums.SOPS.getName())){
                roleflag=false;
            }
        }
        List<Organization> organizationList=new ArrayList<>();
        if (roleflag){
            Integer[] org= selectStrategyCondition.getOrgs();
            if (org==null||org.length<=0){
                List<Integer> orgs = organizationService.getDowmOrg(user.getOrganization().getId());
                for (Integer integer:orgs){
                    Organization organization=new Organization();
                    organization.setId(integer);
                    organizationList.add(organization);
                }
            }else {
                for (int i=0;i<org.length;i++){
                    Organization organization=new Organization();
                    organization.setId(org[i]);
                    organizationList.add(organization);
                }
            }
        }else {
            Integer[] org= selectStrategyCondition.getOrgs();
            List<Organization> organizations = selectStrategyCondition.getOrganizations();
            if (org==null||org.length<=0) {
                Set<Organization> mngOrganizations = user.getMngOrganizations();
                for (Organization organization1:mngOrganizations) {
                    List<Integer> orgs = organizationService.getDowmOrg(organization1.getId());
                    for (Integer integer:orgs){
                        Organization organization=new Organization();
                        organization.setId(integer);
                        organizationList.add(organization);
                    }
                }
            }else {
                for (int i = 0; i < org.length; i++) {
                    Organization organization = new Organization();
                    organization.setId(org[i]);
                    organizationList.add(organization);
                }
            }
        }
        selectStrategyCondition.setOrganizations(organizationList);
        CommonPage page = strategyRepository.queryStrategys(selectStrategyCondition);
        List<Object> objects = page.getContent();
        List<Object> queryPageStrategys = new ArrayList<Object>();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Object id : objects) {
            Strategy strategy = strategyRepository.findOne((Long) id);
            QueryPageStrategy queryPageStrategy = new QueryPageStrategy();
            queryPageStrategy.setId(strategy.getId());
            queryPageStrategy.setStatus(strategy.getStatus());
            queryPageStrategy.setArea(AreaEnums.values()[strategy.getArea()].getName());
            queryPageStrategy.setCreateDate(strategy.getUpdateTime());
            queryPageStrategy.setGroupName(strategy.getGroupName());
            queryPageStrategy.setName(strategy.getName());
            if (strategy.getUid() == user.getId()) {
                queryPageStrategy.setOwn(1);
            } else {
                queryPageStrategy.setOwn(0);
            }
            StrategyLog strategyLog ;
            if (user.getOrganization().getParent()==null){
                strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(strategy.getId(), user.getId(), StrategyLogOperationType.READ);
            }else {
                if (user.getOrganization().getParent().getOrganizationLevel()== OrganizationLevel.BUSINESS_UNIT){
                    strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(strategy.getId(), userRepository.selectMaxRoleById(RoleEnums.DIVISION.getName(), user.getOrganization().getParent().getId()), StrategyLogOperationType.READ);
                }else if (user.getOrganization().getParent().getParent().getOrganizationLevel()== OrganizationLevel.BUSINESS_UNIT){
                    strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(strategy.getId(), userRepository.selectMaxRoleById(RoleEnums.DIVISION.getName(), user.getOrganization().getParent().getParent().getId()), StrategyLogOperationType.READ);
                }else if (user.getOrganization().getParent().getParent().getParent().getOrganizationLevel()== OrganizationLevel.BUSINESS_UNIT){
                    strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(strategy.getId(), userRepository.selectMaxRoleById(RoleEnums.DIVISION.getName(), user.getOrganization().getParent().getParent().getParent().getId()), StrategyLogOperationType.READ);
                }else {
                    strategyLog=null;
                }
            }
             if (strategyLog == null) {
                queryPageStrategy.setReadStatus(0);
            } else if (strategyLog != null) {
                boolean flag=strategyLog.getDate().after(strategy.getSubmitDate());
                if (flag){
                    queryPageStrategy.setReadStatus(1);
                }else {
                    queryPageStrategy.setReadStatus(0);
                }
            } else {
                queryPageStrategy.setReadStatus(0);
            }
            List<AdditionContent> additionContents = additionContentRepository.findByStrategyId(strategy.getId());
            if (additionContents.size() > 0) {
                queryPageStrategy.setIsAdd("追加");
            }
            queryPageStrategy.setFirstProjectName(ProjectEnums.values()[strategy.getFirstProjectId()].getName());
            List<Organization> applydepart = strategy.getApplydeparts();
            List<Organization> organizations=getOrgTree(applydepart);
            queryPageStrategy.setApplydeparts(organizations);
            queryPageStrategys.add(queryPageStrategy);
        }
        page.setContent(queryPageStrategys);
        page.setTotalPages();
        return page;
    }

    @Override
    public boolean updateStrategyStatus(Long id, AuditStatus auditStatus,String logContent) {
        AssertUtil.isNotNull(id);
        AssertUtil.isNotNull(auditStatus);
        User user=userService.getUser();
        Set<Organization> mngOrganizations = user.getMngOrganizations();
        boolean flag=false;
        Strategy one = strategyRepository.findOne(id);
        User user1=userRepository.findOne(one.getUid());
        if (mngOrganizations.contains(user1.getOrganization())||mngOrganizations.contains(user1.getOrganization().getParent())){
            flag=true;
        }
        if (!flag){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        StrategyLog strategyLog = strategyLogService.queryByStrategyIdAndUidAndType(one.getId(), user.getId(), StrategyLogOperationType.AUDIT);
        List<AdditionContent> additionContents =new ArrayList<>();
        //判断策略是否是首次审核还是追加审核
        if (strategyLog!=null){
            additionContents = additionContentRepository.findByStrategyIdAndDateAfter(id, one.getUpdateTime());
        }
        AssertUtil.isNotNull(one, "找不到相关策略");
        if (Objects.equals(one.getStatus(), auditStatus.getCode()))
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED, "状态已变更");
        one.setStatus(auditStatus.getCode());
        for (AdditionContent a : additionContents
                ) {
            a.setStatus(auditStatus.getCode());
            additionContentRepository.saveAndFlush(a);
        }
        StrategyLog strategyLog1=strategyLogService.newStrategyLog(userService.getUser().getId(), "修改状态为:" + auditStatus.name(), StrategyLogOperationType.AUDIT);
        strategyLog1.setLogContent(logContent);
        one.getStrategyLogs().add(strategyLog1);
        Strategy strategy=strategyRepository.saveAndFlush(one);
        if (strategy==null){
            throw new UserDefinedException(ServerStatus.OPERATION_FAILED);
        }
        return true;
    }

    @Override
    public Map<String, Object> getStrategyPhase(Long strategyId, Long newstrategyId, Integer type) {
        Strategy newStrategy = strategyRepository.findOne(newstrategyId);
        Strategy oldStrategy = strategyRepository.findOne(strategyId);
        Map<String, Object> map = new HashMap<>();
        if (type == 1) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 1);
            if (strategyPhase == null||(strategyPhase!=null&&strategyPhase.getNeeds().size()<=0)) {
                StrategyPhase oldStrategyPhase = findByStrategy(oldStrategy, 1);
                if (oldStrategyPhase == null||(oldStrategyPhase!=null&&oldStrategyPhase.getNeeds().size()<=0)) {
                    Strategy strategy = strategyRepository.getTopDateStrategy(newStrategy.getUid());
                    if (strategy==null){
                        map.put("type", getType(newStrategy));
                    }else{
                        oldStrategyPhase = findByStrategy(strategy, 1);
                        oldStrategy = strategy;
                    }
                }
                oldStrategyPhase.setId(strategyPhase.getId());
                strategyPhase = oldStrategyPhase;
                map.put("type", getType(oldStrategy));
            }else {
                map.put("type", getType(newStrategy));
            }
            strategyPhase.setStrategyId(strategyId);
            map.put("firstStrategyPhase", (FirstStrategyPhase) strategyPhase);
        } else if (type == 2) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 2);
            if (strategyPhase == null||(strategyPhase!=null&&strategyPhase.getNeeds().size()<=0)) {
                StrategyPhase oldStrategyPhase = findByStrategy(oldStrategy, 2);
                if (oldStrategyPhase == null||(oldStrategyPhase!=null&&oldStrategyPhase.getNeeds().size()<=0)) {
                    Strategy strategy = strategyRepository.getTopDateStrategy(newStrategy.getUid());
                    if (strategy!=null){
                        oldStrategyPhase = findByStrategy(strategy, 2);
                        oldStrategy = strategy;
                    }
                }
                oldStrategyPhase.setId(strategyPhase.getId());
                strategyPhase = oldStrategyPhase;
            }
            strategyPhase.setStrategyId(strategyId);
            map.put("secStrategyPhase", (SecStrategyPhase) strategyPhase);
        } else if (type == 3) {
            StrategyPhase strategyPhase = findByStrategy(newStrategy, 3);

            if (strategyPhase == null||(strategyPhase!=null&&strategyPhase.getNeeds().size()<=0)) {
                StrategyPhase oldStrategyPhase = findByStrategy(oldStrategy, 3);
                if (oldStrategyPhase == null||(oldStrategyPhase!=null&&oldStrategyPhase.getNeeds().size()<=0)) {
                    Strategy strategy = strategyRepository.getTopDateStrategy(newStrategy.getUid());
                    if (strategy!=null){
                        oldStrategyPhase = findByStrategy(strategy, 3);
                        oldStrategy = strategy;
                    }
                }
                oldStrategyPhase.setId(strategyPhase.getId());
                strategyPhase = oldStrategyPhase;
                map.put("strategy", oldStrategy);
            }else {
                map.put("strategy", newStrategy);
            }
            strategyPhase.setStrategyId(strategyId);
            map.put("finalStrategyPhase", (FinalStrategyPhase) strategyPhase);
        }
        map.put("strategyId", newstrategyId);
        return map;
    }

    @Override
    public ServerStatus downloadStrategy(Long strategyId, HttpServletResponse response, HttpServletRequest request) throws IOException {
        Strategy strategy = strategyRepository.findOne(strategyId);
        ExcelUtil.downloadStrategyExcel(strategy, response, request);
        return ServerStatus.SUCCESS;
    }

    @Override
    public ServerStatus batchDownloadStrategy(long[] strategyIds,HttpServletResponse response,HttpServletRequest request) throws IOException {
        HSSFWorkbook[] workbooks=new HSSFWorkbook[strategyIds.length];
        String[] fileNames=new String[strategyIds.length];
        List<Strategy> strategies=new ArrayList<>();
        a:for (int i=0;i<strategyIds.length;i++){
            Strategy one = strategyRepository.findOne(strategyIds[i]);
            if (one==null){
                continue a;
            }
            strategies.add(one);
        }
        for (int j=0;j<strategies.size();j++){
            List<Organization> orgTree = getOrgTree(strategies.get(j).getApplydeparts());
            strategies.get(j).setApplydeparts(orgTree);
            HSSFWorkbook workbook = ExcelPOI.POIWorkBook(strategies.get(j));
            workbooks[j]=workbook;
            String nameDate= DateUtil.dateToStringDetailTime(new Date());
            String filename=strategies.get(j).getGroupName()+strategies.get(j).getName()+nameDate+".xls";
            fileNames[j]=filename;
        }
        ExcelUtil.batchExportExcel("精品策略.zip",workbooks,fileNames,response,request);
        return ServerStatus.SUCCESS;
    }

    @Override
    public Map<String,Object> getStrategyAddDetails(Long strategyId, Integer number) {
        /* 获取策略追加列表 */
        List<AdditionContent> additionContents = new ArrayList<AdditionContent>();
        Map<String, Object> map = new HashMap<>();
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = new PageRequest(0, number, sort);
        Page<AdditionContent> page = additionContentRepository.findAllByStrategyId(strategyId, pageable);
        additionContents = page.getContent();
        map.put("totalElements",page.getTotalElements());
        map.put("content",additionContents);
        return map;
    }

    @Override
    public Map<String,Object> getStrategyLogDetails(Long strategyId, Integer number, Integer type) {
        /* 获取策略操作记录、批注、备注 */
        Map<String, Object> map = new HashMap<>();
        Strategy strategy = strategyRepository.findOne(strategyId);
        User user = userService.getUser();
        List<Log> logs = new ArrayList<Log>();
        Sort sort = new Sort(Sort.Direction.DESC, "date");
        Pageable pageable = new PageRequest(0, number, sort);
        //根据所需类型查询对应的操作记录、批注、备注
        if (type == 0) {
            Page<StrategyLog> page = strategyLogRepository.findAllByStrategyId(strategyId, pageable);
            map.put("totalElements",page.getTotalElements());
            List<StrategyLog> strategyLogs = page.getContent();
            if (strategyLogs!=null && strategyLogs.size()>0){
                for (StrategyLog strategyLog : strategyLogs) {
                    Log log = new Log(strategyLog.getId(), strategyLog.getUser().getName(), strategyLog.getContent(),strategyLog.getDate());
                    logs.add(log);
                }
            }
        } else if (type>= 1) {
            Page<Advise> page = adviseRepository.findAllByStrategyIdAndType(strategyId, type-1, pageable);
            map.put("totalElements",page.getTotalElements());
            List<Advise> advises = page.getContent();
            for (Advise advise : advises) {
                Log log = new Log(advise.getId(), userRepository.findOne(advise.getUid()).getName(), advise.getContent(),advise.getDate());
                logs.add(log);
            }
        }
        map.put("content",logs);
        return map;
    }


    /**
     * 判断策略创建用户和当前用户是否是同一人
     *
     * @param uid
     * @return
     */
    public void isUser(Integer uid) {
        User user = userService.getUser();
        if (!uid.equals(user.getId())){
            throw new UserDefinedException(ServerStatus.FORBIDDEN);
        }
    }

    /**
     * 判断策略阶段列表中是否存在某一策略阶段
     *
     * @param strategyPhases
     * @param num
     * @return
     */
    public boolean isHasStrategyPhase(Set<StrategyPhase> strategyPhases, int num) {
        boolean flag = true;
        if (strategyPhases != null) {
            for (StrategyPhase s : strategyPhases) {
                if (s.getPhase() == num) {
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * 获取指定的策略阶段
     *
     * @param strategy
     * @param num
     * @return
     */
    public StrategyPhase findByStrategy(Strategy strategy, int num) {
        /*  获取策略阶段集合 */
        Set<StrategyPhase> strategyPhases = strategy.getStrategyPhases();
        /* 根据指定阶段返回策略阶段 */
        if (strategyPhases != null) {
            for (StrategyPhase s : strategyPhases) {
                if (s.getPhase() == num) {
                    return s;
                }
            }
        }
        return null;
    }

    /**
     * 如果创建策略时有的部门已经创建了，返回已创建过策略的部门集合
     *
     * @param organizations
     * @return
     */
    public String validityPeriod(List<Organization> organizations) {
        List<Organization> organizations1 = new ArrayList<>();
        for (Organization organization : organizations) {
            long[] ids = strategyRepository.queryStrategies(organization.getId());
            List<Strategy> strategies = strategyRepository.findAllByStartTimeAfterAndIdIn(new Date(), ids);
            if (strategies != null && strategies.size() > 0) {
                organization=orgInfoRepository.findOne(organization.getId());
                organizations1.add(organization);
            }
        }
        String msg = "";
        if (organizations1 != null && organizations1.size() > 0) {
            List<Organization> organizations2=getOrgTree(organizations1);
            //TODO 抛异常
            for (Organization organization:organizations2){
                msg=msg+organization.getName()+":[";
                for (Organization organization1:organization.getItem()){
                    msg=msg+organization1.getName()+";";
                }
                msg=msg+"]\n";
            }
            msg = msg + "下周已有待实施策略，不能创建！";
        }
        return msg;
    }

    public List<Organization> getOrgTree(Collection<Organization> organizations){
        List<Organization> organizations2=new ArrayList<>();
        for (Organization organization:organizations){
            Organization org = orgInfoRepository.findOne(organization.getParent().getId());
            if (organizations2.contains(org)){
                org.getItem().add(organization);
            }else {
                org.setItem(new HashSet<Organization>());
                org.getItem().add(organization);
                organizations2.add(org);
            }
        }
        for (Organization organization:organizations2){
            List<Organization> organ = orgInfoRepository.findByParentIdOrderByCode(organization.getId());
            if (organ.size()==organization.getItem().size()){
                organization.setItem(new HashSet<>());
            }
        }
        List<Organization> organizations3=new ArrayList<>();
        for (Organization organization:organizations2){
            Organization organization1=new Organization();
            BeanUtils.copyProperties(organization,organization1);
            organizations3.add(organization1);

        }
        return organizations3;
    }

    /**
     * 判断策略是否能追加
     *
     * @return
     */
    public void validityAdd(Strategy strategy) {
        List<Organization> organizations1 = new ArrayList<>();
        for (Organization organization : strategy.getApplydeparts()) {
            long[] ids = strategyRepository.queryStrategies(organization.getId());
            List<Strategy> strategies = strategyRepository.findAllByStartTimeAfterAndIdIn(strategy.getStartTime(), ids);
            if (strategies == null || strategies.size() <= 0) {
                organizations1.add(organization);
            }
        }
        if (strategy.getStatus() <= 0) {
            throw new UserDefinedException(ServerStatus.INFO_EXCEPTION, "该策略处于新建未提交状态，不能追加");
        } else if (organizations1.size() <= 0) {
            throw new UserDefinedException(ServerStatus.INFO_EXCEPTION, "该策略已经过期，无法追加");
        }
    }

    /**
     * 获取下周一零点时间
     *
     * @return
     */
    public Date lastMonday() {
        Calendar calendar = Calendar.getInstance();
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
        } else {
            while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                calendar.add(Calendar.DAY_OF_WEEK, 1);
            }
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int offset = 1 - dayOfWeek;
            calendar.add(Calendar.DATE, offset + 7);
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public String getType(Strategy strategy) {
        if (strategy.getFirstProjectId() == 0) {
            return "主推专业";
        } else if (strategy.getFirstProjectId() == 1) {
            return "主推项目";
        } else {
            return "主推院校";
        }
    }

    @Override
    public Strategy getNewStrategy(UserInfo userInfo) {
        Organization organization = userInfo.getOrganization();
        long[] longs = strategyRepository.queryStrategies(organization.getId());
        return strategyRepository.findByStatusAndIdInAndStartTimeBeforeOrderByStartTimeDesc(2,longs,new Date());
    }

    @Override
    public boolean upload(MultipartFile file, Long strategyPhaseId) throws Exception {
        String osName = System.getProperty("os.name");
        String storedir = null;
        String separator = "";
        if (osName == null)
            osName = "";
        if (osName.toLowerCase().indexOf("win") != -1) {
            separator = "\\";
            if (storedir == null || storedir.equals(""))
                storedir = "d:\\tmp";
        } else {
            separator = "/";
            storedir = "/tmp";
        }
        FileInfo upload = FileUtil.upload(file, storedir);
        StrategyPhase one = strategyPhaseRepository.findOne(strategyPhaseId);
        if (one==null){
            return false;
        }
        one.setUrl(upload.getPath());
        one.setFileName(file.getOriginalFilename());
        strategyPhaseRepository.saveAndFlush(one);
        return true;
    }

    @Override
    public boolean downloadStrategyVerbalTrick(Long strategyPhaseId, HttpServletResponse response, HttpServletRequest request) throws Exception {
        StrategyPhase strategyPhase = strategyPhaseRepository.findById(strategyPhaseId);
        FileUtil.download(strategyPhase.getUrl(),strategyPhase.getFileName(),request,response);
        return true;
    }
}
