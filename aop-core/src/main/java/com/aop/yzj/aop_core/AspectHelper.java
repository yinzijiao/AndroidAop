package com.aop.yzj.aop_core;

import android.util.Log;

import com.aop.yzj.aop_core.annotation.MethodTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

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
            builder.append("ClassName-->" + className);
            builder.append('\n');
            builder.append("MethodName-->" + methodName);
            builder.append('\n');
            builder.append("MethodArgs-->");
            for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
                Object o1 = proceedingJoinPoint.getArgs()[i];
                if (o1 != null) {
                    builder.append("(" + o1.getClass().getSimpleName() + "-" + o1.toString() + ") ");
                }
            }
            builder.append('\n');
            if (o != null) {
                builder.append("MethodReturn-->" + o.getClass().getSimpleName() + "-->" + o.toString());
                builder.append('\n');
            }
            builder.append("MethodDuration-->" + (stop - start) / 1000000D + "ms");
            builder.append('\n');
            Log.i("MethodTrace", builder.toString());
        }
        return o;
    }

}
