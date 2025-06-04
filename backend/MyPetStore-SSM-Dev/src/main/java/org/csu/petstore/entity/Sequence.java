package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("sequence")
public class Sequence {
    @TableId(value = "name")
    private String name;
    @TableField(value = "nextid")
    private int nextId;
}
