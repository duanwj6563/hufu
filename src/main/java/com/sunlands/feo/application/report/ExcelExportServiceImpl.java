package com.sunlands.feo.application.report;

import com.sunlands.feo.common.ExcelUtil;
import com.sunlands.feo.domain.model.reportform.AnalysisProperties;
import com.sunlands.feo.domain.model.reportform.Analyze;
import com.sunlands.feo.domain.model.reportform.UserModules;
import com.sunlands.feo.domain.model.reportform.WeekWork;
import com.sunlands.feo.domain.repository.report.AnalysisPropertiesRepository;
import com.sunlands.feo.domain.repository.report.AnalysisRepository;
import com.sunlands.feo.domain.repository.report.WeekWorkRepository;
import com.sunlands.feo.port.rest.dto.report.WeekWorkDto;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelExportServiceImpl implements ExcelExportService {
    @Autowired
    private WeekWorkRepository weekWorkRepository;
    @Autowired
    private AnalysisRepository analysisRepository;
    @Autowired
    private AnalysisPropertiesRepository analysisPropertiesRepository;

    @Override
    public void weekWeek(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HSSFWorkbook[] workbooks = new HSSFWorkbook[ids.length];
        String[] fileNames = new String[ids.length];
        for (int j = 0; j < ids.length; j++) {
            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet("工作量报表");
            // 设置表格默认列宽度为15个字节
            sheet.setDefaultColumnWidth(20);
            String[] headers = {"排名", "SOP专员", "负责事业部", "负责军团数", "负责军团", "周听线数（通）", "听线时长（分钟）", "军团报告数", "优秀录音下载数", "系统提交策略数"};
            HSSFRow row = sheet.createRow(0);
            //生成表头
            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
            }
            List<WeekWork> weekList = weekWorkRepository.findByWeekIdOrderByRankAsc(ids[j]);
            //插入数据
            if (weekList.size() != 0) {
                // 遍历集合数据，产生数据行
                Iterator<WeekWork> it = weekList.iterator();
                int index = 0;
                while (it.hasNext()) {
                    index++;
                    row = sheet.createRow(index);
                    WeekWork t = it.next();
                    WeekWorkDto weekWorkDto = new WeekWorkDto();
                    BeanUtils.copyProperties(t, weekWorkDto);
                    // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                    Field[] fields = weekWorkDto.getClass().getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        HSSFCell cell = row.createCell(i);
                        Field field = fields[i];
                        String fieldName = field.getName();
                        String getMethodName = "get"
                                + fieldName.substring(0, 1).toUpperCase()
                                + fieldName.substring(1);
                        Class tCls = t.getClass();
                        Method getMethod = tCls.getMethod(getMethodName,
                                new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});
                        // 判断值的类型后进行强制类型转换
                        String textValue = null;
                        if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            textValue = sdf.format(date);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }
                        HSSFRichTextString richString = new HSSFRichTextString(
                                textValue);
                        cell.setCellValue(richString);
                    }
                }
            }
            workbooks[j] = workbook;
            fileNames[j] = "周工作量" + new Date().getTime();
        }
        if (ids.length > 1) {
            ExcelUtil.batchExportExcel("工作量报表.zip", workbooks, fileNames, response, request);
        } else {
            excleDownload(response, workbooks[0], "周工作量");
        }

    }

    @Override
    public void qualityWeeks(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] headers = {"事业部", "军团", "地区", "总机会数", "抽取数", "抽取率", "执行率(执行得分）", "环比", "执行排名", "探需", "主推专业执行占比", "截杀策略执行占比", "流程完整性执行占比"
                , "销转", "RPA"};
        if (ids.length == 1) {
            HSSFWorkbook workbook = getHeaders(headers, "虎符质检综合报表", 13);
            excleDownload(response, workbook, "质检综合报表");
        } else if (ids.length > 1) {
            HSSFWorkbook[] workbooks = new HSSFWorkbook[ids.length];
            String[] fileNames = new String[ids.length];
            for (int j = 0; j < ids.length; j++) {
                HSSFWorkbook workbook = getHeaders(headers, "虎符质检综合报表", 13);
                workbooks[j] = workbook;
                fileNames[j] = "虎符质检综合报表" + new Date().getTime();
            }
            ExcelUtil.batchExportExcel("虎符质检综合报表.zip", workbooks, fileNames, response, request);
        }


    }

    /**
     * 下载方法
     *
     * @param response
     * @param workbook
     * @throws IOException
     */
    private void excleDownload(HttpServletResponse response, HSSFWorkbook workbook, String name) throws IOException {
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((name + ".xls").getBytes(), "iso-8859-1"));
        response.flushBuffer();
        workbook.write(response.getOutputStream());
    }

    /**
     * 生成表头公共方法
     *
     * @param headers
     * @param sheetName
     * @return
     */
    private HSSFWorkbook getHeaders(String[] headers, String sheetName, int size) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(size);
        HSSFRow row = sheet.createRow(0);
        //生成表头
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        return workbook;
    }


    @Override
    public void analyzes(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] headers = {"事业部", "军团", "军团长", "SOP负责人", "策略执行率", "流程完整度",
                "销转", "RPA", "执行排名", "质检抽检数", "抽检比例", "违规率", "探需", "专业", "班型", "截杀",
                "执行问题与建议", "用户模块", "用户分析", "问题话术", "建议话术", "上周培训效果分析"};
        if (ids.length == 1) {
            HSSFWorkbook workbook = getHeaders(headers, "军团策略与质检分析报告", 13);
            getAnalyzeData(ids[0], workbook);
            excleDownload(response, workbook, "军团策略与质检分析报告");
        } else if (ids.length > 1) {
            HSSFWorkbook[] workbooks = new HSSFWorkbook[ids.length];
            String[] fileNames = new String[ids.length];
            for (int j = 0; j < ids.length; j++) {
                HSSFWorkbook workbook = getHeaders(headers, "军团策略与质检分析报告", 13);
                getAnalyzeData(ids[j], workbook);
                workbooks[j] = workbook;
                fileNames[j] = "军团策略与质检分析报告" + new Date().getTime();
            }
            ExcelUtil.batchExportExcel("军团策略与质检分析报告.zip", workbooks, fileNames, response, request);
        }


    }

    /**
     * 分析报告数据生成
     *
     * @param id
     * @param workbook
     */
    private void getAnalyzeData(Long id, HSSFWorkbook workbook) {
        Analyze analyze = analysisRepository.findByStrategyId(id);
        AnalysisProperties analysisProperties = analysisPropertiesRepository.findByStrategyId(id);
        HSSFSheet sheet = workbook.getSheet("军团策略与质检分析报告");
        //第一个单元格
        HSSFRow row = sheet.createRow(1);
        row.createCell(0).setCellValue("");
        if (analyze != null) {
            row.createCell(1).setCellValue(toValue(analyze.getLegion()));
            row.createCell(2).setCellValue(toValue(analyze.getEgatus()));
        }
        row.createCell(3).setCellValue("");
        if (analysisProperties != null) {
            row.createCell(4).setCellValue(toValue(analysisProperties.getEnforcedPolicies()));
            row.createCell(5).setCellValue(toValue(analysisProperties.getIntegrityProcess()));
            row.createCell(6).setCellValue(toValue(analysisProperties.getPinTurn()));
            row.createCell(7).setCellValue(toValue(analysisProperties.getRpa()));
            row.createCell(8).setCellValue(toValue(analysisProperties.getPerformRanking()));
            row.createCell(9).setCellValue(toValue(analysisProperties.getQualityNumber()));
            row.createCell(10).setCellValue(toValue(analysisProperties.getSamplingRate()));
            row.createCell(11).setCellValue(toValue(analysisProperties.getViolationRate()));
            row.createCell(12).setCellValue(toValue(analysisProperties.getAgents()));
            row.createCell(13).setCellValue(toValue(analysisProperties.getMajors()));
            row.createCell(14).setCellValue(toValue(analysisProperties.getClassType()));
            row.createCell(15).setCellValue(toValue(analysisProperties.getPutAway()));
        }
        if (analyze != null) {
            row.createCell(16).setCellValue(toValue(analyze.getIssuesAndrec()));
            row.createCell(17).setCellValue(getUserModules(analyze.getUserModules()));
            row.createCell(18).setCellValue(toValue(analyze.getUserAnalysis()));
            row.createCell(19).setCellValue(toValue(analyze.getQuestionWord()));
            row.createCell(20).setCellValue(toValue(analyze.getSuggestedWord()));
            row.createCell(21).setCellValue(toValue(analyze.getWeekEffect()));
        }

    }

    /**
     * 遍历用户模块list 数据
     * @param userModules
     * @return
     */
    private String getUserModules(List<UserModules> userModules) {
        StringBuffer stringBuffer = new StringBuffer();
        if (userModules.size() == 0) {
            return "";
        } else {
            for (UserModules u : userModules) {
                stringBuffer.append(u.getName() + "\n");
            }
        }
        return stringBuffer.toString();
    }

    @Override
    public void record(Long[] ids, HttpServletRequest request, HttpServletResponse response, OutputStream out) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] headers = {"质检时间", "质检员", "录音类型", "录音链接", "录音时长", "是否报名", "录音性质）", "审核数量", "订单编号", "学员姓名", "报名手机号",
                "一级项目", "报名时间", "缴费金额", "问题1一级分类", "问题1二级分类", "问题1记录", "学员现状分类", "学员现状分类", "取证目的",
                "学习基础", "取证时间/休息时间/工作地区", "探需执行", "策略专业", "推荐专业", "专业执行", "不过退费班7980", "专本连读14800", "名校精品班", "1对1私教班4980",
                "名校就业班12980", "不过退费班7980", "其他（专本连读：8800，12800，15800，19800）", "推班执行", "学籍备案", "政策限制（加考数英/学制改革/高中毕业证）",
                "助学金名额", "分期名额", "其他", "截杀执行", "流程完整性", "执行得分", "备注", "是否追责", "违规时间", "是否扣流水", "罚款金额", "责任人", "事业部", "责任方/军团",
                "销售部/组", "责任人归属", "一级分类", "二级分类", "是否可以回访申诉", "是否", "结果分类", "补充说明", "申诉", "申诉结果", "补充说明"};
        if (ids.length == 1) {
            HSSFWorkbook workbook = getHeaders(headers, "虎符质检抽查打分表", 13);
            excleDownload(response, workbook, "虎符质检抽查打分表");
        } else if (ids.length > 1) {
            HSSFWorkbook[] workbooks = new HSSFWorkbook[ids.length];
            String[] fileNames = new String[ids.length];
            for (int j = 0; j < ids.length; j++) {
                HSSFWorkbook workbook = getHeaders(headers, "虎符质检抽查打分表", 13);
                workbooks[j] = workbook;
                fileNames[j] = "虎符质检抽查打分表" + new Date().getTime();
            }
            ExcelUtil.batchExportExcel("虎符质检抽查打分表.zip", workbooks, fileNames, response, request);
        }
    }

    /**
     * 将对象转换为空
     *
     * @param o
     * @return
     */
    private String toValue(Object o) {
        return o == null ? "" : o.toString();
    }
}
