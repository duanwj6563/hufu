package com.feo.common;

import com.feo.domain.model.reportform.QualityWeek;
import com.feo.port.rest.dto.report.ReportReturn;
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
     */
    public static ReportReturn mode2Dto(Object source) {
        ReportReturn reportReturn = new ReportReturn();
        BeanUtils.copyProperties(source, reportReturn);
        return reportReturn;
    }

    /**
     * modelist转dto
     */
    public static List<ReportReturn> modeList2Dto(List<QualityWeek> source) {
        return source.stream().map(e -> mode2Dto(e)).collect(Collectors.toList());
    }
}
