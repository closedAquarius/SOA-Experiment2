package org.csu.petstore.product.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 商品实体类
 */
@Data
@TableName("product")
public class Product implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(value = "productid")
    private String productId;
    
    @TableField("category")
    private String categoryId;
    
    private String name;
    
    @TableField("descn")
    private String description;
    
    private Integer modifying;
}
