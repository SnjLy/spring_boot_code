package com.yehao.boot.rules;

import com.googlecode.aviator.AviatorEvaluator;
import com.yehao.boot.rules.aviator.MultiplyFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : LiuYong at 2020-08-24
 * @package: com.yehao.boot.rules
 */
@RunWith(JUnit4.class)
public class AviatorTest {


    @Test
    public void testBasic() {
        // exec执行方式，无需传递Map格式
        String age = "18";
        System.out.println(AviatorEvaluator.exec("'His age is '+ age +'!'", age));


        // execute执行方式，需传递Map格式
        Map<String, Object> map = new HashMap<>();
        map.put("age", "18");
        System.out.println(AviatorEvaluator.execute("'His age is '+ age +'!'", map));


        Map<String, Object> map2 = new HashMap<>();
        map2.put("s1", "123qwer");
        map2.put("s2", "123");
        System.out.println(AviatorEvaluator.execute("string.startsWith(s1,s2)", map2));
    }

    @Test
    public void testFunction() {
        // 注册自定义函数
        AviatorEvaluator.addFunction(new MultiplyFunction());
        // 方式1
        System.out.println(AviatorEvaluator.execute("multiply(12.23, -2.3)"));
        // 方式2
        Map<String, Object> params = new HashMap<>();
        params.put("a", 12.23);
        params.put("b", -2.3);
        System.out.println(AviatorEvaluator.execute("multiply(a, b)", params));


        User user = new User(1,"jack","18");
        Map<String, Object> env = new HashMap<>();
        env.put("user", user);
        String result = (String) AviatorEvaluator.execute(" '[user id='+ user.id + ',name='+user.name + ',age=' +user.age +']' ", env);
        String uid = (String) AviatorEvaluator.execute("user.id>0? 'yes':'no'", env);
        System.out.println(result + uid);

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class User {
        private int id;
        private String name;
        private String age;
    }
}
