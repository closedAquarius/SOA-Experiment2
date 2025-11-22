package org.csu.petstore.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.ItemDTO;
import org.csu.petstore.product.dto.Result;
import org.csu.petstore.product.entity.Item;
import org.csu.petstore.product.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品项Controller - RESTful API
 */
@Slf4j
@RestController
@RequestMapping("/api/items")
public class ItemController {
    
    @Autowired
    private ItemService itemService;
    
    /**
     * 获取商品项详情
     * GET /api/items/{itemId}
     */
    @GetMapping("/{itemId}")
    public Result<ItemDTO> getItem(@PathVariable String itemId) {
        log.info("API: 获取商品项详情, itemId={}", itemId);
        ItemDTO item = itemService.getItemById(itemId);
        if (item == null) {
            return Result.error("商品项不存在");
        }
        return Result.success(item);
    }
    
    /**
     * 获取商品的所有商品项
     * GET /api/items/product/{productId}
     */
    @GetMapping("/product/{productId}")
    public Result<List<ItemDTO>> getItemsByProduct(@PathVariable String productId) {
        log.info("API: 获取商品的所有商品项, productId={}", productId);
        List<ItemDTO> items = itemService.getItemsByProductId(productId);
        return Result.success(items);
    }
    
    /**
     * 创建商品项
     * POST /api/items
     */
    @PostMapping
    public Result<String> createItem(@RequestBody Item item) {
        log.info("API: 创建商品项, item={}", item);
        boolean success = itemService.createItem(item);
        if (success) {
            return Result.success("创建成功");
        }
        return Result.error("创建失败");
    }
    
    /**
     * 更新商品项
     * PUT /api/items/{itemId}
     */
    @PutMapping("/{itemId}")
    public Result<String> updateItem(@PathVariable String itemId, 
                                     @RequestBody Item item) {
        log.info("API: 更新商品项, itemId={}, item={}", itemId, item);
        item.setItemId(itemId);
        boolean success = itemService.updateItem(item);
        if (success) {
            return Result.success("更新成功");
        }
        return Result.error("更新失败");
    }
    
    /**
     * 删除商品项
     * DELETE /api/items/{itemId}
     */
    @DeleteMapping("/{itemId}")
    public Result<String> deleteItem(@PathVariable String itemId) {
        log.info("API: 删除商品项, itemId={}", itemId);
        boolean success = itemService.deleteItem(itemId);
        if (success) {
            return Result.success("删除成功");
        }
        return Result.error("删除失败");
    }
    
    /**
     * 检查库存
     * POST /api/items/check-stock
     */
    @PostMapping("/check-stock")
    public Result<Boolean> checkStock(@RequestParam String itemId, 
                                      @RequestParam int quantity) {
        log.info("API: 检查库存, itemId={}, quantity={}", itemId, quantity);
        boolean inStock = itemService.checkStock(itemId, quantity);
        return Result.success(inStock);
    }
}
