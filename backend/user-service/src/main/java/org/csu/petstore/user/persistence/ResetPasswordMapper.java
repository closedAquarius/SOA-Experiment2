package org.csu.petstore.user.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.user.entity.ResetPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordMapper extends BaseMapper<ResetPassword> {
}