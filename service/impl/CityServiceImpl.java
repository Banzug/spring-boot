package com.bzzx.service.impl;

import com.bzzx.domain.CityDO;
import com.bzzx.mapper.CityMapper;
import com.bzzx.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
@Service
public class CityServiceImpl implements CityService{

    /**
     * 注入映射
     */
    @Autowired
    private CityMapper cityMapper;

    /**
     * 加载城市信息
     * @return 返回城市信息列表
     */
    public List<CityDO> listAll() {
        return cityMapper.listAll();
    }

    public CityDO getCity() {
        return cityMapper.getCity();
    }

    public CityDO getCityByParam(String cityName) {
        return cityMapper.getCityByParam(cityName);
    }

    public int insertCity(CityDO city) {
        return cityMapper.insertCity(city);
    }

    public int insertCityBatch(List<CityDO> citys) {
        return cityMapper.insertCityBatch(citys);
    }

    public int updateCityByProvider(CityDO cityDO) {
        return cityMapper.updateCityByProvider(cityDO);
    }

    public CityDO getCityByProvider(CityDO city) {
        return cityMapper.getCityByProvider(city);
    }
}
