package com.bzzx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.StringUtils;
import com.bzzx.datasource.DruidDatasource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/7/25.
 * 注入druid数据连接池到spring boot
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(DruidDatasource.class)
@MapperScan("com.bzzx.mapper")
public class DatabaseSourceConfig implements EnvironmentAware{

    /**
     * 注入自定义druid数据库连接池配置属性
     */
    @Autowired
    private DruidDatasource datasourceProperty;

    private Environment environment;
    private RelaxedPropertyResolver propertyResolver;

    public void setEnvironment(Environment environment) {
        this.environment = environment;
        //使用RelaxedPropertyResolver获取yaml中的数据库连接配置信息
        this.propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public DruidDataSource dataSource() throws SQLException{
        if (StringUtils.isEmpty(propertyResolver.getProperty("url"))) {
            System.out.println("Your database connection pool configuration is incorrect!"
                    + " Please check your Spring profile, current profiles are:"
                    + Arrays.toString(environment.getActiveProfiles()));
            throw new ApplicationContextException(
                    "Database connection pool is not configured correctly");
        }
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(propertyResolver.getProperty("driver-class-name"));
        dataSource.setUrl(propertyResolver.getProperty("url"));
        dataSource.setUsername(propertyResolver.getProperty("username"));
        dataSource.setPassword(propertyResolver.getProperty("password"));
        dataSource.setMaxActive(datasourceProperty.getMaxActive());
        dataSource.setFilters(datasourceProperty.getFilters());
        dataSource.setMinIdle(datasourceProperty.getMinIdle());
        dataSource.setInitialSize(datasourceProperty.getInitialSize());
        dataSource.setMaxWait(datasourceProperty.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(datasourceProperty.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(datasourceProperty.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(datasourceProperty.getValidationQuery());
        dataSource.setPoolPreparedStatements(datasourceProperty.isPoolPreparedStatements());
        dataSource.setTestOnReturn(datasourceProperty.isTestOnReturn());
        dataSource.setTestWhileIdle(datasourceProperty.isTestWhileIdle());
        dataSource.setTestOnBorrow(datasourceProperty.isTestOnBorrow());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(datasourceProperty.getMaxPoolPreparedStatementPerConnectionSize());
        return dataSource;
    }

    /**
     * 可在此处注入mybatis分页插件
     * @return
     * @throws Exception
     */
    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() throws SQLException{
        return new DataSourceTransactionManager(dataSource());
    }
}
