package com.demo.fsm.status;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 17:37
 * @Description:
 */
public enum OrderStatusEnum {
    WAIT_PAYMENT, // 待支付
    WAIT_DELIVER, // 待发货
    WAIT_RECEIVE, // 待收货
    FINISH, // 已完成
}
