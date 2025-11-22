package org.csu.petstore.orderservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.csu.petstore.orderservice.entity.*;
import org.csu.petstore.orderservice.persistence.*;
import org.csu.petstore.orderservice.service.CatalogService;
import org.csu.petstore.orderservice.service.UserService;
import org.csu.petstore.orderservice.vo.AccountVO;
import org.csu.petstore.orderservice.vo.CartItemVO;
import org.csu.petstore.orderservice.vo.CartVO;
import org.csu.petstore.orderservice.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserServiceImpl implements UserService {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CatalogService catalogService;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private ProfileMapper profileMapper;

    @Autowired
    private SignonMapper signonMapper;

    @Autowired
    private BannerDataMapper bannerDataMapper;

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
}
