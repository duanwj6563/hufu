package com.feo.domain.model.enums.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送内容类型
 * Created by huang on 2017/11/24.
 */
public enum ContentTypeEnums {
    GROUP_DYNAMIC(0, "军团动态"),
    SOP_ADVICE(1, "SOP反馈"),
    SYSTEM_MESSAGE(2, "系统通知");

    ContentTypeEnums(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;

    private String name;

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
        for (ContentTypeEnums r : ContentTypeEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
