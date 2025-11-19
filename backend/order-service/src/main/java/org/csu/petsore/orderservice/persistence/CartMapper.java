package org.csu.petsore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petsore.orderservice.entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
}
