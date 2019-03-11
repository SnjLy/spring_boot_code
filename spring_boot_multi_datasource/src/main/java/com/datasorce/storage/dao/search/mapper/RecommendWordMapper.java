package com.datasorce.storage.dao.search.mapper;

import com.datasorce.storage.dao.search.entity.RecommendWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RecommendWordMapper {
	public List<RecommendWord> queryCityHotWords(@Param("city") int city);
	public List<Integer> queryCitys();
}
