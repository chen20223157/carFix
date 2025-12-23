package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}

