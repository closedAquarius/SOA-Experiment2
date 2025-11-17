package org.csu.petstore.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品项数量实体类
 */
@Data
@TableName("itemquantity")
public class ItemQuantity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "itemid")
    private String itemId;
    
    private Integer quantity;
}
