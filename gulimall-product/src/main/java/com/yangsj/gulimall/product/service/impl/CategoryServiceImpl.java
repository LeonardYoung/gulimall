package com.yangsj.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.common.utils.Query;

import com.yangsj.gulimall.product.dao.CategoryDao;
import com.yangsj.gulimall.product.entity.CategoryEntity;
import com.yangsj.gulimall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> entities = baseMapper.selectList(null);
        List<CategoryEntity> levelOne = entities.stream()
                .filter(entity -> entity.getParentCid() == 0)
                .map(menu ->{
                    menu.setChildren(this.getChildrens(menu,entities));
                    return menu;
                })
                .sorted((menu1,menu2)->{
                    return (menu1.getSort()==null ? 0 :menu1.getSort()) - (menu2.getSort()==null ? 0 :menu2.getSort());
                })
                .collect(Collectors.toList());
        return levelOne;
    }

    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all){
        List<CategoryEntity> collect = all.stream()
                .filter(menu -> {return menu.getParentCid().equals(root.getCatId());})
                .map(menu -> {
                    menu.setChildren(this.getChildrens(menu, all));
                    return menu;
                })
                .sorted((menu1, menu2) -> {
                    return (menu1.getSort()==null ? 0 :menu1.getSort()) - (menu2.getSort()==null ? 0 :menu2.getSort());
                })
                .collect(Collectors.toList());

        return collect;
    }

    @Override
    public void removeMenusByids(List<Long> asList) {
        // todo 检查删除的菜单，是否被别的地方引用
        baseMapper.deleteBatchIds(asList);
    }
}
