package org.csu.petstore.service;

import org.csu.petstore.entity.Account;
import org.csu.petstore.entity.Journal;
import org.csu.petstore.entity.ResetPassword;
import org.csu.petstore.entity.Signon;
import org.csu.petstore.entity.UserAddress;
import org.csu.petstore.vo.AccountVO;
import org.csu.petstore.vo.CartItemVO;
import org.csu.petstore.vo.CartVO;

import java.util.List;

public interface UserService {

    /*Account getAccountByUsernameAndPssword(String username,String password);
    AccountVO getAccountVOByUsername(String username);
    Signon getSignonByUsername(String username);
    void insertAccount(AccountVO accountVO);
    void updateJournal(String userId, String description, String date, String color);
    List<Journal> getAllJournals(String username);
    void updateAccount(AccountVO accountVO);*/

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

    void updateCategoryJournal(AccountVO account,String categoryId);
    void updateProductJournal(AccountVO account,String productId);
    void updateItemJournal(AccountVO account,String itemId);
    void updateProfileFavcategory(String favouriteCategoryId, AccountVO account);


    public Account getAccountByUsernameAndPssword(String username,String password);
    public AccountVO getAccountVOByUsername(String username);
    public Signon getSignonByUsername(String username);
    public void insertAccount(AccountVO accountVO);
    public void updateJournal(String userId, String description, String date, String color);
    public List<Journal> getAllJournals(String username);
    public void updateAccount(AccountVO accountVO);
    public void addResetPassword(ResetPassword resetPassword);
    public ResetPassword getResetPasswordByUserId(String userId);
    public void addViewProduct(String productId, String username);
    public void updateResetPassword(ResetPassword resetPassword);
    public boolean login(String username, String password);
}
