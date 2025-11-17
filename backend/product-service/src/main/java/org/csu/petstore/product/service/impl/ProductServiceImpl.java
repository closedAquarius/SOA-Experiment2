package org.csu.petstore.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.csu.petstore.product.dto.ItemDTO;
import org.csu.petstore.product.dto.ProductDTO;
import org.csu.petstore.product.entity.Product;
import org.csu.petstore.product.mapper.ProductMapper;
import org.csu.petstore.product.service.ItemService;
import org.csu.petstore.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品服务实现类
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private ItemService itemService;
    
    @Override
    public ProductDTO getProductById(String productId) {
        log.info("查询商品详情, productId={}", productId);
        
        Product product = productMapper.selectById(productId);
        if (product == null) {
            log.warn("商品不存在, productId={}", productId);
            return null;
        }
        
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        
        // 获取该商品的所有商品项
        List<ItemDTO> items = itemService.getItemsByProductId(productId);
        productDTO.setItems(items);
        
        return productDTO;
    }
    
    @Override
    public List<ProductDTO> getProductsByCategory(String categoryId) {
        log.info("按分类查询商品, categoryId={}", categoryId);
        
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category", categoryId);
        List<Product> products = productMapper.selectList(queryWrapper);
        
        return products.stream().map(product -> {
            ProductDTO dto = new ProductDTO();
            BeanUtils.copyProperties(product, dto);
            return dto;
        }).collect(Collectors.toList());
    }
    
    @Override
    public List<Product> searchProducts(String keyword) {
        log.info("搜索商品, keyword={}", keyword);
        
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", keyword);
        return productMapper.selectList(queryWrapper);
    }
    
    @Override
    public boolean createProduct(Product product) {
        log.info("创建商品, product={}", product);
        return productMapper.insert(product) > 0;
    }
    
    @Override
    public boolean updateProduct(Product product) {
        log.info("更新商品, product={}", product);
        return productMapper.updateById(product) > 0;
    }
    
    @Override
    public boolean deleteProduct(String productId) {
        log.info("删除商品, productId={}", productId);
        return productMapper.deleteById(productId) > 0;
    }
    
    @Override
    public List<Product> getAllProducts() {
        log.info("获取所有商品");
        return productMapper.selectList(null);
    }
}
