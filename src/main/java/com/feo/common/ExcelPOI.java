package com.feo.common;

import com.feo.domain.model.boutique.BoutiqueRecord;
import com.feo.domain.model.enums.record.GoodTypeEnums;
import com.feo.domain.model.enums.strategy.AreaEnums;
import com.feo.domain.model.enums.strategy.ProjectEnums;
import com.feo.domain.model.orgInfo.Organization;
import com.feo.domain.model.strategy.*;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExcelPOI {

    public static StrategyPhase findByStrategy(Strategy strategy, int num) {
        /*  获取策略阶段集合 */
        Set<StrategyPhase> strategyPhases = strategy.getStrategyPhases();
        /* 根据指定阶段返回策略阶段 */
        if (strategyPhases!=null){
            for (StrategyPhase s : strategyPhases) {
                if (s.getPhase() == num) {
                    return s;
                }
            }
        }
        return null;
    }

    public static HSSFWorkbook POIWorkBook(Strategy strategy){
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createInformationProperties();
        workbook.getDocumentSummaryInformation().setCompany("尚德在线教育科技有限公司");
        HSSFSheet sheet = workbook.createSheet("策略"+strategy.getId());
        sheet.setColumnWidth(0, 4500);
        sheet.setColumnWidth(1, 9200);
        sheet.setColumnWidth(2, 7500);
        sheet.setColumnWidth(3, 9000);
        sheet.setColumnWidth(4, 9000);
        sheet.setColumnWidth(5, 9000);
        StrategyPhase strategyPhase1=findByStrategy(strategy,1);
        FirstStrategyPhase firstStrategyPhase= (FirstStrategyPhase) strategyPhase1;
        StrategyPhase strategyPhase2=findByStrategy(strategy,2);
        SecStrategyPhase secStrategyPhase= (SecStrategyPhase) strategyPhase2;
        StrategyPhase strategyPhase3=findByStrategy(strategy,3);
        FinalStrategyPhase finalStrategyPhase= (FinalStrategyPhase) strategyPhase3;

//        第一行
        CellRangeAddress cra=new CellRangeAddress(0, 0, 0, 5);
        sheet.addMergedRegion(cra);
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)750);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(strategy.getName());
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20);
        font.setFontName("微软雅黑");
        style.setFont(font);
        cell.setCellStyle(style);

