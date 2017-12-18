package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程完成度
 * Created by huang on 2017/11/9
 */
public enum FinishNumEnums {
    FINISH_FIVE("4", "4"),
    FINISH_SIX("5", "6"),
    FINISH_EIGHT("8", "8"),
    FINISH_TEN("10", "10");

    private String code;

    private String name;

    FinishNumEnums(String code, String name) {
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
        for (FinishNumEnums r : FinishNumEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
