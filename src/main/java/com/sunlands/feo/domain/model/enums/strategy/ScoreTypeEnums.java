package com.sunlands.feo.domain.model.enums.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2017/11/10.
 */
public enum ScoreTypeEnums {
    //    0、主推项目 1、探需问题 2、主推专业 3、主推院校 4、主推班型 5、截杀策略 6、解决首咨遗留问题 7、触发式开场 8、辅助工具
    MAIN_PROJECT(0, "主推项目"),
    NEED(1, "探需问题"),
    MAIN_MAJOR(2, "主推专业"),
    MAIN_SCHOOL(3, "主推院校"),
    MAIN_CLASS(4, "主推班型"),
    KILL(5, "截杀策略"),
    FIRST_ASK_QUESTION(6, "解决首咨遗留问题"),
    TRIGGER_OPEN(7, "触发式开场"),
    ASSIST_TOOL(8, "辅助工具");

    private Integer code;

    private String name;

    ScoreTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getIdAndNameList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (ScoreTypeEnums r : ScoreTypeEnums.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
