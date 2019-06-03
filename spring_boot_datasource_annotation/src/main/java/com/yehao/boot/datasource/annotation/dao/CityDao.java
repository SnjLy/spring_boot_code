package com.yehao.boot.datasource.annotation.dao;

import com.yehao.boot.datasource.annotation.model.City;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * com.yehao.boot.datasource.annotation.dao
 *
 * @author: SNJly
 * @date: 2019-06-03
 */
// 标志为 Mybatis 的 Mapper
@Mapper
@Component
public interface CityDao {

    /**
     * 根据城市名称，查询城市信息
     *
     * @param cityName 城市名
     */
    @Select("SELECT * FROM city where city_name = #{cityName}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "provinceId", column = "province_id"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "description", column = "description"),
            @Result(property = "city", column = "city"),
            @Result(property = "level", column = "level"),
            @Result(property = "isEnable", column = "is_enable"),
            @Result(property = "isDelete", column = "is_delete"),
            @Result(property = "province", column = "province")
    })
    City findByName(@Param("cityName") String cityName);


    @Insert("insert into city(city_code, city, city_name, province, province_id, parent_id, level) " +
            "values(#{cityCode}, #{city}, #{cityName}, #{province}, #{provinceId}, #{parentId}, #{level})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insertCity(City city);



    /**
     * 返回结果映射
     */
    @Select("SELECT * FROM city where is_enable = 1 and is_delete=0")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "provinceId", column = "province_id"),
            @Result(property = "cityName", column = "city_name"),
            @Result(property = "description", column = "description"),
            @Result(property = "city", column = "city"),
            @Result(property = "level", column = "level"),
            @Result(property = "province", column = "province")
    })
    List<City> findEnableCity();

}