package com.feo.domain.model.enums.record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 录音类型
 * Created by huang on 2017/11/9.
 */
public enum RecordTypeEnums {
    GENERAL("0", "常规"),
    VIOLATION("1", "违规"),
    BLCK_START("2", "后端发起"),
    SOUND_SYSTEM("3", "语音系统");

    private String code;

    private String name;

    RecordTypeEnums(String code, String name) {
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
        for (RecordTypeEnums r : RecordTypeEnums.values()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}