package org.csu.petstore.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.csu.petstore.product.entity.Inventory;

/**
 * 库存Mapper接口
 */
@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {
}
