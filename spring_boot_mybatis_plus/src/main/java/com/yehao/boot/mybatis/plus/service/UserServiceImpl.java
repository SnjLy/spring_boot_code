package com.yehao.boot.mybatis.plus.service;

import com.yehao.boot.mybatis.plus.dao.MyBatisUserMapper;
import com.yehao.boot.mybatis.plus.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.yehao.boot.mybatis.plus.service
 *
 * @author: SNJly
 * @date: 2019-07-09
 */
@Service
@Slf4j
public class UserServiceImpl {

    @Autowired
    private MyBatisUserMapper myBatisUserMapper;

    public List<User> selestAll(){
        return myBatisUserMapper.selectList(null);
    }
}
