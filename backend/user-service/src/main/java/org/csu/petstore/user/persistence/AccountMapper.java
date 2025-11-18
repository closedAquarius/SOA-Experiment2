package org.csu.petstore.user.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.user.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountMapper extends BaseMapper<Account> {
}