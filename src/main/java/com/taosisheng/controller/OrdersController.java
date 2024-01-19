package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.UpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.*;
import com.taosisheng.service.OrderDetailService;
import com.taosisheng.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
@ResponseBody
@Slf4j
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/select")
    public R<Page> select(int page, int pageSize, String selectId,String selectNumber) {
        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(selectId), Orders::getId, selectId);
        wrapper.like(StringUtils.isNotEmpty(selectNumber), Orders::getNumber, selectNumber);
        wrapper.orderByDesc(Orders::getOrderTime);
        Page pageInfo = ordersService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/getOrder")
    public R<List<Map<String, Object>>> getOrder(Integer orderId) {
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderDetail::getOrderId, orderId);
        List<Map<String, Object>> maps = orderDetailService.listMaps(wrapper);
        return R.success(maps);
    }

    @GetMapping("/getOrderMsg")
    public R<Orders> getOrderMsg(Long id){
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getId,id);
        Orders one = ordersService.getOne(wrapper);
        return R.success(one);
    }
    @GetMapping("/getOrderNum")
    public R<Integer> getOrderNum(String userId){
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId,userId);
        int count = ordersService.count(wrapper);
        return R.success(count);
    }

    @GetMapping("/finnishOrder")
    public R<String> finnishOrder(String number){
        UpdateWrapper<Orders> ordersUpdateWrapper = new UpdateWrapper<>();
        ordersUpdateWrapper.set("status",2).eq("number",number);
        ordersService.update(ordersUpdateWrapper);
        return R.success("已完成");
    }
}
