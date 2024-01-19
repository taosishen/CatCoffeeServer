package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.R;
import com.taosisheng.dtoentity.User;
import com.taosisheng.service.UserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.MD5;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@ResponseBody
@Slf4j
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(HttpServletRequest request,@RequestBody User user) {
        System.out.println(user);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhone, user.getPhone());
        User users = userService.getOne(queryWrapper);
        if (users == null) {
            return R.error("登录失败,手机号未注册", 100);
        }
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!password.equals(users.getPassword())) {
            return R.error("登录失败,密码错误!", 100);
        }
        request.getSession().setAttribute("user", users.getId());
        return R.success(users.getId().toString());
    }

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return R.success("退出登录成功");
    }

    @GetMapping("/select")
    public R<Page> selectAll(int page, int pageSize, String phone) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, phone);
        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(phone), User::getPhone, phone);
        wrapper.orderByDesc(User::getId);

        Page pageInfo = userService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, user.getPhone());
        User check = userService.getOne(wrapper);
        if (check != null) {
            return R.error("注册失败！手机号已被注册", 100);
        }
        user.setUsername("@" + UUID.randomUUID());

//        判断性别
        switch (user.getSex()) {
            case "0":
                user.setAvatar("woman.png");
                break;
            case "1":
                user.setAvatar("man.png");
                break;
            case "2":
                user.setAvatar("none.png");
                break;
        }
        user.setCreateTime(LocalDateTime.now());
        user.setMoney(new BigDecimal(0));
        try {
            userService.save(user);
        } catch (Exception e) {
            return R.error("注册失败！请告知店家！", 100);
        }
        return R.success("注册成功");
    }

    @GetMapping("/avatar")
    public R<String> avatar(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        wrapper.eq(User::getPhone, phone);
        User one = userService.getOne(wrapper);
        if (one != null) {
            return R.success("/file/downloadAvtar?t="+one.getAvatar());
        }
        return R.error("查无此人的头像", 100);
    }

    @PostMapping("/update")
    public R<String> update(@RequestBody User user){
        log.info(user.toString());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User one = userService.getOne(wrapper);
        if (one!=null){
            log.info(one.toString());
            userService.update(user,wrapper);
            return R.success("修改成功");
        }
        return R.error("修改失败！！",100);
    }

    @GetMapping("/delete")
    public R<String> delete(String username){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,username);
        User one = userService.getOne(wrapper);
        if (one!=null){
            userService.remove(wrapper);
            return R.success("删除成功");
        }
        return R.error("查无此人",200);
    }

    @GetMapping("/getUserMsg")
    public R<User> getUserMsg(String userId){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId,userId);
        User one = userService.getOne(wrapper);
        return R.success(one);
    }

    @GetMapping("/recharge")
    public R<String> recharge(String userId,Integer money){
        BigDecimal number = new BigDecimal(money);
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getId,userId);
        User one = userService.getOne(lambdaQueryWrapper);
        number = number.add(one.getMoney());
        updateWrapper.eq(User::getId,userId).set(User::getMoney,number);
        userService.update(updateWrapper);
        return R.success("充值成功!");
    }

}
