package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierMapper extends BaseMapper<Supplier> {
}
