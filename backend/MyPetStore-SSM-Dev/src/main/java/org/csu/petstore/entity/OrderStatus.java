package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("orderstatus")
public class OrderStatus {
    @TableId(value = "orderid")
    private int orderId;
    @TableField(value = "linenum")
    private int lineNum;
    @TableField(value = "timestamp")
    private Date timeStamp;
    @TableField(value = "status")
    private String status;
}
