package com.feo.application.strategy;

import java.util.List;
import java.util.Map;

public interface StrategyCommonService {

    /**
     * 获取区域列表
     * @return
     */
    public List<Map<String,Object>> getAreas();

    /**
     * 获取策略阶段分类列表
     * @return
     */
    public List<Map<String,Object>> getScoreTypes();

    /**
     * 获取一级项目列表
     * @return
     */
    public List<Map<String,Object>> getProjects();

}
