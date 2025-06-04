package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.UserAddress;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressMapper extends BaseMapper<UserAddress> {
}
