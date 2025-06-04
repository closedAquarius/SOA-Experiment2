package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("resetpassword")
public class ResetPassword
{
    @TableId(value = "userId")
    private String userId;
    @TableField(value = "status")
    private int status;
}
