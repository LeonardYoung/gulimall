package com.yangsj.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.gulimall.coupon.entity.SpuBoundsEntity;

import java.util.Map;

/**
 * 商品spu积分设置
 *
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 22:00:30
 */
public interface SpuBoundsService extends IService<SpuBoundsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

