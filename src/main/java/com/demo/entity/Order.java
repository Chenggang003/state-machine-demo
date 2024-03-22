package com.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.demo.fsm.status.OrderStatusEnum;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 19:37
 * @Description:
 */
@Data
@TableName("tb_order")
public class Order {

    private Long id;

    private OrderStatusEnum status;

    // 其他订单属性...

}
