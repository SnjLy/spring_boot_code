package com.yehao.service;

import com.alibaba.fastjson.JSON;
import com.yehao.entity.User;
import com.yehao.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.yehao.service
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/7/26.
 */
@Service("userService")
public class UserService {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    public int deleteByPrimaryKey(Integer id){
        return userMapper.deleteByPrimaryKey(id);
    }


    public User insert(User record){
        int t = 0;
        if (null != record){
            LOGGER.info("add user:" + JSON.toJSONString(record));
           t = userMapper.insert(record);
        }
        if (t > 0){
            return record;
        }
        return null;
    }

    public User selectByPrimaryKey(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(User record){
        return userMapper.updateByPrimaryKeySelective(record);
    }

}
