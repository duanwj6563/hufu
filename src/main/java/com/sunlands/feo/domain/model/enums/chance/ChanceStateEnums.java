package com.sunlands.feo.domain.model.enums.chance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学员情况
 */
public enum ChanceStateEnums {

    WORKER("0", "工人"),
    STUDENT("1", "学生"),
    SERVANT("2", "公务员"),
    MANAGER("3", "管理人员"),
    SOLDIER("4", "部队官兵"),
    UNWORK("5", "待业在家"),
    PUBLIC_INSTITUTION("6", "事业单位"),
    PERSONAGE("7", "个体/创业"),
    EMPLOYMENT("8", "职工/白领"),
    MARKET("9", "销售/服务"),
    GOVERNMENT("10", "政府事业单位");

    private String code;

    private String name;

    ChanceStateEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Map<String, Object>> getIdAndNameList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ChanceStateEnums r : ChanceStateEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
