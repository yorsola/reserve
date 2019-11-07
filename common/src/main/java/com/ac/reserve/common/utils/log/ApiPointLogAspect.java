package com.ac.reserve.common.utils.log;

import com.ac.reserve.common.util.JSONUtil;
import com.ac.reserve.common.utils.HttpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class ApiPointLogAspect {

    private static final Logger mLogger = LoggerFactory.getLogger(ApiPointLogAspect.class);

    private static final String LogFormat_SND = "[SND]|%s|%s|%s|%s|%s";
    private static final String LogFormat_RCV = "[RCV]|%s|%s|%s|%s";
    private static final String LogFormat_EX = "[EXP]|%s|%s|%s|%s";
    private static final String LogFormat_TIME = "[TIME]|%s|%s|%s|%s";

    public static final ThreadLocal<ThreadLogInfo> threadLocal = new ThreadLocal<ThreadLogInfo>();

    @Pointcut("execution(public * com.ac.reserve..*.*Controller.*(..))")
    public void apiLog() {
        // Just a pointCut function
    }

    @Before("apiLog()")
    public void before(JoinPoint point) {
        // TODO:
    }

    @Around("apiLog()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object res = null;

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest req = attr.getRequest();
        String url = req.getMethod() + " " + req.getRequestURI();
        String ipAddress = req.getHeader("x-forwarded-for");
        String header = getHeader(HttpUtil.getHeadersInfoMap(req));
        String methodName = pjp.getSignature().getName();
        String paramNames = getParams(pjp);
        Long startTime = System.currentTimeMillis();
        threadLocal.set(new ThreadLogInfo(url, ipAddress, header, methodName, paramNames, startTime, null));
        mLogger.trace(String.format(LogFormat_SND, ipAddress, url, methodName, header, paramNames));
        try {
            res = pjp.proceed();
            Long endTime = System.currentTimeMillis();
            threadLocal.get().setEndTime(endTime);
            mLogger.trace(String.format(LogFormat_RCV, ipAddress, url, methodName, JSONUtil.toJSONString(res)));
        } catch (Throwable e) {
            Long endTime = System.currentTimeMillis();
            threadLocal.get().setEndTime(endTime);
            throw e;
        }

        return res;
    }

    @After("apiLog()")
    public void after() {
        ThreadLogInfo info = threadLocal.get();
        mLogger.trace(String.format(LogFormat_TIME, info.getEndTime() - info.getStartTime(), info.getIpAddress(),
                info.getUrl(), info.getMethodName()));
    }

    @AfterThrowing(pointcut = "apiLog()", throwing = "e")
    public void afterThrow(JoinPoint point, Throwable e) {
        String methodName = point.getSignature().getName();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String msg = sw.toString();
        Long endTime = System.currentTimeMillis();
        threadLocal.get().setEndTime(endTime);
        ThreadLogInfo info = threadLocal.get();
        mLogger.trace(String.format(LogFormat_EX, info.getIpAddress(), info.getUrl(), methodName, msg));
    }

    @AfterReturning("apiLog()")
    public void afterReturning() {

    }

    private String getHeader(Map<String, String> headerMap) {
        return "header:" + JSONUtil.toJSONString(headerMap) + "\n";
    }

    private String getParams(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] names = u.getParameterNames(method);
        Class<?>[] types = method.getParameterTypes();
        Object[] args = point.getArgs();
        String res = "";
        if (args != null && args.length > 0) {
            String value = null;
            try {
                value = JSONUtil.toJSONString(args[0]);
            } catch (Exception e) {
                value = args[0].toString();
            } finally {
                res = types[0].getSimpleName() + " " + names[0] + "<" + value + ">";
            }
            for (int i = 1; i < names.length; i++) {
                value = null;
                try {
                    value = JSONUtil.toJSONString(args[i]);
                } catch (Exception e) {
                    value = args[i].toString();
                } finally {
                    res += ", " + types[i].getSimpleName() + " " + names[i] + "<" + value + ">";
                }
            }
        }
        return res;

    }
}
