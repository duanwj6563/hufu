package com.sunlands.feo.application.report;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

public interface ExcelExportService {
    /**
     * 工作量报表导出
     *
     * @param ids
     * @param request
     * @param response
     * @param out
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void weekWeek(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 质检综合导出
     *
     * @param request
     * @param response
     * @param out
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void qualityWeeks(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 分析报告导出
     *
     * @param id
     * @param request
     * @param response
     * @param out
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void analyzes(Long[] id, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 录音打分下载
     *
     * @param request
     * @param response
     * @param out
     * @throws IOException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void record(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
