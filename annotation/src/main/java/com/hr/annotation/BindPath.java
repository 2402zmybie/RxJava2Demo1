package com.hr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
//编辑时技术  所以让注解存活的周期为编译期
//声明注解的生命周期(源码期 < 编译期 < 运行期)
@Retention(RetentionPolicy.CLASS)
public @interface BindPath {
    String value();
}
