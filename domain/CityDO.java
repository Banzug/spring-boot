package com.bzzx.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/7/23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "city")
public class CityDO {

    /***
     * id属性
     */
    @Id
    private String id;

    /**
     * 城市名
     */
    @Column(name = "city_name")
    private String name;

    /**
     * 州
     */
    @Column(name = "city_state")
    private String state;
}
