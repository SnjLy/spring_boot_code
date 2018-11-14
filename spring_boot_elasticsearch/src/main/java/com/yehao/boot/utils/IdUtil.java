package com.yehao.boot.utils;

import java.util.UUID;

/**
 * @Package: com.yehao.boot.utils
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/10/12.
 */
public class IdUtil {

    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
