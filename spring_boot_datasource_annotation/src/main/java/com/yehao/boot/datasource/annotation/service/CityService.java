package com.yehao.boot.datasource.annotation.service;

import com.yehao.boot.datasource.annotation.dao.CityDao;
import com.yehao.boot.datasource.annotation.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * com.yehao.boot.datasource.annotation.service
 *
 * @author: SNJly
 * @date: 2019-06-03
 */
@Service
public class CityService {

    @Autowired
    private CityDao cityDao;


    public int insertCity(City city){
        Assert.hasLength(city.getCity(), "城市不能为空");
        Assert.hasLength(city.getCityName(), "城市名称不能为空");
        Assert.hasLength(city.getCityCode(), "城市编码不能为空");
        return cityDao.insertCity(city);
    }


    public List<City> getEnabelCitys(){
        return cityDao.findEnableCity();
    }

    public City getCityByName(String cityName){
        Assert.hasLength(cityName, "城市名称不能为空");
        return cityDao.findByName(cityName);
    }

}
