package com.feo.application.strategy.impl;

import com.feo.application.strategy.StrategyCommonService;
import com.feo.domain.model.enums.strategy.AreaEnums;
import com.feo.domain.model.enums.strategy.ProjectEnums;
import com.feo.domain.model.enums.strategy.ScoreTypeEnums;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 一些策略公用列表接口
 */
@Service
public class StrategyCommonServiceImpl implements StrategyCommonService {

    @Override
    public List<Map<String, Object>> getAreas() {
        return AreaEnums.getIdAndNameList();
    }

    @Override
    public List<Map<String, Object>> getScoreTypes() {
        return ScoreTypeEnums.getIdAndNameList();
    }

    @Override
    public List<Map<String, Object>> getProjects() {
        return ProjectEnums.getIdAndNameList();
    }
}
