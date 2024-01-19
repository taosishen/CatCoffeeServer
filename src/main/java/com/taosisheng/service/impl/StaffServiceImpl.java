package com.taosisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taosisheng.dtoentity.Staff;
import com.taosisheng.mapper.StaffMapper;
import com.taosisheng.service.StaffService;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper,Staff> implements StaffService{

}
