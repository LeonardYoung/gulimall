package com.yangsj.gulimall.order.dao;

import com.yangsj.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 22:14:54
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
