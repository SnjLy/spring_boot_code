package com.datasorce.storage.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;

/**
 * @Package: com.datasource.storage.datasource
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/8/29.

*/

@Configuration
@EnableTransactionManagement
@ConditionalOnBean({HljDataSourceConfig.class, SearchDataSourceConfig.class})
public class DataSourcesAutoConfiguration implements TransactionManagementConfigurer {

    @Resource
    private HljDataSourceConfig hljDataSourceConfig;

    @Resource
    private SearchDataSourceConfig searchDataSourceConfig;

/**
     * 配置分布式事务管理

*/
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new ChainedTransactionManager(hljDataSourceConfig.hljTransactionManager(), searchDataSourceConfig.searchTransactionManager());
    }
}
