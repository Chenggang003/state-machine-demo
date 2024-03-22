package com.demo.service;

import com.demo.entity.Order;
import com.demo.fsm.OrderFSMService;
import com.demo.fsm.event.OrderEventEnum;
import com.demo.fsm.status.OrderStatusEnum;
import com.demo.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: devil.chen
 * @Date: 2024/3/18 19:05
 * @Description:
 */
@Service("orderService")
@Slf4j
public class OrderService {

    @Autowired
    private OrderFSMService orderFSMService;

    @Autowired
    private OrderMapper orderMapper;

    /**
     *创建订单
     *
     *@paramorder
     *@return
     */
    public Order create(Order order){
        order.setStatus(OrderStatusEnum.WAIT_PAYMENT);
        orderMapper.insert(order);
        return order;
    }

    /**
     *对订单进行支付
     *
     *@paramid
     *@return
     */
    public String pay(Long id){
        log.info("线程名称：{},尝试支付，订单号：{}",Thread.currentThread().getName(),id);
        if(!orderFSMService.changeState(id, OrderEventEnum.PAYED)){
            log.error("线程名称：{},支付失败, 状态异常，订单ID：{}",Thread.currentThread().getName(),id);
            throw new RuntimeException("支付失败,订单状态异常");
        }
        return "success";
    }

    /**
     *对订单进行发货
     *
     *@paramid
     *@return
     */
    public String deliver(Long id){
        log.info("线程名称：{},尝试发货，订单号：{}",Thread.currentThread().getName(), id);
        if(!orderFSMService.changeState(id, OrderEventEnum.DELIVERY)){
            log.error("线程名称：{},发货失败, 状态异常，订单ID信息：{}",Thread.currentThread().getName(), id);
            throw new RuntimeException("发货失败,订单状态异常");
        }
        return "success";
    }

    /**
     *对订单进行确认收货
     *
     *@paramid
     *@return
     */
    public String receive(Long id){
        log.info("线程名称：{},尝试收货，订单号：{}",Thread.currentThread().getName(),id);
        if(!orderFSMService.changeState(id, OrderEventEnum.RECEIVED)){
            log.error("线程名称：{},收货失败, 状态异常，订单ID：{}",Thread.currentThread().getName(), id);
            throw new RuntimeException("收货失败,订单状态异常");
        }
        return "success";
    }

    public Order getById(Long id) {
        return orderMapper.selectById(id);
    }
}
