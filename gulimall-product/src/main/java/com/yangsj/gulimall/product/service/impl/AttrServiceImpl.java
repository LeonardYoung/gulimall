package com.yangsj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yangsj.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.yangsj.gulimall.product.dao.AttrGroupDao;
import com.yangsj.gulimall.product.dao.CategoryDao;
import com.yangsj.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.yangsj.gulimall.product.entity.AttrGroupEntity;
import com.yangsj.gulimall.product.entity.CategoryEntity;
import com.yangsj.gulimall.product.service.CategoryService;
import com.yangsj.gulimall.product.vo.AttrRespVo;
import com.yangsj.gulimall.product.vo.AttrVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.common.utils.Query;

import com.yangsj.gulimall.product.dao.AttrDao;
import com.yangsj.gulimall.product.entity.AttrEntity;
import com.yangsj.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
//        attrEntity.setAttrName(attr.getAttrName());
        BeanUtils.copyProperties(attr,attrEntity);
        //1、保存基本数据
        this.save(attrEntity);
        //2、保存关联关系
//        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            this.relationDao.insert(relationEntity);
//        }
    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
//        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<>();
        if(catelogId!=0){
            queryWrapper.eq("catelog_id",catelogId);
        }
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //1、设置分类和分组的名字
//            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

//            }


            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrRespVo attrRespVo = new AttrRespVo();
        BeanUtils.copyProperties(attrEntity,attrRespVo);

        AttrAttrgroupRelationEntity  attrAttrgroupRelationEntity = this.relationDao.selectOne(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
        if(attrAttrgroupRelationEntity!=null && attrAttrgroupRelationEntity.getAttrGroupId() != null){
            Long id = attrAttrgroupRelationEntity.getAttrGroupId();
            attrRespVo.setAttrGroupId(id);
            AttrGroupEntity attrGroupEntity = this.attrGroupDao.selectOne(
                    new QueryWrapper<AttrGroupEntity>().eq("attr_group_id",id));
            if(attrGroupEntity != null && attrGroupEntity.getAttrGroupName() != null){
                attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }


        Long catelogId = attrEntity.getCatelogId();
        Long[] cateLogPath = categoryService.findCateLogPath(catelogId);
        attrRespVo.setCatelogPath(cateLogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(categoryEntity != null && categoryEntity.getName() != null){
            attrRespVo.setCatelogName(categoryEntity.getName());
        }

        return attrRespVo;
    }

    @Override
    public void updateAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo,attrEntity);
        this.updateById(attrEntity);

        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        relationEntity.setAttrId(attrEntity.getAttrId());
        relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
        this.relationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
    }

}
