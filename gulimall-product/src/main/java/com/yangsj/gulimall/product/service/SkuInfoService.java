package com.yangsj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.gulimall.product.entity.SkuInfoEntity;

import java.util.Map;

/**
 * sku信息
 *
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 20:41:54
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(SkuInfoEntity skuInfoEntity);
}

