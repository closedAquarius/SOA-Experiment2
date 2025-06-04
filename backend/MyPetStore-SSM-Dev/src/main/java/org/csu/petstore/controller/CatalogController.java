package org.csu.petstore.controller;

import org.csu.petstore.entity.Item;
import org.csu.petstore.entity.Product;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.service.UserService;
import org.apache.catalina.User;
import org.csu.petstore.entity.Product;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.service.UserService;
import org.csu.petstore.service.impl.UserServiceImpl;
import org.csu.petstore.vo.AccountVO;
import org.csu.petstore.vo.CategoryVO;
import org.csu.petstore.vo.ItemVO;
import org.csu.petstore.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value={"/catalog", "/common"})
@SessionAttributes(value = {"productList"})
public class CatalogController {

    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(Model model)
    {
        model.addAttribute("languages", catalogService.getLanguages());
        model.addAttribute("categories", catalogService.getCategories());
        model.addAttribute("allCategories",catalogService.getAllFavcategory());
        return "catalog/main";
    }

    @GetMapping("/viewCategory")
    public String viewCategory(@RequestParam("categoryId") String categoryId,
                               @ModelAttribute("loginAccount") AccountVO loginAccount,
                               Model model)
    {
        CategoryVO category = catalogService.getCategory(categoryId);
        model.addAttribute("category", category);
        if(loginAccount != null){
            userService.updateCategoryJournal(loginAccount,categoryId);
            userService.updateProfileFavcategory(categoryId,loginAccount);
            List<Product> productList = catalogService.getProductListByCategory(loginAccount.getFavouriteCategoryId());
            model.addAttribute("myList", productList);
        }
        return "catalog/category";
    }

    @GetMapping("/category/{categoryId}")
    public CategoryVO viewCategory(@PathVariable String categoryId)
    {
        CategoryVO category = catalogService.getCategory(categoryId);
        return category;
    }

    @GetMapping("/returnMain")
    public String returnMain(Model model)
    {
        return "catalog/main";
    }

    @GetMapping("/viewProduct")

    public String viewProduct(@RequestParam("productId") String productId,
                              @ModelAttribute("loginAccount") AccountVO loginAccount,
                              Model model)
    {
        model.addAttribute("isAdd", "true");
        //记录商品浏览行为
        if(loginAccount != null)
            userService.addViewProduct(productId, loginAccount.getUsername());
        else
            userService.addViewProduct(productId, "");
        ProductVO product = catalogService.getProduct(productId);
        model.addAttribute("product", product);
        if(loginAccount!= null){
            userService.updateProductJournal(loginAccount,productId);
        }
        return "catalog/product";
    }

    @GetMapping("/product/{productId}")
    public ProductVO viewProduct(@PathVariable String productId)
    {
        return catalogService.getProduct(productId);
    }

    @GetMapping("/viewItem")
    public String viewItem(@RequestParam("itemId") String itemId,
                           @ModelAttribute("loginAccount") AccountVO loginAccount,
                           Model model)
    {
        model.addAttribute("isAdd", "true");
        ItemVO item = catalogService.getItem(itemId);
        ProductVO product = catalogService.getProduct(item.getProductId());
        model.addAttribute("item", item);
        model.addAttribute("product", product);
        if(loginAccount!= null){
            userService.updateProductJournal(loginAccount,itemId);
        }
        return "catalog/item";
    }

    @GetMapping("/item/{itemId}")
    public ItemVO viewItem(@PathVariable String itemId)
    {
        ItemVO item = catalogService.getItem(itemId);
        item.description = catalogService.getProduct(item.getProductId()).getDescription();
        return item;
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        List<Product> productList = catalogService.getProductList(keyword);
        model.addAttribute("productList", productList);
        return "catalog/searchProduct";
    }

    @GetMapping("/productAuto")
    @ResponseBody
    public List<Product> productAutoComplete(@RequestParam("keyword") String keyword,
                                      Model model){
        List<Product> productList = catalogService.getProductList(keyword);
        return productList;
    }

    @GetMapping("/getDetails")
    @ResponseBody
    public List<ItemVO> getDetails( @RequestParam("categoryId") String categoryId,
                                  Model model){
        List<Product> productList=catalogService.getProductListByCategory(categoryId);
        List<ItemVO> itemList=catalogService.getItemVOListByProduct(productList.get(0).getProductId());
        for(int i=1;i<productList.size();i++){
            Product product=productList.get(i);
            List<ItemVO> temp=catalogService.getItemVOListByProduct(product.getProductId());
            itemList.addAll(temp);
        }
        return itemList;
    }

    @GetMapping("/search/{keyword}")
    public List<Product> search(@PathVariable  String keyword){
        return catalogService.getProductList(keyword);
    }

}
