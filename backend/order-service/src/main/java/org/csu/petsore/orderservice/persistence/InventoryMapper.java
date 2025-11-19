package org.csu.petsore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petsore.orderservice.entity.Inventory;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryMapper extends BaseMapper<Inventory> {
}
