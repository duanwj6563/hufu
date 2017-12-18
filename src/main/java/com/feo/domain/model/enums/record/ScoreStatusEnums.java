package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 打分状态枚举
 * Created by huang on 2017/11/9.
 */
public enum ScoreStatusEnums {
    NOSAVE_NOSUBMIT("0", "未保存未提交"),
    SAVE_NOSUBMIT("1", "保存未提交"),
    SAVE_SUBMIT("2", "保存并提交");

    private String code;

    private String name;

    ScoreStatusEnums(String code, String name) {
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
        for (ScoreStatusEnums r : ScoreStatusEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
