package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.ViewProduct;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewProductMapper extends BaseMapper<ViewProduct>
{

}
