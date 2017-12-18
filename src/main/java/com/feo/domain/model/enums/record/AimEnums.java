package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取证目的
 * Created by huang on 2017/11/9
 */
public enum AimEnums {
    GO_ABROAD("0", "出国"),
    COMMENT_WORK("1", "评职称"),
    PERSON_IMPROVE("2", "个人提升/充实自己"),
    EDUCATION_CHILD("3", "教育子女"),
    CERTIFICATION("4", "考研/资格证"),
    HUKOU("5", "积分落户"),
    PROMOTION("6", "升职加薪"),
    PUBLIC_INSTITUTION("7", "进事业单位"),
    LOOK_WORK("8", "找工作/跳槽/转行");

    AimEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;

    private String name;

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
        for (AimEnums r : AimEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}