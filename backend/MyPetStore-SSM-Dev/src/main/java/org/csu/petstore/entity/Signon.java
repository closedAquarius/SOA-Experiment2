package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("signon")
public class Signon {
    @TableId("username")
    private String username;
    private String password;
}
