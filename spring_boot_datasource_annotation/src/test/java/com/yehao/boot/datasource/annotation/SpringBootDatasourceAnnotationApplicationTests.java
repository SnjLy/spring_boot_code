package com.yehao.boot.datasource.annotation;

import com.yehao.boot.datasource.annotation.model.City;
import com.yehao.boot.datasource.annotation.service.CityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SpringBootDatasourceAnnotationApplicationTests {

	@Test
	public void contextLoads() {
	}


	@Autowired
	private CityService cityService;

	@Test
	public void save(){
		City city = new City();
		city.setCity("beijing");city.setCityName("北京市"); city.setDescription("中国北京市");city.setProvinceId("1");
		city.setProvince("北京市"); city.setLevel(1); city.setParentId(0L);
		city.setCityCode(UUID.randomUUID().toString());
		cityService.insertCity(city);
		Assert.assertNotNull(city.getId());
	}


	@Test
	public void getCity(){
		String name = "北京市";
		City city = cityService.getCityByName(name);
		Assert.assertNotNull(city);
		Assert.assertNotNull(city.getId());
	}


	@Test
	public void allCitys(){
		List<City> enabelCitys = cityService.getEnabelCitys();
		Assert.assertNotNull(enabelCitys);
	}

}
