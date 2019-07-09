package com.yehao.boot.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * com.yehao.boot.mybatis.plus.entity
 *
 * @author: SNJly
 * @date: 2019-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String userName;

    private String password;

    private Integer age;

    private String name;

    private String address;

    private String major;

    private Integer sex;

    @TableField(value = "update_time")
    private Date updateTime;
    @TableField(value = "add_time")
    private Date addTime;
}
