package com.demo.controller;

import com.demo.entity.Order;
import com.demo.handler.BaseResponse;
import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: devil.chen
 * @Date: 2024/3/19 9:07
 * @Description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     *创建订单
     *
     *@return
     */
    @RequestMapping("/create")
    public BaseResponse create(@RequestBody Order order){
        return BaseResponse.success(orderService.create(order));
    }


    /**
     * 根据id查询订单
     * @param id
     * @return
     */
    @RequestMapping("/getByOrderId")
    public BaseResponse getById(@RequestParam("id") Long id){
        //根据id查询订单
        return BaseResponse.success(orderService.getById(id));
    }

    /**
     *对订单进行支付
     *
     *@paramid
     *@return
     */
    @RequestMapping("/pay")
    public BaseResponse pay(@RequestParam("id") Long id){
        //对订单进行支付
        orderService.pay(id);
        return BaseResponse.success();
    }

     /**
     *对订单进行发货
     *
     *@paramid
     *@return
     */
    @RequestMapping("/deliver")
    public BaseResponse deliver(@RequestParam("id") Long id){
        //对订单进行确认收货
        orderService.deliver(id);
        return BaseResponse.success();
    }


    /**
     *对订单进行确认收货
     *
     *@param id
     *@return
     */
    @RequestMapping("/receive")
    public BaseResponse receive(@RequestParam("id") Long id){
        //对订单进行确认收货
        orderService.receive(id);
        return BaseResponse.success();
    }

}
