package com.demo.fsm.builder;

import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.statemachine.StateMachine;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 19:05
 * @Description:
 */
public interface Builder {

    OrderStatusEnum currentState();

    StateMachine<OrderStatusEnum, OrderEventEnum> build(BeanFactory beanFactory) throws Exception;
}
