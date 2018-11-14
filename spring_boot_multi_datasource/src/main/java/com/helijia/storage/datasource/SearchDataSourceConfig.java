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
@MapperScan(basePackages = SearchDataSourceConfig.PACKAGES, sqlSessionFactoryRef = "searchSqlSessionFactory")
public class SearchDataSourceConfig {
    public static final Logger LOGGER = LoggerFactory.getLogger(SearchDataSourceConfig.class);

    static final String PACKAGES = "com.helijia.storage.dao.search.mapper";

    private static final String MAPPER_LOCATE = "classpath:sql/search/**.xml";

    @ConfigurationProperties(prefix = "search.datasource")
    @Bean(name = "searchDatasource")
    public DruidDataSource searchDataSource(){
        return new DruidDataSource();
    }



    @Bean(name="searchTransactionManager")
    public DataSourceTransactionManager searchTransactionManager(){
        return new DataSourceTransactionManager(searchDataSource());
    }


    @Bean(name = "searchSqlSessionFactory")
    public SqlSessionFactory searchSqlSessionFactory(){
        final SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(searchDataSource());

        try {
            sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATE));
            return sessionFactoryBean.getObject();
        } catch (Exception e) {
            LOGGER.error("配置search数据库的SqlSessionFactory失败，error:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
