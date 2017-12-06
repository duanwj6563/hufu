package com.sunlands.feo.domain.model.enums.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地域
 */
public enum AreaEnums {
    FIRST_TIER_CITY(0,"一线城市（北上广深武）"),//0、一线城市（北上广深武）
    APP(1,"全国APP"),//1、全国APP
    SEM(2,"全国SEM"),//2、全国SEM
    SEC_TIER_CITY(3,"二三线城市（外地）");//3、二三线城市（外地）
    private Integer code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    AreaEnums(Integer code, String name){
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public AreaEnums setCode(Integer code) {
        this.code = code;
        return this;
    }
    public static List<Map<String, Object>> getIdAndNameList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (AreaEnums r : AreaEnums.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
