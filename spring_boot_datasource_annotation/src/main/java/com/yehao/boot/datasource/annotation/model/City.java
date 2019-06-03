package com.yehao.boot.datasource.annotation.model;

import lombok.Data;

/**
 * com.yehao.boot.datasource.annotation.model
 *
 * @author: SNJly
 * @date: 2019-06-03
 */
@Data
public class City {

    private Long id;
    private String cityCode;
    private String city;
    private String cityName;

    private String description;
    private String provinceId;
    private String province;
    private Integer level;
    private Long parentId;
    private Integer isDelete;
    /**
     * 启用
     */
    private Integer isEnable;
}


