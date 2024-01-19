package com.taosisheng.dtoentity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {
    private Long id;


    private String name;


    private String username;


    private String password;


    private String phone;


    private String sex;


    private String avatar;

    private BigDecimal money;

    private LocalDateTime createTime;
}