//        第二行
        HSSFRow row1 = sheet.createRow(1);
        row1.setHeight((short)750);
        for(int i=0;i<6;i++){
            HSSFCell cell1 = row1.createCell(i);
            CellStyle style1 = workbook.createCellStyle();
            style1.setWrapText(true);
            style1.setFillForegroundColor(new HSSFColor.YELLOW().getIndex());
            style1.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
            HSSFFont font1 = workbook.createFont();
            font1.setFontHeightInPoints((short) 12);
            font1.setFontName("微软雅黑");
            style1.setFont(font1);
            if ((i%2)==0){
                style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            }else {
                style1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                style1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                style1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            }
            cell1.setCellStyle(style1);
        }

        //        第三行
        HSSFRow row2 = sheet.createRow(2);
        row2.setHeight((short)850);
        CellStyle style2 = workbook.createCellStyle();
        style2.setWrapText(true);

        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style2.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font2= workbook.createFont();
        font2.setFontHeightInPoints((short) 12);
        font2.setFontName("微软雅黑");
        style2.setFont(font2);
        for(int i=0;i<6;i++){
            HSSFCell cell2 = row2.createCell(i);
            cell2.setCellStyle(style2);
        }

        //        第四行
        HSSFRow row3 = sheet.createRow(3);
        row3.setHeight((short)5000);
        CellStyle style3 = workbook.createCellStyle();
        style3.setWrapText(true);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        style3.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style3.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font3= workbook.createFont();
        font3.setFontHeightInPoints((short) 12);
        font3.setFontName("微软雅黑");
        style3.setFont(font3);

        CellStyle style30 = workbook.createCellStyle();

        style30.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style30.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style30.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style30.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style30.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style30.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style30.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font30= workbook.createFont();
        font30.setFontHeightInPoints((short) 12);
        font30.setFontName("微软雅黑");
        style30.setFont(font30);
        HSSFCell cell30 = row3.createCell(0);
        cell30.setCellStyle(style30);
        for(int i=1;i<6;i++){
            HSSFCell cell3 = row3.createCell(i);
            cell3.setCellStyle(style3);
        }

        //        第五行
        HSSFRow row4 = sheet.createRow(4);
        row4.setHeight((short)850);
        CellStyle style4 = workbook.createCellStyle();
        style4.setWrapText(true);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style4.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style4.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font4= workbook.createFont();
        font4.setFontHeightInPoints((short) 12);
        font4.setFontName("微软雅黑");
        style4.setFont(font4);
        for(int i=0;i<6;i++){
            HSSFCell cell4 = row4.createCell(i);
            cell4.setCellStyle(style4);
        }

        //        第六行
        HSSFRow row5 = sheet.createRow(5);
        row5.setHeight((short)5000);
        CellStyle style5 = workbook.createCellStyle();
        style5.setWrapText(true);
        style5.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style5.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style5.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style5.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style5.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style5.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style5.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font5= workbook.createFont();
        font5.setFontHeightInPoints((short) 12);
        font5.setFontName("微软雅黑");
        style5.setFont(font5);

        CellStyle style50 = workbook.createCellStyle();

        style50.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style50.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style50.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style50.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style50.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style50.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style50.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font50= workbook.createFont();
        font50.setFontHeightInPoints((short) 12);
        font50.setFontName("微软雅黑");
        style50.setFont(font50);
        HSSFCell cell50 = row5.createCell(0);
        cell50.setCellStyle(style50);
        for(int i=1;i<6;i++){
            HSSFCell cell5 = row5.createCell(i);
            cell5.setCellStyle(style5);
        }

        //        第七行
        HSSFRow row6 = sheet.createRow(6);
        row6.setHeight((short)850);
        CellStyle style6 = workbook.createCellStyle();
        style6.setWrapText(true);
        style6.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style6.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style6.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style6.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style6.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style6.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style6.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font6= workbook.createFont();
        font6.setFontHeightInPoints((short) 12);
        font6.setFontName("微软雅黑");
        style6.setFont(font6);
        for(int i=0;i<6;i++){
            HSSFCell cell6 = row6.createCell(i);
            cell6.setCellStyle(style6);
        }

        //        第八行
        HSSFRow row7 = sheet.createRow(7);
        row7.setHeight((short)5000);
        CellStyle style7= workbook.createCellStyle();
        style7.setWrapText(true);
        style7.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style7.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style7.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style7.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style7.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style7.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style7.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font7= workbook.createFont();
        font7.setFontHeightInPoints((short) 12);
        font7.setFontName("微软雅黑");
        style7.setFont(font7);

        CellStyle style70 = workbook.createCellStyle();

        style70.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style70.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style70.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style70.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style70.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style70.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style70.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font70= workbook.createFont();
        font70.setFontHeightInPoints((short) 12);
        font70.setFontName("微软雅黑");
        style70.setFont(font70);
        HSSFCell cell70 = row7.createCell(0);
        cell70.setCellStyle(style70);
        for(int i=1;i<6;i++){
            HSSFCell cell7 = row7.createCell(i);
            cell7.setCellStyle(style7);
        }

        //        第九行
        HSSFRow row8 = sheet.createRow(8);
        row8.setHeight((short)1500);
        CellStyle style8= workbook.createCellStyle();

        style8.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style8.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style8.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style8.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style8.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style8.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style8.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font8= workbook.createFont();
        font8.setFontHeightInPoints((short) 12);
        font8.setFontName("微软雅黑");
        style8.setFont(font8);
        style8.setWrapText(true);

        CellStyle style80 = workbook.createCellStyle();

        style80.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style80.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style80.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style80.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style80.setFillForegroundColor(new HSSFColor.LIGHT_GREEN().getIndex());
        style80.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style80.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font80= workbook.createFont();
        font80.setFontHeightInPoints((short) 12);
        font80.setFontName("微软雅黑");
        style80.setFont(font80);
        style80.setWrapText(true);
        HSSFCell cell80 = row8.createCell(0);
        cell80.setCellStyle(style80);
        for(int i=1;i<6;i++){
            HSSFCell cell8 = row8.createCell(i);
            cell8.setCellStyle(style8);
        }

        //第10行
        CellRangeAddress cra1=new CellRangeAddress(9, 9, 0, 5);
        CellRangeAddress cra2=new CellRangeAddress(3, 7, 5, 5);
        HSSFRow row9 = sheet.createRow(9);
        row9.setHeight((short)3000);
        CellStyle style9= workbook.createCellStyle();
        style9.setWrapText(true);
        style9.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style9.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style9.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style9.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style9.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style9.setFillPattern(CellStyle.SOLID_FOREGROUND);
