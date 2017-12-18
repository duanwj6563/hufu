package com.feo.domain.model.enums.record;

import com.feo.domain.model.enums.strategy.AreaEnums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自考类型
 * Created by huang on 2017/11/9.
 */
public enum ItemEnums {
    SELF_STUDY(0, "自考"),
    TEACHER(1, "教资"),
    OTHER(2, "其他"),
    TO_STAY(3, "等等待加");
    private Integer code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ItemEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public ItemEnums setCode(Integer code) {
        this.code = code;
        return this;
    }

    public static List<Map<String, Object>> getIdAndNameList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AreaEnums r : AreaEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
