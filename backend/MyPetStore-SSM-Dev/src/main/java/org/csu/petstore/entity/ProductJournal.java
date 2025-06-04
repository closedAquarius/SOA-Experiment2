package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("productjournal")
public class ProductJournal
{
    @TableField(value = "userId")
    private String userId;
    @TableField(value = "productId")
    private String productId;
}
