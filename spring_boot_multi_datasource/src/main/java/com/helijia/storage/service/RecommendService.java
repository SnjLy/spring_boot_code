package com.helijia.storage.service;

import com.helijia.storage.dao.search.entity.RecommendWord;
import com.helijia.storage.dao.search.mapper.RecommendWordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Package: com.helijia.storage.service
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
