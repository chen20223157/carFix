package com.carfix.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carfix.entity.Review;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评价Mapper接口
 * 
 * @author CarFix Team
 */
@Mapper
public interface ReviewMapper extends BaseMapper<Review> {
}

