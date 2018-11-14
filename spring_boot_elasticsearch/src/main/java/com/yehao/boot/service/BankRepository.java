package com.yehao.boot.service;

import com.yehao.boot.model.BankModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Package: com.yehao.boot.service
 * @Description:  bank repository
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/11/2.
 */
@Repository
public interface BankRepository extends ElasticsearchRepository<BankModel, Long>{
}
