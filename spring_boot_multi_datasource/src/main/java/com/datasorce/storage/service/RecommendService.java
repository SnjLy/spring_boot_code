package com.datasorce.storage.service;

import com.datasorce.storage.dao.search.entity.RecommendWord;
import com.datasorce.storage.dao.search.mapper.RecommendWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.datasource.storage.service
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/8/29.
 */
@Service
public class RecommendService {

    @Autowired
    private RecommendWordMapper recommendWordMapper;

    public List<RecommendWord> getRecommendWords(int city){
        return recommendWordMapper.queryCityHotWords(city);
    }
}
