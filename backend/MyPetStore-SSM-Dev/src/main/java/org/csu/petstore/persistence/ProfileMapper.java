package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.csu.petstore.entity.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileMapper extends BaseMapper<Profile> {
    @Select("SELECT favcategory, COUNT(*) AS count FROM profile GROUP BY favcategory ORDER BY count DESC LIMIT 1")
    List<String> getMostFrequentCategory();
}
