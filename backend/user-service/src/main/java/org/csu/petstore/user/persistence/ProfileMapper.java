package org.csu.petstore.user.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.user.entity.Profile;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileMapper extends BaseMapper<Profile> {
}