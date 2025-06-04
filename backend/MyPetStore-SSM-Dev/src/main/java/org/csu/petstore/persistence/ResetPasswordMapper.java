package org.csu.petstore.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.csu.petstore.entity.ResetPassword;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordMapper extends BaseMapper<ResetPassword>
{

}
