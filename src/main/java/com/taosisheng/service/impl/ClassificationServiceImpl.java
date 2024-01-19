package com.taosisheng.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.taosisheng.dtoentity.Classification;
import com.taosisheng.mapper.ClassificationMapper;
import com.taosisheng.service.ClassificationService;
import org.springframework.stereotype.Service;

@Service
public class ClassificationServiceImpl extends ServiceImpl<ClassificationMapper, Classification> implements ClassificationService {
}
