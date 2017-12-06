package com.sunlands.feo.common;

import com.sunlands.feo.port.rest.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    /**
     * 策略话术下载
     *
     * @param url
     * @param request
     * @param response
     * @throws Exception
     */
    public static void download(String url,String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try (InputStream inputStream = new FileInputStream(url);
             OutputStream outputStream = response.getOutputStream();) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename="+filename);
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }

    /**
     * 策略话术上传
     * @param file
     * @param folder
     * @return
     * @throws Exception
     */
    public static FileInfo upload(MultipartFile file, String folder) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //TODO 抛异常
        if (!file.isEmpty()) {
            String folders = folder + "\\" + formatter.format(new Date());
            File localFile = new File(folders);
            if (!localFile.exists()) {
                localFile.mkdirs();
            }
            File localFiles = new File(folders, file.getOriginalFilename());
            file.transferTo(localFiles);
            return new FileInfo(localFiles.getAbsolutePath());
        }
        return new FileInfo("");
    }
}
