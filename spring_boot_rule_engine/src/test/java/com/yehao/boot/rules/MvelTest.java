package com.yehao.boot.rules;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mvel2.MVEL;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules
 */
@RunWith(JUnit4.class)
public class MvelTest {

    @Test
    public void testBasic() {
        String expression = "a == null && b == nil ";
        Map<String,Object> map = new HashMap<>();
        map.put("a",null);
        map.put("b",null);

        Object object = MVEL.eval(expression,map);
        System.out.println(object);

    }

}
