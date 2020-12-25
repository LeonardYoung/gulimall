package com.yangsj.gulimall.product.dao;

import com.yangsj.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 20:41:54
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
