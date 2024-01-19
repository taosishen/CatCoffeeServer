package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.*;
import com.taosisheng.service.OrderDetailService;
import com.taosisheng.service.OrdersService;
import com.taosisheng.service.ShoppingCartService;
import com.taosisheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/cart")
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private UserService userService;

    @GetMapping("/getAllCart")
    public R<Page> getAllCart(Long id) {
        log.info("id:{}", id);
        Page pages = new Page(1, 20);

        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(ShoppingCart::getUserId, id);
        //如果id有值，添加id的模糊查询条件
        wrapper.orderByDesc(ShoppingCart::getCreateTime);
        Page pageInfo = shoppingCartService.page(pages, wrapper);
        return R.success(pageInfo);
    }


    @PostMapping("/pushCart")
    public R pushCart(@RequestBody ShoppingCart shoppingCart) {
        log.info(shoppingCart.toString());
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, shoppingCart.getUserId()).eq(ShoppingCart::getGoodId, shoppingCart.getGoodId());
        ShoppingCart one = shoppingCartService.getOne(wrapper);
        shoppingCart.setCreateTime(LocalDateTime.now());
        if (one == null) {
            shoppingCartService.save(shoppingCart);
        } else {
            one.setNumber(one.getNumber() + 1);
            shoppingCartService.update(one, wrapper);
        }
        return R.success("添加购物车成功");
    }

    @PostMapping("/deleteCart")
    public R<String> deleteCart(@RequestBody ShoppingCart shoppingCart) {
        log.info(shoppingCart.toString());
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getId, shoppingCart.getId());
        boolean remove = shoppingCartService.remove(wrapper);
        if (remove) {
            return R.success("删除购物车成功");
        } else {
            return R.error("删除购物车失败", 400);
        }
    }

    @PostMapping("/changeCart")
    public R<String> subCart(@RequestBody ShoppingCart shoppingCart) {
        log.info(shoppingCart.toString());
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getId, shoppingCart.getId());
        //数量为0删除，返回
        if (shoppingCart.getNumber() == 0) {
            shoppingCartService.remove(wrapper);
            return R.success("数量为0,删除购物车成功");
        }
        //修改条件（非删除）
        LambdaUpdateWrapper<ShoppingCart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ShoppingCart::getId, shoppingCart.getId());
        updateWrapper.set(ShoppingCart::getNumber, shoppingCart.getNumber());
        boolean update = shoppingCartService.update(updateWrapper);
        if (update) {
            return R.success("修改购物车成功");
        } else {
            return R.error("修改购物车失败", 300);
        }
    }

    @GetMapping("/buy")
    public R<String> buy(String id,String remark) {
        String number = UUID.randomUUID().toString();
        Orders orders = new Orders();
        System.out.println(id);
        Long userId = Long.valueOf(id);
        //获取当前用户的所有购物车
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, id);
        List<Map<String, Object>> maps = shoppingCartService.listMaps(wrapper);
        //处理订单
        orders.setNumber(number);
        orders.setUserId(userId);
        orders.setStatus(1);
        orders.setRemark(remark);
        orders.setOrderTime(LocalDateTime.now());
        //获取计算总价和商品数量
        BigDecimal amounts = BigDecimal.valueOf(0);
        Integer q = 0;
        for (Iterator<Map<String, Object>> iterator = maps.listIterator(); iterator.hasNext(); ) {
            Map map = iterator.next();
            BigDecimal amount = (BigDecimal) map.get("amount");
            BigDecimal num = new BigDecimal((Integer) map.get("number"));
            BigDecimal bigDecimal = amount.multiply(num);
            amounts = amounts.add(bigDecimal);
            q += (Integer) map.get("number");
        }
        amounts = amounts.divide(new BigDecimal(100));
        orders.setAmount(amounts);
        orders.setNum(q);

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //获取用户信息
        userLambdaQueryWrapper.eq(User::getId,userId);
        User user = userService.getOne(userLambdaQueryWrapper);
        orders.setName(user.getName());
        orders.setPhone(user.getPhone());
        log.info(orders.toString());
        BigDecimal money = user.getMoney();
        money = money.subtract(orders.getAmount());
        if (money.compareTo(BigDecimal.valueOf(0))==-1){
            return R.error("储值不足，请及时充值",100);
        }
        user.setMoney(money);
//        成功修改数据库
        userService.update(user,userLambdaQueryWrapper);
        ordersService.save(orders);

        //处理订单详情(循环取出放到订单详情页)
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (Iterator<Map<String, Object>> iterator = maps.listIterator(); iterator.hasNext(); ) {
            Map map = iterator.next();
            OrderDetail orderDetail = new OrderDetail();
            //这是
            orderDetail.setOrderId(orders.getId());
            orderDetail.setNumber((Integer) map.get("number"));
            orderDetail.setImage((String) map.get("image"));
            orderDetail.setAmount((BigDecimal) map.get("amount"));
            orderDetail.setName((String) map.get("name"));
            orderDetail.setSetmealId((Long) map.get("class_id"));
            orderDetail.setDishId((Long) map.get("good_id"));
            orderDetails.add(orderDetail);
        }
        log.info(orderDetails.toString());
        orderDetailService.saveBatch(orderDetails);
        shoppingCartService.remove(wrapper);

        return R.success("购买成功");
    }

    @GetMapping("/removeCart")
    public R<String> removeCart(Long userId){
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        try {
            shoppingCartService.remove(wrapper);
        }catch (Exception e){
            return R.error("删除失败！",400);
        }
        return R.success("删除成功！");
    }
}
