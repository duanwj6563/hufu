package com.feo.port.rest.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

@Aspect
@Component
public class TimeAspect {
    private final Logger logger = LoggerFactory.getLogger(TimeAspect.class);
    private static String[] types = {"java.lang.Integer", "java.lang.Double",
            "java.lang.Float", "java.lang.Long", "java.lang.Short",
            "java.lang.Byte", "java.lang.Boolean", "java.lang.Char",
            "java.lang.String", "int", "double", "long", "short", "byte",
            "boolean", "char", "float"};

    @Pointcut("execution(* com.sunlands.feo.port.rest.controller..*.*(..))")
    public void log() {

    }

    @Around("log()")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        request.setCharacterEncoding("utf-8");
        String classType = pjp.getTarget().getClass().getName();
        Class<?> clazz = Class.forName(classType);
        String clazzName = clazz.getName();
        String methodName = pjp.getSignature().getName();

        String[] paramNames = getFieldsName(this.getClass(), clazzName, methodName);

        String logContent = writeLogInfo(paramNames, pjp);
        logger.info(">>> Request info: HTTP method : {}, URL : {} ,params : {}",request.getMethod(),request.getRequestURL(),request.getQueryString());
        logger.info("ClazzName : {},MethodName : {},Params : {}", clazzName, methodName, logContent);

        Logger logger = LoggerFactory.getLogger(clazzName);
        long start = new Date().getTime();
        Object object = pjp.proceed();
        long time = new Date().getTime() - start;
        logger.info("Total time ：{} ms", time);
        return object;
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) throws JsonProcessingException {
        logger.info(">>> Response Info : {}", object == null ? null : new ObjectMapper().writeValueAsString(object));
    }

    private String writeLogInfo(String[] paramNames, ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        StringBuilder sb = new StringBuilder();
        boolean clazzFlag = true;
        for (int k = 0; k < args.length; k++) {
            Object arg = args[k];
            sb.append(paramNames[k] + " ");
            // 获取对象类型
            String typeName = arg.getClass().getTypeName();

            for (String t : types) {
                if (t.equals(typeName)) {
                    sb.append("=" + arg + "; ");
                }
            }
            if (clazzFlag) {
                sb.append(getFieldsValue(arg));
            }
        }
        return sb.toString();
    }

    /**
     * 得到方法参数的名称
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @return
     * @throws NotFoundException
     */
    private static String[] getFieldsName(Class cls, String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++) {
            paramNames[i] = attr.variableName(i + pos); //paramNames即参数名
        }
        return paramNames;
    }

    /**
     * 得到参数的值
     *
     * @param obj
     */
    public static String getFieldsValue(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        String typeName = obj.getClass().getTypeName();
        for (String t : types) {
            if (t.equals(typeName))
                return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("【");
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                for (String str : types) {
                    if (f.getType().getName().equals(str)) {
                        sb.append(f.getName() + " = " + f.get(obj) + "; ");
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.append("】");
        return sb.toString();
    }
}
