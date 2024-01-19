package com.taosisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taosisheng.dtoentity.Staff;
import com.taosisheng.dtoentity.User;
import com.taosisheng.mapper.StaffMapper;
import com.taosisheng.mapper.UserMapper;
import com.taosisheng.service.StaffService;
import com.taosisheng.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
