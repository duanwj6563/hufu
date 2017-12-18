package com.feo.domain.model.enums.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目
 */
public enum ProjectEnums {

    SELF_STUDY(0,"自考"),//0、自考
    TEACHER(1,"教资"),//1、教资
    OTHER(2,"其他");//2、其他

    private Integer code;
    private String name;

    ProjectEnums(Integer code,String name){
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getCode() {
        return code;
    }

    public ProjectEnums setCode(Integer code) {
        this.code = code;
        return this;
    }
    public static List<Map<String, Object>> getIdAndNameList() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (ProjectEnums r : ProjectEnums.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
