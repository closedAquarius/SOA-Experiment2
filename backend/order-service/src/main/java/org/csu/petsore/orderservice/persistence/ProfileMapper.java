package org.csu.petsore.orderservice.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petsore.orderservice.entity.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMapper extends BaseMapper<Profile> {
}
