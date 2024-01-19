package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.Classification;
import com.taosisheng.dtoentity.Goods;
import com.taosisheng.dtoentity.R;
import com.taosisheng.service.ClassificationService;
import com.taosisheng.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/class")
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
public class ClassificationController {
    @Autowired
    private ClassificationService classService;
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/select")
    public R<Page> select(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(name), Classification::getName, name);
        wrapper.orderByAsc(Classification::getSort);
        Page pageInfo = classService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody Classification classification) {
        try{
            classService.save(classification);
        }catch (Exception e){
            return R.error("添加失败,已有同名商品",100);
        }
        return R.success("添加成功");
    }

    @PostMapping("/update")
    public R<String> update(Classification classification) {
        LambdaQueryWrapper<Classification> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Classification::getId,classification.getId());
        Classification one = classService.getOne(wrapper);
        if (one == null){
            return R.success("修改失败！没有该套餐！");
        }
        classService.update(classification,wrapper);
        return R.success("修改成功");
    }

    @GetMapping("/delete")
    public R<String> delete(Long id){
        LambdaQueryWrapper<Goods> goodsLambdaQueryWrapper = new LambdaQueryWrapper<>();
        LambdaQueryWrapper<Classification> classificationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        goodsLambdaQueryWrapper.eq(Goods::getCategoryId,id);
        classificationLambdaQueryWrapper.eq(Classification::getId,id);

        goodsService.remove(goodsLambdaQueryWrapper);
        classService.remove(classificationLambdaQueryWrapper);

        return R.success("删除成功");
    }

    @GetMapping("/getGoodsList")
    public R<List<Goods>> getGoods(Long categoryId){
        LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Goods::getCategoryId,categoryId);
        List<Goods> list = goodsService.list(wrapper);
        return R.success(list);
    }
}
