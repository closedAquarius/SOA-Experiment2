package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.ItemQuantity;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemQuantityMapper extends BaseMapper<ItemQuantity> {
}
