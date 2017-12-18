package com.feo.common;

import java.util.Collection;
import java.util.Map;

/**
 * 数据校验
 * Created by yangchao on 17/11/7.
 */
public class StrUtil {

    /**
     * 对象为空校验
     *
     * @param obj 需要验证的类型
     * @return 是否是空
     */
    public static boolean isNull(Object obj) {
        if (null == obj) {
            return true;
        }
        if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;
        }
        if (obj instanceof String && ((String) obj).trim().isEmpty()) {
            return true;
        }
        if (obj instanceof String && obj.equals("null")) {
            return true;
        }
        if (obj instanceof StringBuffer && ((StringBuffer) obj).length() == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).size() == 0) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 对象非空校验
     *
     * @param obj 需要验证的类型
     * @return 是否非空且
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 对象非空校验（不能为0）
     *
     * @param obj 需要验证的类型
     * @return 是否非空且不为0
     */
    public static boolean isNotNullAndNo0(Object obj) {
        boolean flag = !isNull(obj);
        if (flag) {
            if (obj instanceof Integer && (Integer) obj == 0) {
                return false;
            }
        }
        return flag;
    }

}
