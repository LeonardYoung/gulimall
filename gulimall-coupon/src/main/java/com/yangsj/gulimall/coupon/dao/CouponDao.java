package com.yangsj.gulimall.coupon.dao;

import com.yangsj.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 22:00:30
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
