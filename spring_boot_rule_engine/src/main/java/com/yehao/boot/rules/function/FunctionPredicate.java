package com.yehao.boot.rules.function;

import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules
 */
@FunctionalInterface
public interface FunctionPredicate<T, C> {

    boolean apply(T t, C c);
}


