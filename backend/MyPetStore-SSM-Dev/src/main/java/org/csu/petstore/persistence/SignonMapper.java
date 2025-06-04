package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.Signon;
import org.springframework.stereotype.Repository;

@Repository
public interface SignonMapper extends BaseMapper<Signon> {
}
