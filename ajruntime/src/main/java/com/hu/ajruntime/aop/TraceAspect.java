package com.hu.ajruntime.aop;

import com.hu.ajruntime.annotation.DebugTrace;
import com.hu.ajruntime.util.DebugLog;
import com.hu.ajruntime.util.StopWatch;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

@Aspect
public class TraceAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.hu.ajruntime.annotation.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.hu.ajruntime.annotation.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();
        DebugTrace debugTrace = methodSignature.getMethod().getAnnotation(DebugTrace.class);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        // 被注解的方法在这一行代码被执行
        Object result = joinPoint.proceed();
        stopWatch.stop();

        DebugLog.log(className, buildLogMessage(methodName, stopWatch.getTotalTimeMillis(), debugTrace.value()));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName     A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @param otherStr       其他信息
     * @return A string representing message.
     */
    private static String buildLogMessage(String methodName, long methodDuration, String otherStr) {
        StringBuilder message = new StringBuilder();
        message.append("【记录日志-->[")
                .append("methodName:")
                .append(methodName)
                .append("]-[methodDuration:")
                .append(methodDuration)
                .append("ms]-[others:")
                .append(otherStr)
                .append("]<--】");
        return message.toString();
    }
}