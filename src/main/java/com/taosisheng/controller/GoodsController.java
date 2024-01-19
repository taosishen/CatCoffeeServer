package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.Classification;
import com.taosisheng.dtoentity.Goods;
import com.taosisheng.dtoentity.R;
import com.taosisheng.dtoentity.Staff;
import com.taosisheng.service.ClassificationService;
import com.taosisheng.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/goods")
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private ClassificationService classificationService;

    @GetMapping("/select")
    public R<Page> select(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(name), Goods::getName, name);
        wrapper.orderByDesc(Goods::getId);
        Page pageInfo = goodsService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/selectByCategory")
    public R<Page> selectByCategory(int page, int pageSize, String category) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, category);

        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(category), Goods::getCategoryId, category);
        wrapper.orderByDesc(Goods::getSort);
        Page pageInfo = goodsService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody Goods goods) {
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        classificationLambdaQueryWrapper.eq(Classification::getId, goods.getCategoryId());
        Classification one1 = classificationService.getOne(classificationLambdaQueryWrapper);
        wrapper.eq(Goods::getName, goods.getName()).eq(Goods::getStatus, 1);
        Goods one = goodsService.getOne(wrapper);
        if (goods.getImage() == null) goods.setImage("goods.png");
        if (one != null) {
            return R.error("已有该商品", 100);
        }
        if (one1 == null) {
            return R.error("没有该分类", 100);
        }
        try {
            goodsService.save(goods);
        } catch (Exception e) {
            return R.error("添加失败，请检查字段！",100);
        }
        return R.success("添加成功");
    }

    @PostMapping("/update")
    public R<String> update(@RequestBody Goods goods) {
        LambdaQueryWrapper<Goods> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Goods::getId,goods.getId());
        goodsService.update(goods, wrapper1);
        return R.success("修改成功");
    }

    @GetMapping("/getClassList")
    public R<List<Map<String, Object>>> getClassList() {
        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Classification::getName, Classification::getId).orderByDesc(Classification::getSort);
        List<Map<String, Object>> list = classificationService.listMaps(wrapper);
        return R.success(list);
    }
    @GetMapping("/selectByclass")
    public R<List> selectByclass(Long categoryId) {

        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件

        wrapper.eq(Goods::getCategoryId,categoryId);
        List<Map<String, Object>> maps = goodsService.listMaps(wrapper);
        return R.success(maps);
    }

    @GetMapping("/delete")
    public R<String> delete(Long id) {
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getId, id);
        Goods one = goodsService.getOne(wrapper);
        if (one == null) {
            return R.error("删除失败！商品不存在", 100);
        }
        goodsService.remove(wrapper);
        return R.success("删除成功！");
    }

    @GetMapping("/getGoodById")
    public R<Goods> getGoodById(Long id){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getId,id);
        Goods one = goodsService.getOne(wrapper);
        return R.success(one);
    }
}
