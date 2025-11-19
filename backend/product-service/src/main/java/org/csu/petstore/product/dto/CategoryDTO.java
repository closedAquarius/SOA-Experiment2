package org.csu.petstore.product.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * 分类数据传输对象
 */
@Data
public class CategoryDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String categoryId;
    private String name;
    private String description;
    
    // 该分类下的商品列表
    private List<ProductDTO> products;
}
