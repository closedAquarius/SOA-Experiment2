package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("returnorders")
public class ReturnOrders
{
    @TableId(value = "orderId")
    private String orderId;
    @TableField("reason")
    private String reason;
    @TableField("descn")
    private String descn;
    @TableField("image")
    private String image;
    @TableField("status")
    private String status;
}
