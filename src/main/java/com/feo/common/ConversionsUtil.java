package com.feo.common;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 转换工具类
 */
public class ConversionsUtil {
    /**
     * 单个mode转dto
     *
     * @param source
     * @param target
     */
    public static Object mode2Dto(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * modelist转dto
     */
    public static List modeList2Dto(List source, List target, Object o) {

        source.stream().map(e -> mode2Dto(e, o)).collect(Collectors.toList());
        return new ArrayList();
    }
}
