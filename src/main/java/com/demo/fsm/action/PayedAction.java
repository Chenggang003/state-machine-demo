package com.demo.fsm.action;

import com.demo.entity.Order;
import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import com.demo.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 19:33
 * @Description:
 */
@Component
@Slf4j
public class PayedAction implements Action<OrderStatusEnum, OrderEventEnum> {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void execute(StateContext<OrderStatusEnum, OrderEventEnum> context) {

        try {
            //订单的对象
            Order order = context.getExtendedState().get(Order.class, Order.class);

            //当前订单状态校验
            log.info("当前订单状态校验");

            //添加交易记录
            log.info("添加交易支付记录");

            //订单状态更改
            log.info("订单状态更改");
            order.setStatus(OrderStatusEnum.WAIT_DELIVER);
            orderMapper.updateById(order);

            //操作日志
            log.info("订单操作日志");
        } catch (Exception e) {
            context.getExtendedState().getVariables().put(Exception.class, new RuntimeException("订单支付异常"));
            log.error("处理, 从状态[ {} ], 经过事件[ {} ], 到状态[ {} ], 出现异常：", context.getSource().getId(), context.getEvent(), context.getTarget().getId(), e);
        }
    }
}
