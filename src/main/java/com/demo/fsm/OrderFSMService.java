package com.demo.fsm;

import com.demo.entity.Order;
import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import com.demo.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

/**
 * 状态机服务
 * Created by jinwei on 28/03/2017.
 */
@Service
@Slf4j
public class OrderFSMService {

    @Autowired
    private BuilderFactory builderFactory;

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 修改订单状态
     * @param id
     * @param orderEventEnum
     * @return
     */
    public boolean changeState(Long id, OrderEventEnum orderEventEnum) {
        //1、查找订单信息（可选）
        Order order = orderMapper.selectById(id);

        //2. 根据订单创建状态机
        StateMachine<OrderStatusEnum, OrderEventEnum> stateMachine = builderFactory.create(order);

        //3. 发送当前请求的状态
        boolean isSend = stateMachine.sendEvent(orderEventEnum);
        if (!isSend) {
            log.error("创建订单状态机失败,无法从状态 {} 转向 => {}", order.getStatus(), orderEventEnum);
            throw new RuntimeException("订单状态流转失败，无法从状态【" + order.getStatus()+"】 执行事件 【" + orderEventEnum + "】");
        }

        //4. 判断处理过程中是否出现了异常
        Exception exception = stateMachine.getExtendedState().get(Exception.class, Exception.class);
        if (exception != null) {
            throw (RuntimeException) exception;
        }
        return true;
    }
}
