package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.ProductJournal;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJournalMapper extends BaseMapper<ProductJournal>
{

}
