package com.aop.yzj.aop_core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleClick {
    /**
     * time
     *
     * @return
     */
    long value() default 500;
}
