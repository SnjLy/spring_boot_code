package com.yehao.boot.rules.function.service;

import com.yehao.boot.rules.function.FunctionPredicate;

import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules.function.service
 */
public class FunctionImpl{

   public FunctionPredicate<Integer, Map<String, Object>> fp = (integer, map) -> {
       Integer key = (Integer) map.get("key");
       return key.equals(integer);
   };
}
