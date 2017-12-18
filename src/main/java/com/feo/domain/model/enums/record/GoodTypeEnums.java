package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 优秀类型
 * Created by huang on 2017/11/9.
 */
public enum GoodTypeEnums {
    FINISH_SUCCESS("0", "流程完整"),
    CLASS_RECOMMEND("1", "推班明确"),
    NEED_CLEAR("2", "探需到位"),
    SONIC_IMPACT_REAL("3", "截杀真实"),
    MAJORS_AJILE("4", "专业灵活");

    private String code;

    private String name;

    GoodTypeEnums(String code, String name) {
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
        for (GoodTypeEnums r : GoodTypeEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
