package org.csu.petstore.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.ProductDTO;
import org.csu.petstore.product.dto.Result;
import org.csu.petstore.product.entity.Product;
import org.csu.petstore.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品Controller - RESTful API
 */
@Slf4j
@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    /**
     * 获取商品详情
     * GET /api/products/{productId}
     */
    @GetMapping("/{productId}")
    public Result<ProductDTO> getProduct(@PathVariable String productId) {
        log.info("API: 获取商品详情, productId={}", productId);
        ProductDTO product = productService.getProductById(productId);
        if (product == null) {
            return Result.error("商品不存在");
        }
        return Result.success(product);
    }
    
    /**
     * 按分类查询商品列表
     * GET /api/products/category/{categoryId}
     */
    @GetMapping("/category/{categoryId}")
    public Result<List<ProductDTO>> getProductsByCategory(@PathVariable String categoryId) {
        log.info("API: 按分类查询商品, categoryId={}", categoryId);
        List<ProductDTO> products = productService.getProductsByCategory(categoryId);
        return Result.success(products);
    }
    
    /**
     * 搜索商品
     * GET /api/products/search?keyword=xxx
     */
    @GetMapping("/search")
    public Result<List<Product>> searchProducts(@RequestParam String keyword) {
        log.info("API: 搜索商品, keyword={}", keyword);
        List<Product> products = productService.searchProducts(keyword);
        return Result.success(products);
    }
    
    /**
     * 获取所有商品
     * GET /api/products
     */
    @GetMapping
    public Result<List<Product>> getAllProducts() {
        log.info("API: 获取所有商品");
        List<Product> products = productService.getAllProducts();
        return Result.success(products);
    }
    
    /**
     * 创建商品
     * POST /api/products
     */
    @PostMapping
    public Result<String> createProduct(@RequestBody Product product) {
        log.info("API: 创建商品, product={}", product);
        boolean success = productService.createProduct(product);
        if (success) {
            return Result.success("创建成功");
        }
        return Result.error("创建失败");
    }
    
    /**
     * 更新商品
     * PUT /api/products/{productId}
     */
    @PutMapping("/{productId}")
    public Result<String> updateProduct(@PathVariable String productId, 
                                        @RequestBody Product product) {
        log.info("API: 更新商品, productId={}, product={}", productId, product);
        product.setProductId(productId);
        boolean success = productService.updateProduct(product);
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
    
    /**
     * 删除商品
     * DELETE /api/products/{productId}
     */
    @DeleteMapping("/{productId}")
    public Result<String> deleteProduct(@PathVariable String productId) {
        log.info("API: 删除商品, productId={}", productId);
        boolean success = productService.deleteProduct(productId);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
}
