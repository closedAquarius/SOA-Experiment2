package org.csu.petstore.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.petstore.entity.*;
import org.csu.petstore.persistence.*;
import org.csu.petstore.service.CatalogService;
import org.csu.petstore.service.UserService;
import org.csu.petstore.vo.AccountVO;
import org.csu.petstore.vo.CartItemVO;
import org.csu.petstore.vo.CartVO;
import org.csu.petstore.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SignonMapper signonMapper;
    @Autowired
    private BannerDataMapper bannerDataMapper;
    @Autowired
    private ProfileMapper profileMapper;
    @Autowired
    private JournalMapper journalMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CatalogService catalogService;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private ResetPasswordMapper resetPasswordMapper;
    @Autowired
    private ViewProductMapper viewProductMapper;
    @Autowired
    private ProductJournalMapper productJournalMapper;

    @Override
    public Account getAccountByUsernameAndPssword(String username, String password) {
        Signon signon = signonMapper.selectById(username);
        if(signon.getPassword().equals(password)){
            Account account = accountMapper.selectById(username);
            return account;
        }else{
            return null;
        }
    }

    @Override
    public void insertAccount(AccountVO accountVO) {
        accountMapper.insert(accountVO.getAccount());
        signonMapper.insert(accountVO.getSignon());
        profileMapper.insert(accountVO.getProfile());
    }

    @Override
    public Signon getSignonByUsername(String username) {
        Signon signon = signonMapper.selectById(username);
        return signon;
    }

    @Override
    public AccountVO getAccountVOByUsername(String username) {
        Account account = accountMapper.selectById(username);
        Signon signon = signonMapper.selectById(username);
        Profile profile = profileMapper.selectById(username);
        BannerData bannerData = bannerDataMapper.selectById(profile.getFavouriteCategoryId());
        QueryWrapper<Journal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", username);
        List<Journal> journals = journalMapper.selectList(queryWrapper);
        AccountVO accountVO = new AccountVO();
        accountVO.setAccount(account);
        accountVO.setBannerDataAndProfile(bannerData, profile);
        accountVO.setJournals(journals);
        accountVO.setPassword(signon.getPassword());
        return accountVO;
    }

    @Override
    @Transactional
    public void updateJournal(String userId, String description, String date, String color) {
        Journal journal = new Journal();
        journal.setUsername(userId);
        journal.setDescription(description);
        journal.setDate(date);
        journal.setColor(color);
        journalMapper.insert(journal);
    }

    @Override
    public List<Journal> getAllJournals(String username) {
        QueryWrapper<Journal> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", username);
        List<Journal> journals = journalMapper.selectList(queryWrapper);
        return journals;
    }

    @Override
    public void updateAccount(AccountVO accountVO) {
        accountMapper.updateById(accountVO.getAccount());
        profileMapper.updateById(accountVO.getProfile());
        if(accountVO.getPassword() != null && accountVO.getPassword().length() > 0)
            signonMapper.updateById(accountVO.getSignon());
    }

    @Override
    public CartVO getCart(String userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        List<Cart> carts = cartMapper.selectList(queryWrapper);
        CartVO cartVO = new CartVO();
        cartVO.setUsername(userId);
        for(Cart cart : carts) {
            ItemVO itemVO = catalogService.getItem(cart.getItemId());
            Boolean isInblock = catalogService.isItemInStock(itemVO.getItemId());
            cartVO.addItem(itemVO, isInblock,cart);
        }
        return cartVO;
    }

    @Override
    public void addCartItem(String userId, CartItemVO cartItem) {
        Cart cart = new Cart();
        cart.setUsername(userId);
        cart.setItemId(cartItem.getItem().getItemId());
        cart.setQuantity(cartItem.getQuantity());
        cartMapper.insert(cart);
    }

    @Override
    public void updateCart(String userId, CartItemVO cartItem) {
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", userId)
                        .eq("itemid", cartItem.getItem().getItemId())
                .set("quantity", cartItem.getQuantity());
        cartMapper.update(updateWrapper);
    }

    @Override
    public void deleteCart(String userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId);
        cartMapper.delete(queryWrapper);
    }

    @Override
    public void deleteItem(String userId, CartItemVO cartItem) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", userId)
                .eq("itemid", cartItem.getItem().getItemId());
        cartMapper.delete(queryWrapper);
    }

    @Override
    public List<UserAddress> getUserOKAddressByUsername(String username) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username);
        List<UserAddress> userAddresses = userAddressMapper.selectList(queryWrapper);
        List<UserAddress> userAddressList = new ArrayList<>();
        for(UserAddress userAddress : userAddresses){
            if(userAddress != null && userAddress.getStatus().equals("OK")){
                userAddressList.add(userAddress);
            }
        }
        return userAddressList;
    }

    @Override
    public void addUserAddress(UserAddress userAddress) {
        userAddressMapper.insert(userAddress);
    }

    @Override
    public void deleteUserAddress(String username, String addressId) {
        UpdateWrapper<UserAddress> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", username)
                .eq("addrid", addressId)
                .set("status", "NO");
        userAddressMapper.update(updateWrapper);
    }

    @Override
    public void updateMainAddress(String username, String addressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username)
                .eq("addrid", addressId);
        UserAddress userAddress = userAddressMapper.selectOne(queryWrapper);
        Account account = accountMapper.selectById(username);
        account.setFirstName(userAddress.getFirstName());
        account.setLastName(userAddress.getLastName());
        account.setAddress1(userAddress.getAddress1());
        account.setAddress2(userAddress.getAddress2());
        account.setCity(userAddress.getCity());
        account.setState(userAddress.getState());
        account.setZip(userAddress.getZip());
        account.setCountry(userAddress.getCountry());
        accountMapper.updateById(account);
    }

    @Override
    public UserAddress getUserAddressByAddressId(String username, String addressId) {
        QueryWrapper<UserAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userid", username)
               .eq("addrid", addressId);
        UserAddress userAddress = userAddressMapper.selectOne(queryWrapper);
        return userAddress;
    }

    @Override
    public void updateCategoryJournal(AccountVO account, String categoryId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String browseCategoryString = "User "+ account.getUsername() + " browsed the product category: "
                + "<a href=\"categoryForm?categoryId=" + categoryId + "\">" + categoryId + "</a>.";
        updateJournal(account.getUsername(), browseCategoryString, currentDate, "#70AD47");
    }

    @Override
    public void updateProductJournal(AccountVO account, String productId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String browseItemString = "User "+ account.getUsername() + " browsed the product: "
                + "<a href=\"productForm?productId=" + productId + "\">" + productId + "</a>.";
        updateJournal(account.getUsername(), browseItemString, currentDate, "#70AD47");
    }

    @Override
    public void updateItemJournal(AccountVO account, String itemId) {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDate = formatter.format(date);
        String browseItemString = "User "+ account.getUsername() + " browsed the item: "
                + "<a href=\"itemForm?itemId=" + itemId + "\">" + itemId + "</a>.";
        updateJournal(account.getUsername(), browseItemString, currentDate, "#70AD47");
    }

    @Override
    public void updateProfileFavcategory(String favouriteCategoryId, AccountVO account) {
        UpdateWrapper<Profile> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("userid", account.getUsername())
               .set("favcategory", favouriteCategoryId);
        profileMapper.update(updateWrapper);
    }

    @Override
    public void addResetPassword(ResetPassword resetPassword) {
        resetPasswordMapper.insert(resetPassword);

    }

    @Override
    public ResetPassword getResetPasswordByUserId(String userId) {
        ResetPassword check = resetPasswordMapper.selectById(userId);
        return check;
    }

    @Override
    public void addViewProduct(String productId, String username)
    {
        System.out.println(productId);
        System.out.println(username);
        //更新商品浏览总次数
        ViewProduct viewProduct = viewProductMapper.selectById(productId);

        if (viewProduct != null)
        {
            //如果商品存在，viewCount+1
            int currentCount = viewProduct.getViewCount();
            viewProduct.setViewCount(currentCount + 1);
            viewProductMapper.updateById(viewProduct);
        }
        else
        {
            //如果不存在，插入新数据
            viewProduct = new ViewProduct();
            viewProduct.setProductId(productId);
            viewProduct.setViewCount(1);  //第一次访问
            viewProductMapper.insert(viewProduct);
        }

        //记录用户浏览行为
        if(!Objects.equals(username, "") && username != null)
        {
            ProductJournal productJournal = new ProductJournal();
            productJournal.setUserId(username);
            productJournal.setProductId(productId);
            productJournalMapper.insert(productJournal);
        }
    }

    @Override
    public void updateResetPassword(ResetPassword resetPassword) {
        resetPasswordMapper.updateById(resetPassword);
    }

    @Override
    public boolean login(String username, String password) {
        Signon signon = signonMapper.selectById(username);
        if(signon == null){
            return false;
        }
        return signon.getPassword().equals(password);
    }
}
