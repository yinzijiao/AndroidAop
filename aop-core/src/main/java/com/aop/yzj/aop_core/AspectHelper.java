package com.aop.yzj.aop_core;

import android.util.Log;

import com.aop.yzj.aop_core.annotation.MethodTrace;
import com.aop.yzj.aop_core.annotation.SingleClick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yin on 2017/8/16.
 */

@Aspect
public class AspectHelper {

    @Pointcut("execution(@com.aop.yzj.aop_core.annotation.MethodTrace * *(..)) && @annotation(trace)")
    public void methodTrace(MethodTrace trace) {
    }

    @Around("methodTrace(trace)")
    public Object methodTrace(final ProceedingJoinPoint proceedingJoinPoint, final MethodTrace trace) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        long start = System.nanoTime();
        Object o = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
        long stop = System.nanoTime();

        if (trace.value()) {
            StringBuilder builder = new StringBuilder();
            builder.append(className);
            builder.append("-->");
            builder.append(Modifier.toString(methodSignature.getModifiers()) + " ");
            builder.append(methodSignature.getReturnType().getSimpleName());
            if (o != null) {
                builder.append("[" + o.toString() + "]");
            }
            builder.append(" ");
            builder.append(methodName);
            builder.append("(");
            for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
                Object o1 = proceedingJoinPoint.getArgs()[i];
                builder.append(methodSignature.getParameterTypes()[i].getSimpleName() + " " + methodSignature.getParameterNames()[i] + "[" + (o1 == null ? "null" : o1.toString()) + "]");
                if (i != proceedingJoinPoint.getArgs().length - 1) {
                    builder.append(", ");
                }
            }
            builder.append(")");
            builder.append("{" + (stop - start) / 1000000D + "ms}");
            Log.i("MethodTrace", builder.toString());
        }
        return o;
    }

    @Pointcut("execution(@com.aop.yzj.aop_core.annotation.SingleClick * *(..)) && @annotation(click)")
    public void singleClick(SingleClick click) {
    }

    private Map<String, Long> map = new HashMap<>();

    @Around("singleClick(click)")
    public void singleClick(final ProceedingJoinPoint joinPoint, final SingleClick click) throws Throwable {
        Long aLong = map.get(joinPoint.getSignature().toString());
        if (aLong != null && aLong > 0) {
            long l = System.currentTimeMillis() - aLong;
            if (l > click.value()) {
                joinPoint.proceed(joinPoint.getArgs());
            }
        } else {
            joinPoint.proceed(joinPoint.getArgs());
        }
        map.put(joinPoint.getSignature().toString(), System.currentTimeMillis());
    }
}
