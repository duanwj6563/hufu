package com.feo.application.strategy;

import com.feo.domain.exception.ServerStatus;
import com.feo.domain.model.enums.AuditStatus;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.domain.model.strategy.*;
import com.feo.domain.model.user.UserInfo;
import com.feo.port.rest.dto.CommonPage;
import com.feo.port.rest.dto.strategy.AddStrategyPhaseModel;
import com.feo.port.rest.dto.strategy.SelectStrategyCondition;
import com.feo.port.rest.dto.strategy.StrategyBase;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public interface StrategyService {

    /**
     * 根据id获取对应策略
     * @param id 策略id
     * @return
     */
    public Strategy findById(Long id);

    /**
     * 查询指定策略阶段
     * @param strategy
     * @param num
     * @return
     */
    public StrategyPhase findByStrategy(Strategy strategy,int num);

    /**
     * 新建策略
     * @param
     * @return
     */
    public Map<String,Object> createStrategy(Long strategyId);

    /**
     * 添加策略基本信息
     * @param oldStrategyId
     * @param newStrategy
     * @return
     */
    public Map<String,Object> addStrategyBase(Long oldStrategyId,StrategyBase strategyBase);

    /**
     * 添加首咨策略阶段
     * @param firstStrategyPhase
     * @param oldStrategyId
     * @return
     */
    public boolean addFirstStrategyPhase(FirstStrategyPhase firstStrategyPhase, Long oldStrategyId);

    /**
     * 添加回访策略阶段
     * @param secStrategyPhase
     * @param oldStrategyId
     * @return
     */
    public boolean addSecStrategyPhase(SecStrategyPhase secStrategyPhase, Long oldStrategyId);

    /**
     * 保存或更新策略（此方法为保存库存策略阶段和竞争对手）
     * @param
     */
    public boolean addFinalStrategyPhase(Long srategyId, FinalStrategyPhase finalStrategyPhase, String competitor, String compareContent, String comparePrice, String compareKill, String analyzeAdvantage);


    /**
     * 验证策略的策略阶段是否已经含有对应的策略阶段
     * @param strategyPhases
     * @param num
     * @return
     */
    public boolean isHasStrategyPhase(Set<StrategyPhase> strategyPhases, int num);

    /**
     * 追加策略阶段相关
     * @param
     * @return
     */
    public boolean addStrategy(Long strategyId,List<AddStrategyPhaseModel> AddStrategyPhaseModels);

    /**
     * 添加策略建议
     * @param
     * @param advise
     * @return
     */
    public boolean addAdvice(Advise advise);

    /**
     * 根据策略id获取策略详情
     * @param strategyId
     * @return
     */
    public Map<String,Object> getStrategyLogDetails(Long strategyId,Integer number, Integer type);

    /**
     * 获取策略追加列表
     * @param strategyId
     * @param number
     * @return
     */
    public Map<String,Object> getStrategyAddDetails(Long strategyId, Integer number);

    /**
     * 获取策略
     * @param strategyId 策略id
     * @return
     */
    public Map<String,Object> getStrategy(Long strategyId);

    /**
     * 查询策略
     * @param area
     * @param firstProject
     * @param applyparts
     * @param status
     * @return
     */
//    public List<Map<String,Object>> queryStrategy(Integer area, Long firstProject, Long applyparts, Integer status);

    /**
     * 查询策略分页
     * @param selectStrategyCondition
     * @return
     */
    public CommonPage queryStrategy(SelectStrategyCondition selectStrategyCondition);

    /**
     * 修改策略状态
     * @param id
     * @param auditStatus
     * @return
     */
    boolean updateStrategyStatus(Long id, AuditStatus auditStatus,String logContent);

    /**
     * 获取指定策略阶段
     * @param strategyId
     * @param newstrategyId
     * @param type
     * @return
     */
    Map<String,Object> getStrategyPhase(Long strategyId, Long newstrategyId, Integer type);

    /**
     * 测试下载
     * @param strategyId
     * @param response
     * @return
     */
    ServerStatus downloadStrategy(Long strategyId, HttpServletResponse response, HttpServletRequest request) throws IOException;

    /**
     * 批量下载策略
     * @param strategyIds
     * @param response
     * @return
     * @throws IOException
     */
    ServerStatus batchDownloadStrategy(long[] strategyIds,HttpServletResponse response,HttpServletRequest request) throws IOException;

    /**
     * 判断策略创建用户和当前用户是否是同一人
     *
     * @param uid
     * @return
     */
    public void isUser(Integer uid);

    /**
     * 如果创建策略时有的部门已经创建了，返回已创建过策略的部门集合
     *
     * @param organizations
     * @return
     */
    public String validityPeriod(List<Organization> organizations);

    /**
     * 获取组织部门的树形结构
     * @param organizations
     * @return
     */
    public List<Organization> getOrgTree(Collection<Organization> organizations);

    /**
     * 验证策略是否可以追加
     * @param strategy
     */
    public void validityAdd(Strategy strategy);

    /**
     * 获取下周一零点时间
     *
     * @return
     */
    public Date lastMonday();

    /**
     * 获取二级项目类型
     * @param strategy
     * @return
     */
    public String getType(Strategy strategy);

    Strategy getNewStrategy(UserInfo userInfo);

    boolean upload(MultipartFile file, Long strategyPhaseId) throws Exception;

    boolean downloadStrategyVerbalTrick(Long strategyPhaseId, HttpServletResponse response, HttpServletRequest request) throws Exception;
}
