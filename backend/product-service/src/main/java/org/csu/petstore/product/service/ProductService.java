package org.csu.petstore.product.service;

import org.csu.petstore.product.dto.ProductDTO;
import org.csu.petstore.product.entity.Product;

import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService {
    
    /**
     * 根据ID获取商品详情
     */
    ProductDTO getProductById(String productId);
    
    /**
     * 根据分类ID获取商品列表
     */
    List<ProductDTO> getProductsByCategory(String categoryId);
    
    /**
     * 搜索商品（按名称模糊查询）
     */
    List<Product> searchProducts(String keyword);
    
    /**
     * 创建商品
     */
    boolean createProduct(Product product);
    
    /**
     * 更新商品信息
     */
    boolean updateProduct(Product product);
    
    /**
     * 删除商品
     */
    boolean deleteProduct(String productId);
    
    /**
     * 获取所有商品
     */
    List<Product> getAllProducts();
}
