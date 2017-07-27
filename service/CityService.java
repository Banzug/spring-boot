package com.bzzx.service;

import com.bzzx.domain.CityDO;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */
public interface CityService {

    /**
     * 获取所所有的城市信息。
     * @return 返回城市信息列表
     */
    List<CityDO> listAll();

    CityDO getCity();

    CityDO getCityByParam(String cityName);

    int insertCity(CityDO city);

    int insertCityBatch(List<CityDO> citys);

    int updateCityByProvider(CityDO cityDO);

    CityDO getCityByProvider(CityDO city);
}
