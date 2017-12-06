package com.sunlands.feo.domain.event;

import com.sunlands.feo.application.OrganizationService;
import com.sunlands.feo.application.UserService;
import com.sunlands.feo.application.message.MessageSerevice;
import com.sunlands.feo.common.DateUtil;
import com.sunlands.feo.common.StrUtil;
import com.sunlands.feo.domain.model.boutique.BoutiqueRecord;
import com.sunlands.feo.domain.model.boutique.BoutiqueStrategy;
import com.sunlands.feo.domain.model.enums.OrganizationLevel;
import com.sunlands.feo.domain.model.enums.message.ContentTypeEnums;
import com.sunlands.feo.domain.model.enums.message.SendTypeEnums;
import com.sunlands.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.sunlands.feo.domain.model.enums.user.RoleEnums;
import com.sunlands.feo.domain.model.message.MessageTemplate;
import com.sunlands.feo.domain.model.record.Record;
import com.sunlands.feo.domain.model.reportform.Analyze;
import com.sunlands.feo.domain.model.strategy.Advise;
import com.sunlands.feo.domain.model.strategy.Strategy;
import com.sunlands.feo.domain.model.strategy.StrategyLog;
import com.sunlands.feo.domain.model.user.User;
import com.sunlands.feo.domain.repository.orgInfo.OrgInfoRepository;
import com.sunlands.feo.domain.repository.record.RecordRepository;
import com.sunlands.feo.domain.repository.strategy.StrategyLogRepository;
import com.sunlands.feo.domain.repository.strategy.StrategyRepository;
import com.sunlands.feo.domain.repository.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.*;

/**
 * 事件实现层
 * Created by huang on 2017/11/9.
 */
@Component
public class EventCommonProcessor {

    //事件日志
    private static final Logger logger = LoggerFactory.getLogger(EventCommonProcessor.class);

    //消息Serevice
    private MessageSerevice messageSerevice;

    //组织结构Service
    private OrganizationService organizationService;

    //组织结构Repository
    private OrgInfoRepository orgInfoRepository;

    //用户Repository
    private UserRepository userRepository;

    //策略Repository
    private StrategyRepository strategyRepository;

    //质检Repository
    private RecordRepository recordRepository;

    //用户Service
    private UserService userService;

