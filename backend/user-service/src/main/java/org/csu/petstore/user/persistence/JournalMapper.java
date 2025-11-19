package org.csu.petstore.user.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.user.entity.Journal;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalMapper extends BaseMapper<Journal> {
}