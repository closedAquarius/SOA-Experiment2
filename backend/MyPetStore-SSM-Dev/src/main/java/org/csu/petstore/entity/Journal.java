package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("journal")
public class Journal {
    @TableField(value = "userid")
    private String username;
    private String description;
    private String date;
    private String color;
}
