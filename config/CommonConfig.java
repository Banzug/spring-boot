package com.bzzx.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 * 配置文件属性对应实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
@Builder
@Validated
@Configuration
@ConfigurationProperties(prefix = "common")
public class CommonConfig {

    @NotNull
    private String username;

    @NotNull
    private String host;

    @NotNull
    private int port;

    private String password;

    private List<String> list;

    private Map<String, String> maps;

}
