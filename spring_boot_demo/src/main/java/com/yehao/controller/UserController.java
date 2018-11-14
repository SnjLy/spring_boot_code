package com.yehao.controller;

import com.alibaba.fastjson.JSON;
import com.yehao.entity.User;
import com.yehao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Package: com.yehao.controller
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/7/26.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/add")
    public User addUser(String user){
        User u = JSON.parseObject(user, User.class);
        userService.insert(u);
        return  u;
    }


    @RequestMapping("/get")
    public User getUser(Integer id){
        User u = userService.selectByPrimaryKey(id);
        return  u;
    }


}
