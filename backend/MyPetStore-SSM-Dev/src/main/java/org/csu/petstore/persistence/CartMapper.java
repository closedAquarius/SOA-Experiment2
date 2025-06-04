package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.Cart;
import org.springframework.stereotype.Repository;

@Repository
public interface CartMapper extends BaseMapper<Cart> {
}
