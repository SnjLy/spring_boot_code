package com.yehao.boot.rules.aviator;

import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules.aviator
 */
public class MultiplyFunction extends AbstractFunction {
    @Override
    public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {

        double num1 = FunctionUtils.getNumberValue(arg1, env).doubleValue();
        double num2 = FunctionUtils.getNumberValue(arg2, env).doubleValue();
        return new AviatorDouble(num1 * num2);
    }

    @Override
    public String getName() {
        return "multiply";
    }

}