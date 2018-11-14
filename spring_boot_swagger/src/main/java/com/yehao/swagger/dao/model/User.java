package com.yehao.swagger.dao.model;

import lombok.Data;

/**
 * @Package: com.yehao.swagger.dao.model
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/7/31.
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String address;
    private String major;
    private Integer sex;
}