    //策略日志Repository
    private StrategyLogRepository strategyLogRepository;

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(Strategy event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        //策略审核状态 0-新建,1-待审核,2-审核通过,3-驳回,
        Integer _status = event.get_status();
        Integer status = event.getStatus();
        //策略发布,追加重新发布待审核
        if ((_status == 0 && status == 1) || (_status == 3 && status == 1) || (_status == 1 && status == 1)) {
            //推送人&接收人
            User user = userRepository.findOne(event.getUid());
            Integer[] userIds = userRepository.selectUserIdsByRole(RoleEnums.SOPS.getName());
            //推送消息模板
            String subject = MessageTemplate.WAIT_APPROVE(event.getGroupName(), event.getName());
            messageSerevice.pushMessage(subject, "/strategies/" + event.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);
        }
        //策略审核通过
        if (_status == 1 && status == 2) {
            //审批通过人
            List<StrategyLog> StrategyLogs = strategyLogRepository.findAllByStrategyId(event.getId(), new Sort(Sort.Direction.DESC, "date"));
            User user = new User();
            for (StrategyLog strategyLog : StrategyLogs) {
                if (strategyLog.getOperationType().equals(StrategyLogOperationType.AUDIT.getCode())) {
                    user = strategyLog.getUser();
                    break;
                }
            }
            //推送消息模板
            String subject = MessageTemplate.FINISH_APPROVE(event.getName(), user.getUsername());
            //接收人（创建者本人，销售部经理，事业部负责人，集团sop，事业部sop,不推审批人）
            List<Integer> passUserIds = new ArrayList<>();
            passUserIds.add(user.getId());
            Integer[] userIds = getPushUserIdUtil(true, true, true, true, event.getUid(), event.getUid(), getLeaderUtil(event.getUid()), passUserIds);
            messageSerevice.pushMessage(subject, "/strategies/" + event.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);
        }
        //策略审核驳回
        if (_status == 1 && status == 3) {
            //审批驳回人
            List<StrategyLog> StrategyLogs = strategyLogRepository.findAllByStrategyId(event.getId(), new Sort(Sort.Direction.DESC, "date"));
            User user = new User();
            String content = "";
            for (StrategyLog strategyLog : StrategyLogs) {
                if (strategyLog.getOperationType().equals(StrategyLogOperationType.AUDIT.getCode())) {
                    user = strategyLog.getUser();
                    content = strategyLog.getContent();
                    break;
                }
            }
            //推送消息模板
            content = content == null ? "未批注" : content;
            String subject = MessageTemplate.NO_APPROVE(event.getName(), user.getUsername(), content);
            //推送人
            Integer[] userIds = {getLeaderUtil(event.getUid()).getId(), event.getUid()};
            messageSerevice.pushMessage(subject, "/strategies/" + event.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);
        }
        logger.info("status :{} \t ->{}", event.get_status(), event.getStatus());
    }

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(StrategyLog event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        //策略追加
        if (event.getOperationType().equals(StrategyLogOperationType.ADDED.getCode())) {
            //推送人
            Strategy strategy = strategyRepository.findOne(event.getStrategyId());
            User user = userRepository.findOne(strategy.getUid());
            String subject = MessageTemplate.ADD_TO(strategy.getGroupName(), user.getUsername());
            Integer[] userIds = new Integer[]{getLeaderUtil(strategy.getUid()).getId()};
            messageSerevice.pushMessage(subject, "/strategies/" + strategy.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);
        }
    }

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(Advise event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        //已备注（其他人）
        if (event.getType() == 0) {
            Strategy strategy = strategyRepository.findOne(event.getStrategyId());
            //推送人（创建者本人，事业部老大，sops负责人，集团sop，事业部sop,不推送）
            List<Integer> passUserIds = new ArrayList<>();
            passUserIds.add(event.getUid());
            User leaderUtil = getLeaderUtil(strategy.getUid());
            Integer[] userIds = getPushUserIdUtil(false, true, false, false, strategy.getUid(), strategy.getUid(), leaderUtil, passUserIds);
            //推送消息模板
            User one = userRepository.findOne(event.getUid());
            String subject = MessageTemplate.COMMENT(strategy.getName(), one.getUsername(), event.getContent());
            messageSerevice.pushMessage(subject, "/strategies/" + event.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, one, userIds);
        }
        //已批注(事业部负责人)
        if (event.getType() == 1) {
            Strategy strategy = strategyRepository.findOne(event.getStrategyId());
            //推送人（创建者本人，sops负责人，集团sop，事业部sop）
            User user = userRepository.findOne(event.getUid());
            Integer[] userIds = getPushUserIdUtil(false, true, true, true, strategy.getUid(), strategy.getUid(), user, null);
            //推送消息模板
            String subject = MessageTemplate.POSTIL(strategy.getName(), user.getUsername(), event.getContent());
            messageSerevice.pushMessage(subject, "/strategies/" + event.getId(), ContentTypeEnums.GROUP_DYNAMIC.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);
        }
    }

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(Analyze event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        //推送人
        Strategy strategy = strategyRepository.findOne(event.getStrategyId());
        User one = userRepository.findOne(strategy.getUid());
        List<Integer> groupOrgIds = organizationService.getDowmOrg(one.getOrganization().getId());
        List<Integer> userIdList = userRepository.selectManagerById(RoleEnums.SALE.getName(), groupOrgIds);
        userIdList.add(getLeaderUtil(strategy.getUid()).getId());
        userIdList.add(strategy.getUid());
        Integer[] userIds = getArrayByList(userIdList);
        //推送消息模板
        String subject = MessageTemplate.AUALYZE_REPORT(event.getReportName(), DateUtil.dateToStringTime(event.getCreateDate()));
        User sendUser = userRepository.findByUsername(event.getCreater());
        messageSerevice.pushMessage(subject, "/reports/strategies/" + event.getId(), ContentTypeEnums.SOP_ADVICE.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, sendUser, userIds);

    }

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(BoutiqueStrategy event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        User user = getUser();
        Strategy strategy = strategyRepository.findOne(event.getStrategyId());
        //推送人
        List<Integer> passUserIds = new ArrayList<>();
        passUserIds.add(user.getId());
        Integer[] userIds = getPushUserIdUtil(true, true, true, true, strategy.getUid(), strategy.getUid(), getLeaderUtil(strategy.getUid()), passUserIds);
        //推送消息模板
        String subject = MessageTemplate.GOOD_SELECT(strategy.getName(), "策略", user.getUsername());
        messageSerevice.pushMessage(subject, "/reports/strategies/" + event.getId(), ContentTypeEnums.SOP_ADVICE.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 1, user, userIds);

    }

