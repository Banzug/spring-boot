package com.bzzx.provider;

import com.bzzx.domain.CityDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26.
 * 自定义动态sql。用于批量操作
 */
@Slf4j
public class CityMapperProvider {

    /**
     * 批量插入城市信息
     * @return
     */
    public String insertBatch(Map<String, Object> map){
        List<CityDO> lists = (List<CityDO>)map.get("citys");
        StringBuilder sql = new StringBuilder();
        sql.append("insert into city(id, city_name, city_state) ");
        sql.append("values ");
        //由于在Citymapper.insertCityBatch方法中使用了@param,所以用自定义的key获取数据。否则如果没有自定义key
        //则使用list做key获取数据
        MessageFormat mf = new MessageFormat("(#'{'citys[{0}].id}, #'{'citys[{0}].name}, #'{'citys[{0}].state})");
        for (int i=0; i<lists.size(); i++) {
            sql.append(mf.format(new Object[]{i}));
            if(i < lists.size() - 1) {
                sql.append(",");
            }
        }
        log.info("the sql statement is {}", sql.toString());
        return sql.toString();
    }

    /**
     * 动态更新城市信息
     * @return
     */
    public String dynamicUpdate(final CityDO city) {
        return new SQL(){
            {
                UPDATE("city ");
                if (city.getName() != null && city.getName().trim() != "")
                {
                    SET("city_name = #{name}");
                }
                if (city.getState() != null && city.getState().trim() != ""){
                    SET("city_state = #{state}");
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }

    /**
     * 动态查询城市信息
     * @return
     */
    public String dynamicQuery(final CityDO city) {
        return new SQL(){
            {
                SELECT("city_name name, city_state state");
                FROM("city");
                if (city.getId() != null){
                    WHERE("id = #{id}");
                }
                if (city.getName() != null){
                    WHERE("city_name = #{name}");
                }
            }
        }.toString();
    }
}
