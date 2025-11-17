package org.csu.petstore.product.service;

import org.csu.petstore.product.dto.CategoryDTO;
import org.csu.petstore.product.entity.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {
    
    /**
     * 获取所有分类
     */
    List<Category> getAllCategories();
    
    /**
     * 根据ID获取分类详情（包含商品列表）
     */
    CategoryDTO getCategoryById(String categoryId);
    
    /**
     * 创建分类
     */
    boolean createCategory(Category category);
    
    /**
     * 更新分类
     */
    boolean updateCategory(Category category);
}
