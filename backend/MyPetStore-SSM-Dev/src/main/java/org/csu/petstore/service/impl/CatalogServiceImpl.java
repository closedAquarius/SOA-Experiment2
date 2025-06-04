package org.csu.petstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstore.entity.Category;
import org.csu.petstore.entity.Item;
import org.csu.petstore.entity.ItemQuantity;
import org.csu.petstore.entity.Product;
import org.csu.petstore.persistence.*;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.vo.CategoryVO;
import org.csu.petstore.vo.ItemVO;
import org.csu.petstore.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("catalogService")
public class CatalogServiceImpl implements CatalogService
{
    private static final List<String> LANGUAGE_LIST;
    private static final List<String> CATEGORY_LIST;
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemQuantityMapper itemQuantityMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private ProfileMapper profileMapper;
    static
    {
        LANGUAGE_LIST = Arrays.asList("english", "japanese");
        CATEGORY_LIST = Arrays.asList("FISH", "DOGS", "REPTILES", "CATS", "BIRDS");
    }

    public List<String> getLanguages() {
        return LANGUAGE_LIST;
    }

    public List<String> getCategories() {
        return CATEGORY_LIST;
    }

    @Override
    public String getAllFavcategory() {
        List<String> result=profileMapper.getMostFrequentCategory();
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public CategoryVO getCategory(String categoryId)
    {
        CategoryVO categoryVO = new CategoryVO();
        Category category = categoryMapper.selectById(categoryId);

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category" , categoryId);
        List<Product> productList = productMapper.selectList(queryWrapper);

        categoryVO.setCategoryId(categoryId);
        categoryVO.setCategoryName(category.getName());
        categoryVO.setProductList(productList);

        return categoryVO;

    }

    @Override
    public ProductVO getProduct(String productId)
    {
        ProductVO productVO = new ProductVO();
        Product product = productMapper.selectById(productId);
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid" , productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);

        productVO.setProductId(productId);
        productVO.setCategoryId(product.getCategoryId());
        productVO.setProductName(product.getName());
        productVO.setItemList(itemList);
        productVO.setDescription(product.getDescription());

        return productVO;
    }

    @Override
    public ItemVO getItem(String itemId)
    {
        ItemVO itemVO = new ItemVO();
        Item item = itemMapper.selectById(itemId);
        Product product = productMapper.selectById(item.getProductId());
        ItemQuantity itemQuantity = itemQuantityMapper.selectById(itemId);

        itemVO.setItemId(itemId);
        itemVO.setProductId(product.getProductId());
        itemVO.setProductName(product.getName());
        itemVO.setListPrice(item.getListPrice());
        itemVO.setAttribute1(item.getAttribute1());
        itemVO.setAttribute2(item.getAttribute2());
        itemVO.setAttribute3(item.getAttribute3());
        itemVO.setAttribute4(item.getAttribute4());
        itemVO.setAttribute5(item.getAttribute5());
        itemVO.setQuantity(itemQuantity.getQuantity());
        return itemVO;
    }

    @Override
    public List<Product> getProductList(String keyword) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name" , keyword);
        List<Product> productList = productMapper.selectList(queryWrapper);
        return productList;
    }

    @Override
    public boolean isItemInStock(String itemId) {
        int quantity = inventoryMapper.selectById(itemId).getQuantity();
        return quantity > 0;
    }

    @Override
    public List<Product> getProductListByCategory(String categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category" , categoryId);
        List<Product> productList = productMapper.selectList(queryWrapper);
        return productList;
    }

    @Override
    public List<Item> getItemListByProduct(String productId){
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid" , productId);
        List<Item> itemList = itemMapper.selectList(queryWrapper);
        return itemList;
    }

    @Override
    public List<ItemVO> getItemVOListByProduct(String productId) {
        List<Item> itemList = getItemListByProduct(productId);
        List<ItemVO> itemVOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemVO itemVO = getItem(item.getItemId());
            itemVOList.add(itemVO);
        }
        return itemVOList;
    }

    @Override
    public boolean checkItemQuantity(String itemId,int quantity) {
        int itemQuantity = inventoryMapper.selectById(itemId).getQuantity();
        System.out.println(itemQuantity+"    "+quantity);
        return quantity <= itemQuantity;
    }
}
