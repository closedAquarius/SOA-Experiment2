package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.LineItem;
import org.springframework.stereotype.Repository;

@Repository
public interface LineItemMapper extends BaseMapper<LineItem> {
}
