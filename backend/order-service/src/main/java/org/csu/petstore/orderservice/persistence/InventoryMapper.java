package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMapper extends BaseMapper<Inventory> {
}
