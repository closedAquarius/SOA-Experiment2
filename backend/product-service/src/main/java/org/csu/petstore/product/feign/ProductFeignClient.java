package org.csu.petstore.product.feign;

import org.csu.petstore.product.dto.ItemDTO;
import org.csu.petstore.product.dto.ProductDTO;
import org.csu.petstore.product.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 商品服务Feign客户端接口
 * 供其他微服务（如订单服务、用户服务）调用
 * 
 * @FeignClient(name = "product-service") 指定要调用的服务名称
 */
@FeignClient(name = "product-service")
public interface ProductFeignClient {
    
    /**
     * 获取商品详情
     */
    @GetMapping("/api/products/{productId}")
    Result<ProductDTO> getProduct(@PathVariable("productId") String productId);
    
    /**
     * 获取商品项详情
     */
    @GetMapping("/api/items/{itemId}")
    Result<ItemDTO> getItem(@PathVariable("itemId") String itemId);
    
    /**
     * 获取商品的所有商品项
     */
    @GetMapping("/api/items/product/{productId}")
    Result<List<ItemDTO>> getItemsByProduct(@PathVariable("productId") String productId);
    
    /**
     * 检查库存
     */
    @PostMapping("/api/items/check-stock")
    Result<Boolean> checkStock(@RequestParam("itemId") String itemId, 
                               @RequestParam("quantity") int quantity);
    
    /**
     * 搜索商品
     */
    @GetMapping("/api/products/search")
    Result<List<ProductDTO>> searchProducts(@RequestParam("keyword") String keyword);
}
