package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.ReturnOrders;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnOrdersMapper extends BaseMapper<ReturnOrders>
{

}
