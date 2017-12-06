package com.sunlands.feo.port.rest.controller.file;

import com.sunlands.feo.port.rest.dto.FileInfo;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/files")
public class FileController {
    private String folder = "D:\\Users\\cindy\\Downloads\\file";
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/goUpload")
    public String go() {
        return "uploadimg";
    }

    @PostMapping
    @ResponseBody
    public FileInfo upload(MultipartFile file) throws Exception {
        logger.info("【文件信息】{}【文件原始名字】{}【文件大小】{}", file.getName(), file.getOriginalFilename(), file.getSize());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //TODO 抛异常
        if (!file.isEmpty()) {
            logger.info("【文件后缀suffix】{}", file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
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

    @GetMapping("/{id}")
    public void download(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
             OutputStream outputStream = response.getOutputStream();) {

            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }
}