package org.csu.petstore.product.service;

import org.csu.petstore.product.dto.ItemDTO;
import org.csu.petstore.product.entity.Item;

import java.util.List;

/**
 * 商品项服务接口
 */
public interface ItemService {
    
    /**
     * 根据ID获取商品项详情
     */
    ItemDTO getItemById(String itemId);
    
    /**
     * 根据商品ID获取所有商品项
     */
    List<ItemDTO> getItemsByProductId(String productId);
    
    /**
     * 创建商品项
     */
    boolean createItem(Item item);
    
    /**
     * 更新商品项
     */
    boolean updateItem(Item item);
    
    /**
     * 删除商品项
     */
    boolean deleteItem(String itemId);
    
    /**
     * 检查库存是否充足
     */
    boolean checkStock(String itemId, int quantity);
}
