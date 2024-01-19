package com.taosisheng.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taosisheng.dtoentity.R;
import com.taosisheng.dtoentity.Staff;
import com.taosisheng.service.StaffService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/staff")
@ResponseBody
@Slf4j
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/login")
    public R<String> login(HttpServletRequest request, @RequestBody Staff staff) {
        System.out.println(staff);
        LambdaQueryWrapper<Staff> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Staff::getUsername, staff.getUsername());
        Staff user = staffService.getOne(queryWrapper);
        String password = DigestUtils.md5DigestAsHex(staff.getPassword().getBytes());
        if (user == null) {
            return R.error("登录失败,用户不存在哦！", 100);
        }
        if (!user.getPassword().equals(password)) {
            return R.error("登录失败,密码错误!", 100);
        }
        if (user.getStatus() == 0) {
            return R.error("账号已禁用", 100);
        }
        request.getSession().setAttribute("staff", user.getId());
        return R.success(user.getId().toString());
    }

    @GetMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("staff");
        return R.success("退出登录");
    }

    @GetMapping("/select")
    public R<Page> select(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);

        Page pages = new Page(page, pageSize);

        LambdaQueryWrapper<Staff> wrapper = new LambdaQueryWrapper<>();

        //如果name有值，添加name的模糊查询条件
        wrapper.like(StringUtils.isNotEmpty(name), Staff::getName, name);
        wrapper.orderByDesc(Staff::getId);
        Page pageInfo = staffService.page(pages, wrapper);
        return R.success(pageInfo);
    }

    @PostMapping("/add")
    public R<String> add(@RequestBody Staff staff) {
        LambdaQueryWrapper<Staff> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Staff::getUsername, staff.getUsername());
        Staff check = staffService.getOne(wrapper);
        if (check != null) {
            return R.error("添加失败，已有相同用户，请尝试重新添加用户", 100);
        }
        staff.setPassword(DigestUtils.md5DigestAsHex(staff.getPassword().getBytes()));
        try{
            staffService.save(staff);
        }catch (Exception e){
            return R.error("添加失败",300);
        }
        return R.success("添加成功");
    }

    @PostMapping("/update")
    public R<String> updateUser(@RequestBody Staff staff) {
        log.info(staff.toString());
        if (staff.getPassword().length() < 20) {
            staff.setPassword(DigestUtils.md5DigestAsHex(staff.getPassword().getBytes()));
        }
        LambdaQueryWrapper<Staff> wrapper = new LambdaQueryWrapper<>();
        //如果name有值，添加name的模糊查询条件
        wrapper.eq(Staff::getUsername, staff.getUsername());
        try {
            staffService.update(staff, wrapper);
        } catch (Exception e) {
            return R.error("修改失败", 100);
        }
        return R.success("修改成功");
    }

    @GetMapping("/delete")
    public R<String> delete(String username){
        LambdaQueryWrapper<Staff> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Staff::getUsername,username);
        Staff one = staffService.getOne(wrapper);
        if (one != null){
            staffService.remove(wrapper);
            return R.success("删除成功");
        }
        return R.error("删除失败",100);
    }
}
