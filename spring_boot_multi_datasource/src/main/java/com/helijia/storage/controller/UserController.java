package com.helijia.storage.controller;

import com.helijia.storage.dao.hlj.entity.UserCollect;
import com.helijia.storage.dao.search.entity.RecommendWord;
import com.helijia.storage.service.RecommendService;
import com.helijia.storage.service.UserService;
import org.assertj.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Package: com.helijia.storage.controller
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/8/29.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @Autowired
    private RecommendService recommendService;

    @RequestMapping("/getCollect")
    public List<UserCollect> getUser(String userId){
        Date date = new Date();
        List<UserCollect> collectList = userService.queryUserCollect(userId, DateUtil.yesterday());
        System.out.println(collectList);
        return collectList;
    }

    @RequestMapping("/recommends")
    public List<RecommendWord> getRecomendwords(Integer city){
        return recommendService.getRecommendWords(city);
    }
}
