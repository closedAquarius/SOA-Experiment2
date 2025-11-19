package org.csu.petstore.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.CategoryDTO;
import org.csu.petstore.product.dto.Result;
import org.csu.petstore.product.entity.Category;
import org.csu.petstore.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类Controller - RESTful API
 */
@Slf4j
@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 获取所有分类
     * GET /api/categories
     */
    @GetMapping
    public Result<List<Category>> getAllCategories() {
        log.info("API: 获取所有分类");
        List<Category> categories = categoryService.getAllCategories();
        return Result.success(categories);
    }
    
    /**
     * 获取分类详情（包含商品列表）
     * GET /api/categories/{categoryId}
     */
    @GetMapping("/{categoryId}")
    public Result<CategoryDTO> getCategory(@PathVariable String categoryId) {
        log.info("API: 获取分类详情, categoryId={}", categoryId);
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            return Result.error("分类不存在");
        }
        return Result.success(category);
    }
    
    /**
     * 创建分类
     * POST /api/categories
     */
    @PostMapping
    public Result<String> createCategory(@RequestBody Category category) {
        log.info("API: 创建分类, category={}", category);
        boolean success = categoryService.createCategory(category);
        if (success) {
            return Result.success("创建成功");
        }
        return Result.error("创建失败");
    }
    
    /**
     * 更新分类
     * PUT /api/categories/{categoryId}
     */
    @PutMapping("/{categoryId}")
    public Result<String> updateCategory(@PathVariable String categoryId, 
                                         @RequestBody Category category) {
        log.info("API: 更新分类, categoryId={}, category={}", categoryId, category);
        category.setCategoryId(categoryId);
        boolean success = categoryService.updateCategory(category);
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
}
