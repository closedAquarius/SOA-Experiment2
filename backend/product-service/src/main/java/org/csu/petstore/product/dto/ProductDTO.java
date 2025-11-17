package org.csu.petstore.product.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 商品数据传输对象
 */
@Data
public class ProductDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String productId;
    private String categoryId;
    private String name;
    private String description;
    
    // 关联的商品项列表
    private List<ItemDTO> items;
}
