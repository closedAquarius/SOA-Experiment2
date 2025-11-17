package org.csu.petstore.product.dto;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品项数据传输对象
 */
@Data
public class ItemDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String itemId;
    private String productId;
    private String productName;
    private BigDecimal listPrice;
    private BigDecimal unitCost;
    private String status;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;
    private Integer quantity;
    private String description;
}
