package com.demo.fsm.builder;

import com.demo.fsm.action.PayedAction;
import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 19:23
 * @Description: 以待付款为初始状态 构建 状态机
 */
@Component
@Slf4j
public class WaitPaymentBuilder implements Builder {

    @Autowired
    private PayedAction payedAction;

    @Override
    public OrderStatusEnum currentState() {
        return OrderStatusEnum.WAIT_PAYMENT;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderEventEnum> build(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<OrderStatusEnum, OrderEventEnum> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId("WAIT_PAYMENT").beanFactory(beanFactory)
                .listener(listener());

        builder.configureStates()
                .withStates()
                .initial(OrderStatusEnum.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatusEnum.class));

        builder.configureTransitions()

                // 待付款 -> [付款] -> 待发货
                .withExternal()
                .source(OrderStatusEnum.WAIT_PAYMENT).target(OrderStatusEnum.WAIT_DELIVER)
                .event(OrderEventEnum.PAYED)
                .action(payedAction);

        return builder.build();
    }

    public StateMachineListener<OrderStatusEnum, OrderEventEnum> listener() {
        return new StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum>() {
            @Override
            public void stateChanged(State<OrderStatusEnum, OrderEventEnum> from, State<OrderStatusEnum, OrderEventEnum> to) {
                log.info("进入到状态机事件监听方法啦");
                log.info("State change to " + to.getId());
                log.info("消息通知买家等");
                log.info("三方系统调用等");
            }
        };
    }
}
