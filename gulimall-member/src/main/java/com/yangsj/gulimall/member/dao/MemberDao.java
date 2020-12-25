package com.yangsj.gulimall.member.dao;

import com.yangsj.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 22:09:13
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
