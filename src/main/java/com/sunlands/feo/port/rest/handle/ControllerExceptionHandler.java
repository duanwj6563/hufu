package com.sunlands.feo.port.rest.handle;

import com.sunlands.feo.domain.exception.GlobalException;
import com.sunlands.feo.domain.exception.UserDefinedException;
import com.sunlands.feo.domain.exception.ExceptionReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = UserDefinedException.class)
    @ResponseBody
    public Object handleUserDefinedException(HttpServletRequest request, Exception e) {
        logger.error("【系统异常 - UserDefinedException】{}", e);
        logger.error("HTTP method : {}, URL : {} ,params : {}",request.getMethod(),request.getRequestURL(),request.getQueryString());
        String className = e.getStackTrace()[0].getClassName();
        GlobalException ge = (GlobalException) e;
        ExceptionReturn er = new ExceptionReturn();
        BeanUtils.copyProperties(ge,er);
        er.setPath(request.getServletPath());
        er.setException(className);
        logger.error("ExceptionInfo : {}", er);
        return er;
    }
    @ExceptionHandler(value = Exception.class)
    public Object handleException(HttpServletRequest request, Exception e) {
        logger.error("【系统异常 - Exception】{}", e);
        logger.error("HTTP method : {}, URL : {} ,params : {}",request.getMethod(),request.getRequestURL(),request.getQueryString());
        logger.error("ExceptionInfo : {}", e);
        return e;
    }
}

