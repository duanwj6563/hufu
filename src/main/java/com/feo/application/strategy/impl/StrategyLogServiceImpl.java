package com.feo.application.strategy.impl;

import com.feo.domain.model.enums.strategy.StrategyLogOperationType;
import com.feo.domain.model.strategy.StrategyLog;
import com.feo.domain.model.user.User;
import com.feo.domain.repository.strategy.StrategyLogRepository;
import com.feo.application.strategy.StrategyLogService;
import com.feo.application.UserService;
import com.feo.common.AssertUtil;
import com.feo.common.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by yangchao on 17/11/8.
 */
@Service
public class StrategyLogServiceImpl implements StrategyLogService {
    @Autowired
    private UserService userService;
    @Autowired
    private StrategyLogRepository strategyLogRepository;

    @Override
    public StrategyLog queryByStrategyIdAndUidAndType(Long strategyId, Integer userId, StrategyLogOperationType ot) {
        AssertUtil.isNotNull(strategyId,"策略id为空");
        AssertUtil.isNotNull(userId,"操作用户id为空");
        AssertUtil.isNotNull(ot,"操作类型为空");
        List<StrategyLog> list= strategyLogRepository.findByStrategyIdAndUser_IdAndOperationTypeOrderByDateDesc(strategyId, userId, ot.getCode());
        if (StrUtil.isNull(list))return null;
        return list.get(0);
    }

    @Override
    public StrategyLog newStrategyLog(Integer userId,String content,StrategyLogOperationType ot){
        /*记录日志*/
        User u = userService.queryByUserId(userId);
        StrategyLog s = new StrategyLog();
        s.setDate(new Date());
        s.setUser(u);
        s.setContent(content);
        s.setOperationType(ot.getCode());
        return s;
    }
}
