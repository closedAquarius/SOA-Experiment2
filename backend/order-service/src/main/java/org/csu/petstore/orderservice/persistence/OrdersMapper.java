package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
}
