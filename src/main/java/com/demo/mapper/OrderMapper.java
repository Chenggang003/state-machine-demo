package com.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: devil.chen
 * @Date: 2024/3/19 10:13
 * @Description:
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
