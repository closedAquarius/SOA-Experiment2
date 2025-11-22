package org.csu.petstore.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstore.orderservice.entity.Item;
import org.csu.petstore.orderservice.entity.ItemQuantity;
import org.csu.petstore.orderservice.entity.Product;
import org.csu.petstore.orderservice.persistence.InventoryMapper;
import org.csu.petstore.orderservice.persistence.ItemMapper;
import org.csu.petstore.orderservice.persistence.ItemQuantityMapper;
import org.csu.petstore.orderservice.persistence.ProductMapper;
import org.csu.petstore.orderservice.service.CatalogService;
import org.csu.petstore.orderservice.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemQuantityMapper itemQuantityMapper;

    @Autowired
    private InventoryMapper inventoryMapper;

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
    public List<Product> getProductListByCategory(String categoryId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category" , categoryId);
        List<Product> productList = productMapper.selectList(queryWrapper);
        return productList;
    }

    @Override
    public boolean isItemInStock(String itemId) {
        int quantity = inventoryMapper.selectById(itemId).getQuantity();
        return quantity > 0;
    }

    @Override
    public boolean checkItemQuantity(String itemId,int quantity) {
        int itemQuantity = inventoryMapper.selectById(itemId).getQuantity();
        System.out.println(itemQuantity+"    "+quantity);
        return quantity <= itemQuantity;
    }
}
