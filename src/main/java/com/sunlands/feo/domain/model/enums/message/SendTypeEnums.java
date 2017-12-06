package com.sunlands.feo.domain.model.enums.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推送类型枚举
 * Created by huang on 2017/11/24.
 */
public enum SendTypeEnums {
    //消息发送类型 0,虎符  1,天网
    TIGER_SYSTEM(0, "虎符"),
    SKYNET_SYSTEM(1, "天网");

    SendTypeEnums(Integer code, String name) {
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
        for (SendTypeEnums r : SendTypeEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
