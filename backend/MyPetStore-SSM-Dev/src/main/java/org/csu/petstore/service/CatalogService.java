package org.csu.petstore.service;

import org.csu.petstore.entity.Item;
import org.csu.petstore.entity.Product;
import org.csu.petstore.vo.CategoryVO;
import org.csu.petstore.vo.ItemVO;
import org.csu.petstore.vo.ProductVO;

import java.util.List;

public interface CatalogService{
    List<String> getLanguages();

    List<String> getCategories();

    String getAllFavcategory();

    public CategoryVO getCategory(String categoryId);

    public ProductVO getProduct(String productId);

    public ItemVO getItem(String itemId);

    public List<Product> getProductList(String keyword);

    public boolean isItemInStock(String itemId);

    List<Product> getProductListByCategory(String categoryId);

    List<Item> getItemListByProduct(String productId);

    List<ItemVO> getItemVOListByProduct(String productId);

    boolean checkItemQuantity(String itemId,int quantity);
}
