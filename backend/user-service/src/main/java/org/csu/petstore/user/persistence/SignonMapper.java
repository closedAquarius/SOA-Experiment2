package org.csu.petstore.user.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.user.entity.Signon;
import org.springframework.stereotype.Repository;

@Repository
public interface SignonMapper extends BaseMapper<Signon> {
}