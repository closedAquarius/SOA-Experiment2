package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("viewproduct")
public class ViewProduct
{
    @TableId(value="productId")
    private String productId;
    @TableField(value="viewCount")
    private int viewCount;
}