//        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style9.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font9= workbook.createFont();
        font9.setFontHeightInPoints((short) 12);
        font9.setFontName("微软雅黑");
        style9.setFont(font9);
        for(int i=0;i<6;i++){
            HSSFCell cell9 = row9.createCell(i);
            cell9.setCellStyle(style9);
        }
        sheet.addMergedRegion(cra1);
        sheet.addMergedRegion(cra2);


//        往excel填充值
        HSSFRow roww1 = sheet.getRow(1);
        HSSFCell roww1Cell0 = roww1.getCell(0);
        roww1Cell0.setCellValue("项目：");
        HSSFCell roww1Cell1 = roww1.getCell(1);
        roww1Cell1.setCellValue(ProjectEnums.values()[strategy.getFirstProjectId()].getName());
        HSSFCell roww1Cell2 = roww1.getCell(2);
        roww1Cell2.setCellValue("适用部门：");
        HSSFCell roww1Cell3 = roww1.getCell(3);
        List<Organization> applydeparts = strategy.getApplydeparts();
        String str1="";
        for (Organization org:applydeparts ) {
            str1=str1+org.getName()+",";
        }
        int index = str1.lastIndexOf(",");
        if (index>0)
        str1=str1.substring(0,index);
        roww1Cell3.setCellValue(str1);
        HSSFCell roww1Cell4 = roww1.getCell(4);
        roww1Cell4.setCellValue("地域");
        HSSFCell roww1Cell5 = roww1.getCell(5);
        roww1Cell5.setCellValue(AreaEnums.values()[strategy.getArea()].getName());

        HSSFRow roww2 = sheet.getRow(2);
        HSSFCell roww2Cell0 = roww2.getCell(0);
        roww2Cell0.setCellValue("拨打策略");
        HSSFCell roww2Cell1 = roww2.getCell(1);
        roww2Cell1.setCellValue("探需必问点");
        HSSFCell roww2Cell2 = roww2.getCell(2);
        String str;
        if (strategy.getFirstProjectId()==0){
            str= "主推专业";
        }else if (strategy.getFirstProjectId()==1){
            str= "主推项目";
        }else {
            str= "主推院校";
        }
        roww2Cell2.setCellValue(str);
        HSSFCell roww2Cell3 = roww2.getCell(3);
        roww2Cell3.setCellValue("主推班型");
        HSSFCell roww2Cell4 = roww2.getCell(4);
        roww2Cell4.setCellValue("截杀策略+促销活动");
        HSSFCell roww2Cell5 = roww2.getCell(5);
        roww2Cell5.setCellValue("内部PK活动+激励活动");

        HSSFRow roww3 = sheet.getRow(3);
        HSSFCell roww3Cell0 = roww3.getCell(0);
        roww3Cell0.setCellValue("首咨");
        HSSFCell roww3Cell1 = roww3.getCell(1);
        if (firstStrategyPhase!=null){
            Set<Need> needs = firstStrategyPhase.getNeeds();
            String needstr="";
            for(Need need:needs){
                needstr=needstr+(need.getNumber()+1)+":"+need.getNeedName()+";\n";
            }
            roww3Cell1.setCellValue(needstr);
        }
        HSSFCell roww3Cell2 = roww3.getCell(2);
        if (firstStrategyPhase!=null) {
            Set<SecondProject> secondProjects = firstStrategyPhase.getSecondProjects();
            String secStr = "";
            for (SecondProject s : secondProjects) {
                secStr = secStr + (s.getNumber()+1) + ":" + s.getDescription() + ";\n";
            }
            roww3Cell2.setCellValue(secStr);
        }
        HSSFCell roww3Cell3 = roww3.getCell(3);
        if (firstStrategyPhase!=null) {
            Set<ClassSize> mainClassSizes = firstStrategyPhase.getMainClassSizes();
            String classStr = "";
            for (ClassSize classSize : mainClassSizes) {
                classStr = classStr + (classSize.getNumber() +1)+ ":" + classSize.getDescription() + ";\n";
            }
            roww3Cell3.setCellValue(classStr);
        }
        HSSFCell roww3Cell4 = roww3.getCell(4);
        if (firstStrategyPhase!=null) {
            Set<KillStrategy> killStrategies = firstStrategyPhase.getKillStrategies();
            String killStr = "";
            for (KillStrategy killStrategy : killStrategies) {
                killStr = killStr + (killStrategy.getNumber()+1) + ":" + killStrategy.getDescription() + ";\n";
            }
            roww3Cell4.setCellValue(killStr);
        }
