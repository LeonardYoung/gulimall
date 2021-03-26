package com.yangsj.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.gulimall.product.entity.AttrEntity;
import com.yangsj.gulimall.product.vo.AttrGroupRelationVO;
import com.yangsj.gulimall.product.vo.AttrRespVo;
import com.yangsj.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 20:41:54
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attrVo);

    List<AttrEntity> getRelationAttr(Long attrId);

    void deleteRelation(AttrGroupRelationVO[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

