package com.yangsj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.gulimall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 20:41:54
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();

    void removeMenusByids(List<Long> asList);
}