//        HSSFCell roww3Cell5 = roww3.getCell(5);
//        roww3Cell5.setCellValue("");

        HSSFRow roww4 = sheet.getRow(4);
        HSSFCell roww4Cell0 = roww4.getCell(0);
        roww4Cell0.setCellValue("拨打策略");
        HSSFCell roww4Cell1 = roww4.getCell(1);
        roww4Cell1.setCellValue("唤醒回忆+深入探需");
        HSSFCell roww4Cell2 = roww4.getCell(2);
        roww4Cell2.setCellValue("解决首咨遗留问题");
        HSSFCell roww4Cell3 = roww4.getCell(3);
        roww4Cell3.setCellValue("主推班型");
        HSSFCell roww4Cell4 = roww4.getCell(4);
        roww4Cell4.setCellValue("截杀策略+优惠折扣");


        HSSFRow roww5 = sheet.getRow(5);
        HSSFCell roww5Cell0 = roww5.getCell(0);
        roww5Cell0.setCellValue("回访");
        HSSFCell roww5Cell1 = roww5.getCell(1);
        if (secStrategyPhase!=null) {
            Set<Need> needs1 = secStrategyPhase.getNeeds();
            String needstr1 = "";
            for (Need need : needs1) {
                needstr1 = needstr1 + (need.getNumber()+1) + ":" + need.getNeedName() + ";\n";
            }
            roww5Cell1.setCellValue(needstr1);
        }
        HSSFCell roww5Cell2 = roww5.getCell(2);
        if (secStrategyPhase!=null) {
            Set<SolveFirstProblem> solveFirstProblems = secStrategyPhase.getSolveFirstProblems();
            String slove = "";
            for (SolveFirstProblem s : solveFirstProblems) {
                slove = slove + (s.getNumber() +1)+ ":" + s.getSolve() + ";\n";
            }
            roww5Cell2.setCellValue(slove);
        }
        HSSFCell roww5Cell3 = roww5.getCell(3);
        if (secStrategyPhase!=null) {
            Set<ClassSize> mainClassSizes1 = secStrategyPhase.getMainClassSizes();
            String classStr1 = "";
            for (ClassSize classSize : mainClassSizes1) {
                classStr1 = classStr1 + (classSize.getNumber()+1) + ":" + classSize.getDescription() + ";\n";
            }
            roww5Cell3.setCellValue(classStr1);
        }
        HSSFCell roww5Cell4 = roww5.getCell(4);
        if (secStrategyPhase!=null) {
            Set<KillStrategy> killStrategies1 = secStrategyPhase.getKillStrategies();
            String killStr1 = "";
            for (KillStrategy killStrategy : killStrategies1) {
                killStr1 = killStr1 + (killStrategy.getNumber()+1) + ":" + killStrategy.getDescription() + ";\n";
            }
            roww5Cell4.setCellValue(killStr1);
        }

        HSSFRow roww6 = sheet.getRow(6);
        HSSFCell roww6Cell0 = roww6.getCell(0);
        roww6Cell0.setCellValue("拨打策略");
        HSSFCell roww6Cell1 = roww6.getCell(1);
        roww6Cell1.setCellValue("触发式开场");
        HSSFCell roww6Cell2 = roww6.getCell(2);
        roww6Cell2.setCellValue("辅助咨询工具");
        HSSFCell roww6Cell3 = roww6.getCell(3);
        roww6Cell3.setCellValue("主推班型");
        HSSFCell roww6Cell4 = roww6.getCell(4);
        roww6Cell4.setCellValue("终极截杀+优惠折扣");

        HSSFRow roww7 = sheet.getRow(7);
        HSSFCell roww7Cell0 = roww7.getCell(0);
        roww7Cell0.setCellValue("库存跨期");
        HSSFCell roww7Cell1 = roww7.getCell(1);
        if (finalStrategyPhase!=null) {
            Set<TriggerOpen> triggerOpens = finalStrategyPhase.getTriggerOpens();
            String open = "";
            for (TriggerOpen triggerOpen : triggerOpens) {
                open = open + +(triggerOpen.getNumber() +1)+ ":" + triggerOpen.getTriggerOpen() + ";\n";
            }
            roww7Cell1.setCellValue(open);
        }
        HSSFCell roww7Cell2 = roww7.getCell(2);
        if (finalStrategyPhase!=null) {
            Set<AssistTool> assistTools = finalStrategyPhase.getAssistTools();
            String assistStr = "";
            for (AssistTool assistTool : assistTools) {
                assistStr = assistStr + (assistTool.getNumber()+1) + ":" + assistTool.getTool() + ";\n";
            }
            roww7Cell2.setCellValue(assistStr);
        }
        HSSFCell roww7Cell3 = roww7.getCell(3);
        if (finalStrategyPhase!=null) {
            Set<ClassSize> mainClassSizes2 = finalStrategyPhase.getMainClassSizes();
            String classStr2 = "";
            for (ClassSize classSize : mainClassSizes2) {
                classStr2 = classStr2 +(classSize.getNumber()+1) + ":" + classSize.getDescription() + ";\n";
            }
            roww7Cell3.setCellValue(classStr2);
        }
        HSSFCell roww7Cell4 = roww7.getCell(4);
        if (finalStrategyPhase!=null) {
            Set<KillStrategy> killStrategies2 = finalStrategyPhase.getKillStrategies();
            String killStr2 = "";
            for (KillStrategy killStrategy : killStrategies2) {
                killStr2 = killStr2 + (killStrategy.getNumber()+1) + ":" + killStrategy.getDescription() + ";\n";
            }
            roww7Cell4.setCellValue(killStr2);
        }

        HSSFRow roww8 = sheet.getRow(8);
        HSSFCell roww8Cell0 = roww8.getCell(0);
        roww8Cell0.setCellValue("主要竞争对手");
        HSSFCell roww8Cell1 = roww8.getCell(1);
        if (strategy.getCompetitor()==null){
            roww8Cell1.setCellValue("哪几家：");
        }else {
            roww8Cell1.setCellValue("哪几家："+strategy.getCompetitor());
        }
        HSSFCell roww8Cell2 = roww8.getCell(2);
        if (strategy.getCompareContent()==null){
            roww8Cell2.setCellValue("竞争对手主推学历形式或专业：");
        }else {
            roww8Cell2.setCellValue("竞争对手主推学历形式或专业："+strategy.getCompareContent());
        }
        roww8Cell2.setCellValue("竞争对手主推学历形式或专业："+strategy.getCompareContent());
        HSSFCell roww8Cell3 = roww8.getCell(3);
        if (strategy.getComparePrice()==null){
            roww8Cell3.setCellValue("竞争对手价格：");
        }else {
            roww8Cell3.setCellValue("竞争对手价格："+strategy.getComparePrice());
        }
        HSSFCell roww8Cell4 = roww8.getCell(4);
        if (strategy.getCompareKill()==null){
            roww8Cell4.setCellValue("竞争对手截杀：");
        }else {
            roww8Cell4.setCellValue("竞争对手截杀："+strategy.getCompareKill());
        }
        HSSFCell roww8Cell5 = roww8.getCell(5);
        if (strategy.getAnalyzeAdvantage()==null){
            roww8Cell5.setCellValue("与竞争对手相比尚德优势分析：");
        }else {
            roww8Cell5.setCellValue("与竞争对手相比尚德优势分析："+strategy.getAnalyzeAdvantage());
        }

        HSSFRow roww9 = sheet.getRow(9);
        HSSFCell roww9Cell0 = roww9.getCell(0);
        roww9Cell0.setCellValue("说明：\r\n" +
                "1.所有模块都需要填写,以上表格内容为模板，无需照搬照抄：1）探需：填写主要问题。2）主推专业：需要填写专本分别的主推专业,最多主推1-2个专业。3）主推班型：填写主推班型的价格。4）截杀策略+促销活动：填写截杀策略及在括号中写出对应截杀话术和促销活动，务必简明扼要。5）内部PK活动+激励活动：军团内部PK或激励活动方案 \r\n" +
                "2.如本军团不同地区销售策略不同，请分别填写各个地区的主推专业、班型、及截杀策略，并标注清楚各销售部负责的地区。\r\n" +
                "3.每周更新策略时，只需要填写当周策略调整的模块，例如调整截杀策略和促销活动，例如竞争对手，例如报考期主推专业变更。");
        return workbook;
    }

    public static HSSFWorkbook POIWorkBook(BoutiqueRecord[] records) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        workbook.createInformationProperties();
        workbook.getDocumentSummaryInformation().setCompany("尚德在线教育科技有限公司");
        HSSFSheet sheet = workbook.createSheet("优秀录音");
        sheet.setColumnWidth(0, 3000);
        sheet.setColumnWidth(1, 3000);
        sheet.setColumnWidth(2, 5000);
        sheet.setColumnWidth(3, 18000);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("微软雅黑");
        style.setFont(font);

