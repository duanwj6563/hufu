package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 质检性质
 * Created by huang on 2017/11/9.
 */
public enum RecTypeEnums {
    FIRST_ASK(1, "首咨"),
    SECOND_ASK(2, "回访"),
    FINAL_ASK(3, "跨期");

    private Integer code;

    private String name;

    RecTypeEnums(Integer code, String name) {
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
        List<Map<String, Object>> list = new ArrayList<>();
        for (RecTypeEnums r : RecTypeEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode() + "");
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }

}

