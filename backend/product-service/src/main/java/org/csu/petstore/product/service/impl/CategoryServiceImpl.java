package org.csu.petstore.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.CategoryDTO;
import org.csu.petstore.product.dto.ProductDTO;
import org.csu.petstore.product.entity.Category;
import org.csu.petstore.product.entity.Product;
import org.csu.petstore.product.mapper.CategoryMapper;
import org.csu.petstore.product.mapper.ProductMapper;
import org.csu.petstore.product.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现类
 */
@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    
    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Override
    public List<Category> getAllCategories() {
        log.info("获取所有分类");
        return categoryMapper.selectList(null);
    }
    
    @Override
    public CategoryDTO getCategoryById(String categoryId) {
        log.info("查询分类详情, categoryId={}", categoryId);
        
        Category category = categoryMapper.selectById(categoryId);
        if (category == null) {
            log.warn("分类不存在, categoryId={}", categoryId);
            return null;
        }
        
        CategoryDTO categoryDTO = new CategoryDTO();
        BeanUtils.copyProperties(category, categoryDTO);
        
        // 获取该分类下的所有商品
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", categoryId);
        List<Product> products = productMapper.selectList(queryWrapper);
        
        List<ProductDTO> productDTOs = products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            BeanUtils.copyProperties(product, dto);
            return dto;
        }).collect(Collectors.toList());
        
        categoryDTO.setProducts(productDTOs);
        
        return categoryDTO;
    }
    
    @Override
    public boolean createCategory(Category category) {
        log.info("创建分类, category={}", category);
        return categoryMapper.insert(category) > 0;
    }
    
    @Override
    public boolean updateCategory(Category category) {
        log.info("更新分类, category={}", category);
        return categoryMapper.updateById(category) > 0;
    }
}
