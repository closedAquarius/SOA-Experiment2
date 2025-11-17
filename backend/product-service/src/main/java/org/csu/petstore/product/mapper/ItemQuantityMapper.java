package org.csu.petstore.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.csu.petstore.product.entity.ItemQuantity;

/**
 * 商品项数量Mapper接口
 */
@Mapper
public interface ItemQuantityMapper extends BaseMapper<ItemQuantity> {
}
