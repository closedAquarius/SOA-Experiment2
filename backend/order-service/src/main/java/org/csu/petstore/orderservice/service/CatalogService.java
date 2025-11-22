package org.csu.petstore.orderservice.service;

import org.csu.petstore.orderservice.entity.Product;
import org.csu.petstore.orderservice.vo.ItemVO;

import java.util.List;

public interface CatalogService {
    public ItemVO getItem(String itemId);

    List<Product> getProductListByCategory(String categoryId);

    public boolean isItemInStock(String itemId);

    boolean checkItemQuantity(String itemId,int quantity);
}
