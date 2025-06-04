package org.csu.petstore;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.csu.petstore.entity.Cart;
import org.csu.petstore.entity.Category;
import org.csu.petstore.entity.Product;
import org.csu.petstore.persistence.CartMapper;
import org.csu.petstore.persistence.CategoryMapper;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.vo.CategoryVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MyPetStoreSsmDevApplicationTests {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private CartMapper cartMapper;

    @Test
    void contextLoads() {
        Category category = categoryMapper.selectById("CATS");
        System.out.println(category);

    }

    @Test
    void test(){
        CategoryVO category = catalogService.getCategory("CATS");
        System.out.println(category.getCategoryId());
    }

    @Test
    void test2(){
        /*QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", "j2ee");
        List<Cart> cartList = cartMapper.selectList(queryWrapper);
        System.out.println(cartList);*/
        // cartMapper.selectById("j2ee");不能这样查询
    }

}
