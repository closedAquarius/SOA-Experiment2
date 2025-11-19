package org.csu.petstore.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.ItemDTO;
import org.csu.petstore.product.entity.Item;
import org.csu.petstore.product.entity.ItemQuantity;
import org.csu.petstore.product.entity.Product;
import org.csu.petstore.product.mapper.ItemMapper;
import org.csu.petstore.product.mapper.ItemQuantityMapper;
import org.csu.petstore.product.mapper.ProductMapper;
import org.csu.petstore.product.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品项服务实现类
 */
@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    
    @Autowired
    private ItemMapper itemMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ItemQuantityMapper itemQuantityMapper;
    
    @Override
    public ItemDTO getItemById(String itemId) {
        log.info("查询商品项详情, itemId={}", itemId);
        
        Item item = itemMapper.selectById(itemId);
        if (item == null) {
            log.warn("商品项不存在, itemId={}", itemId);
            return null;
        }
        
        ItemDTO itemDTO = new ItemDTO();
        BeanUtils.copyProperties(item, itemDTO);
        
        // 获取商品名称
        Product product = productMapper.selectById(item.getProductId());
        if (product != null) {
            itemDTO.setProductName(product.getName());
            itemDTO.setDescription(product.getDescription());
        }
        
        // 获取库存数量
        ItemQuantity itemQuantity = itemQuantityMapper.selectById(itemId);
        if (itemQuantity != null) {
            itemDTO.setQuantity(itemQuantity.getQuantity());
        }
        
        return itemDTO;
    }
    
    @Override
    public List<ItemDTO> getItemsByProductId(String productId) {
        log.info("查询商品的所有商品项, productId={}", productId);
        
        QueryWrapper<Item> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("productid", productId);
        List<Item> items = itemMapper.selectList(queryWrapper);
        
        return items.stream().map(item -> {
            ItemDTO dto = new ItemDTO();
            BeanUtils.copyProperties(item, dto);
            
            // 获取库存
            ItemQuantity itemQuantity = itemQuantityMapper.selectById(item.getItemId());
            if (itemQuantity != null) {
                dto.setQuantity(itemQuantity.getQuantity());
            }
            
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public boolean createItem(Item item) {
        log.info("创建商品项, item={}", item);
        return itemMapper.insert(item) > 0;
    }
    
    @Override
    public boolean updateItem(Item item) {
        log.info("更新商品项, item={}", item);
        return itemMapper.updateById(item) > 0;
    }
    
    @Override
    public boolean deleteItem(String itemId) {
        log.info("删除商品项, itemId={}", itemId);
        return itemMapper.deleteById(itemId) > 0;
    }
    
    @Override
    public boolean checkStock(String itemId, int quantity) {
        log.info("检查库存, itemId={}, quantity={}", itemId, quantity);
        
        ItemQuantity itemQuantity = itemQuantityMapper.selectById(itemId);
        if (itemQuantity == null) {
            log.warn("库存信息不存在, itemId={}", itemId);
            return false;
        }
        
        return itemQuantity.getQuantity() >= quantity;
    }
}
