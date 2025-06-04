package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.Journal;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalMapper extends BaseMapper<Journal> {
}
