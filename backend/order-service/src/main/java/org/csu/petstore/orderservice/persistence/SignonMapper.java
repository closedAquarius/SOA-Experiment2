package org.csu.petstore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.orderservice.entity.Signon;
import org.springframework.stereotype.Repository;

@Repository
public interface SignonMapper extends BaseMapper<Signon> {
}
