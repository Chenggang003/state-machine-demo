package com.demo.fsm;

import com.demo.entity.Order;
import com.demo.fsm.builder.Builder;
import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 17:37
 * @Description: 根据订单的状态创建状态机
 */
@Component
public class BuilderFactory {

    private Map<OrderStatusEnum, Builder> stateBuilderMap = new ConcurrentHashMap<>();


    @Autowired
    private List<Builder> stateBuilders;


    @Autowired
    private BeanFactory beanFactory;

    @PostConstruct
    public void init(){
        stateBuilderMap = stateBuilders.stream().collect(Collectors.toMap(Builder::currentState, Function.identity()));
    }

    /**
     * @param order
     * @return
     */
    public StateMachine<OrderStatusEnum, OrderEventEnum> create(Order order) {

        OrderStatusEnum orderStatus = order.getStatus();
        Builder builder = stateBuilderMap.get(orderStatus);

        StateMachine<OrderStatusEnum, OrderEventEnum> sm;
        try {
            sm = builder.build(beanFactory);
            sm.start();
        } catch (Exception e) {
            throw new RuntimeException("创建订单状态机异常");
        }
        sm.getExtendedState().getVariables().put(Order.class, order);
        return sm;
    }

}
