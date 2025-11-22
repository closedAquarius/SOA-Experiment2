package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.OrderStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusMapper extends BaseMapper<OrderStatus> {
}
