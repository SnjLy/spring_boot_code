package com.yehao.boot.rules.function.service;

import com.yehao.boot.rules.function.FunctionPredicate;

import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules.function.service
 */
public class CustomerFunctionImpl implements FunctionPredicate<String, Map<String, Object>> {

    @Override
    public boolean apply(String s, Map<String, Object> map) {
        return map.containsKey(s);
    }

}
