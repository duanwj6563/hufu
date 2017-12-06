package com.sunlands.feo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举工具类
 * Created by huang on 2017/12/1.
 */
public class EnumsUtil {

    /**
     * 增加所有选项
     *
     * @param enums 枚举项
     * @return 返回增加所有字段的集合
     */
    public static List<Map<String, Object>> getIdAndNameList(List<Map<String, Object>> enums) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer i = 0; i < enums.size(); i++) {
            if (i == 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", "null");
                map.put("name", "所有");
                list.add(map);
            }
            list.add(enums.get(i));
        }
        return list;
    }
}
