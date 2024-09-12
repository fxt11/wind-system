package com.example.lifeline.utils.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)//注解放置在方法的上面
@Retention(RetentionPolicy.RUNTIME)//注解可以在JVM之中存在
@Documented
public @interface OperationLogAnnotation {
    String module() default "";

    String operationType() default "";

    String operationDesc() default "";
}
