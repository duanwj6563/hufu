package com.sunlands.feo.common;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sunlands.feo.domain.model.boutique.BoutiqueRecord;
import com.sunlands.feo.domain.model.strategy.Strategy;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.sunlands.feo.common.ExcelPOI.POIWorkBook;

public class ExcelUtil {

    /**
     * 下载单个策略详情
     * @param strategy
     * @param response
     * @throws IOException
     */
    public static void downloadStrategyExcel(Strategy strategy, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HSSFWorkbook workbook = POIWorkBook(strategy);
        downloadExcel(strategy.getGroupName()+strategy.getName(),workbook,response,request);
    }

    /**
     * 下载优秀录音列表
     * @param records
     * @param response
     * @throws IOException
     */
    public static void downloadRecordExcel(BoutiqueRecord[] records, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HSSFWorkbook workbook = POIWorkBook(records);
        downloadExcel("优秀录音",workbook,response,request);
    }

    public static void downloadExcel(String name, HSSFWorkbook workbook, HttpServletResponse response, HttpServletRequest request) throws IOException{
        ByteArrayOutputStream outXls = new ByteArrayOutputStream();
        workbook.write(outXls);
        String datStr= com.sunlands.feo.common.DateUtil.dateToStringDetailTime(new Date());
        try {
            byte[] content = outXls.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            String filename=name +datStr+ ".xls";
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0||request.getHeader("User-Agent").toUpperCase().contains("MSIE")||request.getHeader("User-Agent").toUpperCase().contains("Trident")) {
                filename = URLEncoder.encode(filename, "UTF-8");
            } else {
                filename = new String(filename.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", filename));
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentLength(content.length);

            ServletOutputStream outputStream = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(outputStream);
            byte[] buff = new byte[8192];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
            outputStream.flush();
            outputStream.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 将多个文件流转换为一个输出流
     * @param zipName 压缩文件名
     * @return
     * @throws IOException
     */
    public static void batchExportExcel(String zipName, Workbook[] workbooks, String[] fileNames,HttpServletResponse response,HttpServletRequest request) throws IOException {
        OutputStream out=response.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(out);
        response.setContentType("application/octet-stream ");
        response.setHeader("Connection", "close"); // 表示不能用浏览器直接打开
        response.setHeader("Accept-Ranges", "bytes");// 告诉客户端允许断点续传多线程连接下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(zipName.getBytes("GB2312"), "ISO8859-1"));
        response.setCharacterEncoding("UTF-8");
        for (int i=0;i<workbooks.length;i++){
            ZipEntry entry = new ZipEntry(fileNames[i] + ".xls");
            zipOutputStream.putNextEntry(entry);
            workbooks[i].write(zipOutputStream);
        }
        zipOutputStream.flush();
        zipOutputStream.close();
    }
}
