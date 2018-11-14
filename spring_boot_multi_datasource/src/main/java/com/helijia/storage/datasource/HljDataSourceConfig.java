package com.helijia.storage.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @Package: com.helijia.storage.datasources
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/8/28.
 */
@Configuration
@MapperScan(basePackages = HljDataSourceConfig.PACKAGES, sqlSessionFactoryRef = "hljSqlSessionFactory")
public class HljDataSourceConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(HljDataSourceConfig.class);

    static final String PACKAGES = "com.helijia.storage.dao.hlj.mapper";

    private static final String MAPPER_LOCATE = "classpath:sql/hlj/**.xml";

    @ConfigurationProperties(prefix = "hlj.datasource")
    @Primary
    @Bean(name = "hljDatasource")
    public DruidDataSource hljDataSource(){
        return new DruidDataSource();
    }



    @Bean(name="hljTransactionManager")
    @Primary
    public DataSourceTransactionManager hljTransactionManager(){
        return new DataSourceTransactionManager(hljDataSource());
    }


    @Primary
    @Bean(name = "hljSqlSessionFactory")
    public SqlSessionFactory hljSqlSessionFactory(){
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(hljDataSource());

        try {
            sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATE));
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            LOGGER.error("配置主库的SqlSessionFactory失败，error:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