//        第一行
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)600);
        HSSFCell cell = row.createCell(0);
        cell.setCellStyle(style);
        cell.setCellValue("咨询师");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellStyle(style);
        cell1.setCellValue("所属军团");
        HSSFCell cell2 = row.createCell(2);
        cell2.setCellStyle(style);
        cell2.setCellValue("录音类型");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellStyle(style);
        cell3.setCellValue("url");
        for (int i=1;i<records.length+1;i++){
            HSSFRow row1 = sheet.createRow(i);
            row1.setHeight((short)600);
            BoutiqueRecord record=records[i-1];
            HSSFCell cell10 = row1.createCell(0);
            cell10.setCellValue(record.getCounselorName());
            HSSFCell cell11 = row1.createCell(1);
            cell11.setCellValue(record.getGroupName());
            HSSFCell cell22 = row1.createCell(2);
            cell22.setCellValue(GoodTypeEnums.values()[record.getGoodType()].getName());
            HSSFCell cell33 = row1.createCell(3);
            cell33.setCellValue(record.getUrl());
        }
        return workbook;
    }

    /**
     * 读取excel表的值
     * @param file excel文件路径
     * @param start 读取的开始列号（起始值为1）
     * @param end   读取的结束列号（含头不含尾）
     * @param startRowNum 读取的开始行号，直至读取的最后一行（起始值为1）
     * @return List<List<String>>
     */
    public static List<List<String>> getValues(String file,int start,int end,int startRowNum){
        List<List<String>> lists=new ArrayList<>();
        String fileToBeRead = file;
        Workbook workbook=null;
        try {
            if(fileToBeRead.indexOf(".xlsx")>-1){
                workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
            } else {
                workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
            }
            String sheetName = workbook.getSheetName(0);

            Sheet sheet = workbook.getSheet(sheetName);
            // 创建对工作表的引用
            int rows = sheet.getPhysicalNumberOfRows();// 获取表格的
            int columns = 0;
            for (int r = (startRowNum-1); r < rows; r++) { // 循环遍历表格的行
                Row row = sheet.getRow(r); // 获取单元格中指定的行对象
                if (row != null) {
                    List<String> values=new ArrayList<>();
                    //int cells = row.getPhysicalNumberOfCells();// 获取一行中的单元格数
                    //int cells = row.getLastCellNum();// 获取一行中最后单元格的编号（从1开始）
                    for (int c = start-1; c < end; c++) { // 循环遍历行中的单元格
                        Cell cell = row.getCell((short) c);
                        values.add(getCellValue(cell));
                    }
                    lists.add(values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

    /**
     * 获取cell的值
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) {
        DecimalFormat df = new DecimalFormat("#");
        if (cell == null)
            return "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format(cell.getDateCellValue()).toString();
                    //return sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                }
                return df.format(cell.getNumericCellValue());
            case Cell.CELL_TYPE_STRING:
                //System.out.println(cell.getStringCellValue());
                return cell.getStringCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case Cell.CELL_TYPE_ERROR:
                return cell.getErrorCellValue() + "";
        }
        return "";
    }
}