    @Async
    @TransactionalEventListener
    public void handleUserSavedComplete(BoutiqueRecord event) {
        logger.info("收到监听事件:\n  {} : {} " + event.getClass().getTypeName());
        User user = getUser();
        Record record = recordRepository.findOne(event.getId());
        Strategy strategy = strategyRepository.findOne(record.getStrategyId());
        //推送人
        List<Integer> passUserIds = new ArrayList<>();
        passUserIds.add(user.getId());
        Integer[] userIds = getPushUserIdUtil(true, true, true, true, strategy.getUid(), strategy.getUid(), getLeaderUtil(strategy.getUid()), passUserIds);
        //推送消息模板
        String subject = MessageTemplate.GOOD_SELECT(record.getId() + "", "录音", user.getUsername());
        messageSerevice.pushMessage(subject, "/reports/strategies/" + event.getId(), ContentTypeEnums.SOP_ADVICE.getCode(), SendTypeEnums.TIGER_SYSTEM.getCode(), 0, user, userIds);
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户实体
     */
    public User getUser() {
        return userService.getUser();
    }

    /**
     * 消息推送范围
     *
     * @param pushLeader   是否推送事业部老大
     * @param pushSops     是否推送集团sop负责人
     * @param pushSale     是否军团长旗下销售经理
     * @param pushCreateId 是否推送创建者id
     * @param createId     创建者id
     * @param groupUserId  军团长id
     * @param leader       事业部老大id
     * @param passUserIds  不推送的用户ids
     * @return 消息推送用户ids集合
     */
    private Integer[] getPushUserIdUtil(boolean pushLeader, boolean pushSops, boolean pushSale, boolean pushCreateId, Integer createId, Integer groupUserId, User leader, List<Integer> passUserIds) {
        Set<Integer> resultSet = new HashSet<>();
        //事业部所有组织结构
        Integer orgPId = leader.getOrganization().getId();
        List<Integer> dowmOrg = organizationService.getDowmOrg(orgPId);
        dowmOrg.add(orgPId);
        //事业部sop
        List<Integer> threeIds = orgInfoRepository.selectUserIdByNameAndOrgId(RoleEnums.DSOP.getName(), dowmOrg);
        resultSet.addAll(threeIds);
        //集团sop
        List<Integer> fourIds = orgInfoRepository.selectSopUserIdByNameAndOrgId(RoleEnums.SOP.getName(), dowmOrg);
        resultSet.addAll(fourIds);
        //sop负责人
        if (pushSops) {
            Integer[] fiveIds = userRepository.selectUserIdsByRole(RoleEnums.SOPS.getName());
            List<Integer> fiveIdList = Arrays.asList(fiveIds);
            resultSet.addAll(fiveIdList);
        }
        //事业部老大
        if (pushLeader) {
            resultSet.add(leader.getId());
        }
        //军团长旗下所有销售部经理
        if (pushSale) {
            User one = userRepository.findOne(groupUserId);
            List<Integer> groupOrgIds = organizationService.getDowmOrg(one.getOrganization().getId());
            List<Integer> sixIds = userRepository.selectManagerById(RoleEnums.SALE.getName(), groupOrgIds);
            resultSet.addAll(sixIds);
        }
        //策略或分析报告本人
        if (pushCreateId) {
            resultSet.add(createId);
        }
        //不需要推送的人
        if (StrUtil.isNotNull(passUserIds)) {
            resultSet.removeAll(passUserIds);
        }
        //返回需要的推送人
        Integer[] arr = new Integer[resultSet.size()];
        Integer index = 0;
        for (Integer userId : resultSet) {
            arr[index] = userId;
            index++;
        }
        return arr;
    }

    /**
     * 根据军团长获取事业部负责人
     *
     * @param groupUserId 军团长id
     * @return 事业部老大id
     */
    private User getLeaderUtil(Integer groupUserId) {
        User user = userRepository.findOne(groupUserId);
        Integer orgId;
        //判断组织结构身份
        if (user.getOrganization().getOrganizationLevel().equals(OrganizationLevel.BUSINESS_UNIT)) {
            orgId = user.getOrganization().getParent().getParent().getId();
        } else {
            orgId = user.getOrganization().getParent().getId();
        }
        Integer leader = userRepository.selectMaxRoleById(RoleEnums.DIVISION.getName(), orgId);
        return userRepository.findOne(leader);
    }

    /**
     * list转换数组
     *
     * @return Intege数组
     */
    private Integer[] getArrayByList(List<Integer> list) {
        Integer[] arr = new Integer[list.size()];
        for (Integer i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    @Autowired
    public void setMessageSerevice(MessageSerevice messageSerevice) {
        this.messageSerevice = messageSerevice;
    }

    @Autowired
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setOrgInfoRepository(OrgInfoRepository orgInfoRepository) {
        this.orgInfoRepository = orgInfoRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setStrategyRepository(StrategyRepository strategyRepository) {
        this.strategyRepository = strategyRepository;
    }

    @Autowired
    public void setRecordRepository(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setStrategyLogRepository(StrategyLogRepository strategyLogRepository) {
        this.strategyLogRepository = strategyLogRepository;
    }
}
