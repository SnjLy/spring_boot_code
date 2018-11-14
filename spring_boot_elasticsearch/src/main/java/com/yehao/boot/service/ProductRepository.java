package com.yehao.boot.service;

import com.yehao.boot.model.ProductModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @Package: com.yehao.boot.service
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/10/12.
 */
@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductModel, Long>{
}
