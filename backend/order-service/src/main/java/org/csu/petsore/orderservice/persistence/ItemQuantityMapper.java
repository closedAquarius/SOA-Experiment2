package org.csu.petsore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petsore.orderservice.entity.ItemQuantity;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemQuantityMapper extends BaseMapper<ItemQuantity> {
}
