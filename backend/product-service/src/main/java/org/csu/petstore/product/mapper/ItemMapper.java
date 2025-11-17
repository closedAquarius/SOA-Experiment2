package org.csu.petstore.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.csu.petstore.product.entity.Item;

/**
 * 商品项Mapper接口
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
