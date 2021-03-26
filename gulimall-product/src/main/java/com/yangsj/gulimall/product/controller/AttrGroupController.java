package com.yangsj.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.yangsj.gulimall.product.entity.AttrEntity;
import com.yangsj.gulimall.product.service.AttrAttrgroupRelationService;
import com.yangsj.gulimall.product.service.AttrService;
import com.yangsj.gulimall.product.service.CategoryService;
import com.yangsj.gulimall.product.vo.AttrGroupRelationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.yangsj.gulimall.product.entity.AttrGroupEntity;
import com.yangsj.gulimall.product.service.AttrGroupService;
import com.yangsj.common.utils.PageUtils;
import com.yangsj.common.utils.R;



/**
 * 属性分组
 *
 * @author yangsj
 * @email shaojun47@qq.com
 * @date 2020-12-25 20:41:54
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVO> vos){

        relationService.saveBatch(vos);
        return R.ok();
    }

    @GetMapping("{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrId){
        List<AttrEntity> attrList = attrService.getRelationAttr(attrId);
        return R.ok().put("data",attrList);
    }
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(params,attrgroupId);
        return R.ok().put("page",page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params,
                    @PathVariable("catelogId") Long catelogId){
        PageUtils page = attrGroupService.queryPage(params,catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        // 用当前当前分类id查询完整路径并写入 attrGroup
        attrGroup.setCatelogPath(categoryService.findCateLogPath(attrGroup.getCatelogId()));

        return R.ok().put("attrGroup", attrGroup);
    }

    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody  AttrGroupRelationVO[] vos){
        attrService.deleteRelation(vos);
        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
