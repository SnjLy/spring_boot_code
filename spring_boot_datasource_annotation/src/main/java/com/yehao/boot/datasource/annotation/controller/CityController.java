package com.yehao.boot.datasource.annotation.controller;

import com.yehao.boot.datasource.annotation.model.City;
import com.yehao.boot.datasource.annotation.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * com.yehao.boot.datasource.annotation.controller
 *
 * @author: SNJly
 * @date: 2019-06-03
 */
@RequestMapping("city")
@RestController
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("/save")
    public String save(City city){
        city.setCityCode(UUID.randomUUID().toString());
        cityService.insertCity(city);
        return city.toString();
    }


    @RequestMapping("/getCity")
    public City getCity(String name){
        return cityService.getCityByName(name);
    }


    @RequestMapping("/enableCitys")
    public List<City> allCitys(){
        return cityService.getEnabelCitys();
    }
}
