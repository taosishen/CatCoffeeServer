package com.taosisheng.dtoentity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 */
@Data
public class Orders implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    //订单号
    private String number;

    //订单状态 1待付款，2待派送，3已派送，4已完成，5已取消
    private Integer status;

    //下单用户id
    private Long userId;

    //下单时间
    private LocalDateTime orderTime;

    //实收金额
    private BigDecimal amount;

    //用户名
    private String name;

    //备注
    private String remark;

    //手机号
    private String phone;

    //数量
    private Integer num;
}
