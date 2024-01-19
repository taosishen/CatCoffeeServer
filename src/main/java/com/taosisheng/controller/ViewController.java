package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.*;
import com.taosisheng.service.GoodsService;
import com.taosisheng.service.OrderDetailService;
import com.taosisheng.service.OrdersService;
import com.taosisheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/view")
@ResponseBody
@Slf4j
public class ViewController {
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    //获取近七天日期
    public static List<String> getBeforeSevenDays2() {
        // 获取当前日期
        Date currentDate = new Date();

        // 创建Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        //当天月日
        List<String> list = new ArrayList<>();
        list.add(sdf.format(new Date()));
        // 遍历并输出前6天的日期
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date previousDate = calendar.getTime();
            list.add(0, sdf.format(previousDate));
        }

        return list;

    }

    @GetMapping("/getSevenDay")
    public R<Map> getSevenDay() {
        List<String> time = getBeforeSevenDays2();
        Map map = new HashMap<>();
        List lists = new ArrayList<>();
        List userCountList = new ArrayList<>();
        log.info(time.toString());

        for (int i = 0; i < time.size(); i++) {
            String index = time.get(i);
            LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
            wrapper.like(Orders::getOrderTime, index);
            int count = ordersService.count(wrapper);
            lists.add(count);
        }
        for (int i = 0; i < time.size(); i++) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.like(User::getCreateTime,time.get(i));
            int count = userService.count(userLambdaQueryWrapper);
            userCountList.add(count);
        }
        map.put("sevenDay", time);
        map.put("sevenOrderCount", lists);
        map.put("sevenRegisterCount", userCountList);
        return R.success(map);
    }

    @GetMapping("/getOrderRanking")
    public R<List> getOrderRanking(){
        QueryWrapper<OrderDetail> wrapper = new QueryWrapper<>();
        wrapper.select("count(dish_id) as value","name").groupBy("dish_id").orderByDesc("value");
        List<Map<String, Object>> list = orderDetailService.listMaps(wrapper);
        return R.success(list);
    }
}
