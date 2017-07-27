package com.bzzx.mapper;

import com.bzzx.domain.CityDO;
import com.bzzx.provider.CityMapperProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 * city实体类对应的mybatis接口
 */
@Mapper
public interface CityMapper{

    /**
     * 获取所有城市信息
     * @return 返回城市信息列表
     */
    @Select({"select id, city_name, city_state from city"})
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "city_name", property = "name"),
            @Result(column = "city_state", property = "state")
    })
    List<CityDO> listAll();

    /**
     * 固定查询
     * @return
     */
    @Select({"SELECT * FROM city WHERE city_name = '柳州'"})
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "city_name", property = "name"),
            @Result(column = "city_state", property = "state")
    })
    CityDO getCity();

    /**
     * 根据城市名查询城市信息
     * @param cityName 城市名
     * @return 返回城市完整的信息
     */
    @Select({"SELECT id, city_name name, city_state state FROM city WHERE city_name = #{name}"})
    CityDO getCityByParam(@Param("name") String cityName);

    /**
     * 查询城市一条信息
     * @param city 城市对象
     * @return 插入成功返回1， 否则返回0
     */
    @Insert({"insert into city(id, city_name, city_state) values(#{id}, #{name}, #{state})"})
    int insertCity(CityDO city);

    /**
     * 批量插入。
     * MyBatis会把insertCityBatch方法中的List类型的参数存入一个Map中, 默认的key是"list", 可以用@Param注解自定义名称,
     * MyBatis在调用@InsertProvide指定的方法时将此map作为参数传入, 所有代码中使用List<User> users = (List<User>) map.get("list");获取list参数.
     * @return
     * @see http://f0rb.iteye.com/blog/1207384
     */
    @InsertProvider(type = CityMapperProvider.class, method = "insertBatch")
    int insertCityBatch(@Param("citys") List<CityDO> citys);

    /**
     * 使用动态sql完成数据的更新（含有if判断）。
     * @param cityDO 传入城市对象
     * @return 如果完成更新返回1， 否则返回0
     */
    @UpdateProvider(type = CityMapperProvider.class, method = "dynamicUpdate")
    int updateCityByProvider(CityDO cityDO);

    /**
     * 使用动态sql查询城市信息
     * @param city 城市对象
     * @return 返回城市信息
     */
    @SelectProvider(type = CityMapperProvider.class, method = "dynamicQuery")
    CityDO getCityByProvider(CityDO city);

}
