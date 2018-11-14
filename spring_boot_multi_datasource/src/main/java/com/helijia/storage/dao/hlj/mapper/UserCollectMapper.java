package com.helijia.storage.dao.hlj.mapper;


import com.helijia.storage.dao.hlj.entity.UserCollect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author yehao
 */
@Mapper
@Component
public interface UserCollectMapper {
	List<UserCollect> queryUserCollect(@Param("userId") String userId, @Param("nearCollect") Date nearCollect);


}
