package com.taosisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taosisheng.dtoentity.Goods;
import com.taosisheng.mapper.GoodsMapper;
import com.taosisheng.service.GoodsService;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
