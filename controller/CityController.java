package com.bzzx.controller;

import com.bzzx.domain.CityDO;
import com.bzzx.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/24.
 */
@Controller
@Slf4j
@RequestMapping("city")
public class CityController {

    @Autowired
    private CityService cityService;

    @RequestMapping("getCity")
    public String getCityinfo() {
        System.out.println("ok");
        //System.out.println(cityService.getCity().toString());
        List<CityDO> list = cityService.listAll();
        for (CityDO city: list) {
            System.out.println(city.toString());
        }
        return "ok";
    }

    @RequestMapping("get")
    public String get() {
        CityDO city = cityService.getCity();
        log.warn("the city info is {}", city.toString());
        //System.out.println("test");
        return "ciyi";
    }

    @GetMapping("getCityByParam")
    public String getCityByParam(@RequestParam String name) {
        log.warn("the city name is {}", name);
        CityDO city = cityService.getCityByParam(name);
        log.warn("get city info by param, {}", city.toString());
        return "ok";
    }

    @PostMapping("insertCity")
    public String insertCity() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        CityDO city = CityDO.builder().id(uuid).name("帝都").state("帝都").build();
        int ret = cityService.insertCity(city);
        if(ret == 1)
            log.warn("插入成功");
        else
            log.warn("插入失败");
        return "ok";
    }

    @PostMapping("insertMany")
    public String insertMany() {
        System.out.println("开始执行方法");
        String uuid1 = UUID.randomUUID().toString().replace("-", "");
        CityDO city1 = CityDO.builder().id(uuid1).name("天津").state("天津").build();
        String uuid2 = UUID.randomUUID().toString().replace("-", "");
        CityDO city2 = CityDO.builder().id(uuid2).name("魔都").state("魔都").build();
        String uuid3 = UUID.randomUUID().toString().replace("-", "");
        CityDO city3 = CityDO.builder().id(uuid3).name("深圳").state("广东").build();
        log.warn("开始插入");
        List<CityDO> citys = new ArrayList<CityDO>();
        citys.add(city1);
        citys.add(city2);
        citys.add(city3);
        int ret = cityService.insertCityBatch(citys);
        if(ret > 1)
            log.warn("成功插入{}条数据", ret);
        else
            log.warn("插入失败");
        return "ok";
    }

    @PostMapping("updateCityInfo")
    public String updateCityInfo() {
        log.warn("开始执行更新数据");
        CityDO city = CityDO.builder().id("3").name("德州").state("山东").build();
        int ret = cityService.updateCityByProvider(city);
        if (ret == 1)
            log.warn("更新完成");
        else
            log.warn("更新失败");
        return "ok";
    }

    @PostMapping("updateCityInfo1")
    public String updateCityInfo1() {
        log.warn("开始执行更新数据，只更新state属性");
        //CityDO city = CityDO.builder().id("33d8a05d5a314d1795d8cc52e360972f").name("").state("北京").build();
        CityDO city = new CityDO();
        city.setId("33d8a05d5a314d1795d8cc52e360972f");
        city.setState("北京");
        int ret = cityService.updateCityByProvider(city);
        if (ret == 1)
            log.warn("更新完成");
        else
            log.warn("更新失败");
        return "ok";
    }

    @PostMapping("getCity")
    public String getCity() {
        log.warn("开始执行动态方法查询城市信息");
        CityDO city = new CityDO();
        city.setId("6e92dc67297547f788285dc100f33586");
        city.setName("天津");
        city = cityService.getCityByProvider(city);
        log.warn("the city info is {}", city.toString());
        return "ok";
    }
}
