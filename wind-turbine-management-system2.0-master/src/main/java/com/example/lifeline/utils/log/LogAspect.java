package com.example.lifeline.utils.log;

import com.example.lifeline.entity.OperationLog;
import com.example.lifeline.service.OperationLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LogAspect {

    @Autowired
    @Qualifier("OperationLogService")
    private OperationLogService operationLogService;

    //时间格式
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Pointcut("@annotation(com.example.lifeline.utils.log.OperationLogAnnotation)")
    public void Pointcut() {
    }

    @Around("Pointcut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        //获取当前系统时间用来计算请求花费时长
        long start = System.currentTimeMillis();
        //创建日志对象
        OperationLog operationLog = new OperationLog();
        //设置当前系统时间为操作时间
        operationLog.setOperationTime(Timestamp.valueOf(sdf.format(new Date())));

        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (RuntimeException e) {
            //如果捕获了异常，那就记录异常原因
            operationLog.setException(e.toString().substring(0, Math.min(e.toString().length(), 50)));
        }

        //请求截取完成之后，计算花费时间
        operationLog.setTimeCost(System.currentTimeMillis() - start);
        //截取请求，获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求方法
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        //根据注释和请求填入相关日志信息
        OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
        operationLog.setModule(annotation.module());//操作模板
        operationLog.setOperationType(annotation.operationType());//操作类型
        operationLog.setOperationDesc(annotation.operationDesc());//操作描述
        operationLog.setIP(request.getRemoteAddr());//操作IP地址
        operationLog.setUrl(request.getRequestURL().toString());//操作连接地址
        String uid = request.getHeader("uid");

        operationLog.setUid(request.getHeader("uid"));//操作用户名
        operationLog.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));//操作调用方法
        operationLogService.insertOperation(operationLog);//插入日志信息到数据库
        return result;
    }

    private Map<String, Object> getRequestParamsByProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        //参数名
        String[] paramNames = ((MethodSignature) proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValues = proceedingJoinPoint.getArgs();
        return buildRequestParam(paramNames, paramValues);
    }

    private Map<String, Object> buildRequestParam(String[] paramNames, Object[] paramValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            Object value = paramValues[i];
            //如果是文件对象
            if (value instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) value;
                value = file.getOriginalFilename();  //获取文件名
            }
            requestParams.put(paramNames[i], value);
        }
        return requestParams;
    }

}
