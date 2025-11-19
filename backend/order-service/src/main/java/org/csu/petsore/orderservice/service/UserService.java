package org.csu.petsore.orderservice.service;

import org.csu.petsore.orderservice.entity.UserAddress;
import org.csu.petsore.orderservice.vo.AccountVO;
import org.csu.petsore.orderservice.vo.CartItemVO;
import org.csu.petsore.orderservice.vo.CartVO;

import java.util.List;

public interface UserService {
    CartVO getCart(String userId);
    void addCartItem(String userId, CartItemVO cartItem);
    void updateCart(String userId, CartItemVO cartItem);
    void deleteItem(String userId, CartItemVO cartItem);
    void deleteCart(String userId);

    List<UserAddress> getUserOKAddressByUsername(String username);
    void addUserAddress(UserAddress userAddress);
    void deleteUserAddress(String username, String addressId);
    void updateMainAddress(String username, String addressId);
    UserAddress getUserAddressByAddressId(String username,String addressId);

    public AccountVO getAccountVOByUsername(String username);

    public void updateJournal(String userId, String description, String date, String color);
}
