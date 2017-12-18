package com.feo.domain.model.enums.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huang on 2017/11/28.
 */
public enum RoleEnums {
    //事业部负责人
    DIVISION(0, "DIVISION"),
    //军团长
    MANAGER(1, "MANAGER"),
    //销售经理/销售主管
    SALE(2, "SALE"),
    //集团sop负责人
    SOPS(3, "SOPS"),
    //集团sop专员
    SOP(4, "SOP"),
    //事业部sop
    DSOP(5, "DSOP"),
    //咨询师
    USER(6, "USER");

    RoleEnums(Integer code, String name) {
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
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (RoleEnums r : RoleEnums.values()) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", r.getCode());
            map.put("name", r.getName());
            list.add(map);
        }
        return list;
    }
}
