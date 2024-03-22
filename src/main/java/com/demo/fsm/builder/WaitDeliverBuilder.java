package com.demo.fsm.builder;

import com.demo.fsm.action.DeliverAction;
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
 * @Description:
 */
@Component
@Slf4j
public class WaitDeliverBuilder implements Builder {

    @Autowired
    private DeliverAction deliverAction;

    @Override
    public OrderStatusEnum currentState() {
        return OrderStatusEnum.WAIT_DELIVER;
    }

    @Override
    public StateMachine<OrderStatusEnum, OrderEventEnum> build(BeanFactory beanFactory) throws Exception {

        StateMachineBuilder.Builder<OrderStatusEnum, OrderEventEnum> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .machineId("WAIT_DELIVER").beanFactory(beanFactory)
                .listener(listener());

        builder.configureStates()
                .withStates()
                .initial(OrderStatusEnum.WAIT_DELIVER)
                .states(EnumSet.allOf(OrderStatusEnum.class));

        builder.configureTransitions()

                // 待发货 -> [发货] -> 待收货
                .withExternal()
                .source(OrderStatusEnum.WAIT_DELIVER).target(OrderStatusEnum.WAIT_RECEIVE)
                .event(OrderEventEnum.DELIVERY)
                .action(deliverAction);

        return builder.build();
    }

    public StateMachineListener<OrderStatusEnum, OrderEventEnum> listener() {
        return new StateMachineListenerAdapter<OrderStatusEnum, OrderEventEnum>() {
            @Override
            public void stateChanged(State<OrderStatusEnum, OrderEventEnum> from, State<OrderStatusEnum, OrderEventEnum> to) {
                log.info("进入到状态机事件监听方法啦");
                log.info("State change to " + to.getId());
                log.info("消息通知用户等，例如商品使用，注意事项等");
                log.info("物流信息跟踪等");
            }
        };
    }
}
