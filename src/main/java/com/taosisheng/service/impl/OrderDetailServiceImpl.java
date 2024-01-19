package com.taosisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taosisheng.dtoentity.OrderDetail;
import com.taosisheng.mapper.OrderDetailMapper;
import com.taosisheng.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
