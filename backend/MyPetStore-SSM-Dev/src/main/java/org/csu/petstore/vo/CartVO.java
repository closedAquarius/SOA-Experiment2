package org.csu.petstore.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.csu.petstore.entity.Cart;
import org.csu.petstore.entity.Item;

import java.math.BigDecimal;
import java.util.*;

@Data
public class CartVO {
    String username;
    @JsonIgnore
    private final Map<String, CartItemVO> itemMap = Collections.synchronizedMap(new HashMap<String, CartItemVO>());
    private final List<CartItemVO> itemList = new ArrayList<CartItemVO>();

    @JsonIgnore
    public Iterator<CartItemVO> getCartItems() {
        return itemList.iterator();
    }
    public void addItem(ItemVO item, boolean isInStock, Cart cart) {
        CartItemVO cartItem = (CartItemVO) itemMap.get(item.getItemId());
        //System.out.println("before add:"+item.getQuantity());
        if (cartItem == null) {
            cartItem = new CartItemVO();
            cartItem.setItem(item);
            cartItem.setQuantity(cart.getQuantity());
            cartItem.setInStock(isInStock);
            itemMap.put(item.getItemId(), cartItem);
            itemList.add(cartItem);
        }else{
            cartItem.incrementQuantity();
        }
        //System.out.println("add item, quantity: " + cartItem.getQuantity());
    }

    public int getNumberOfItems() {
        return itemList.size();
    }

    public BigDecimal getSubTotal() {
        BigDecimal subTotal = new BigDecimal("0");
        Iterator<CartItemVO> items = getCartItems();
        while (items.hasNext()) {
            CartItemVO cartItem = (CartItemVO) items.next();
            ItemVO item = cartItem.getItem();
            BigDecimal listPrice = item.getListPrice();
            BigDecimal quantity = new BigDecimal(String.valueOf(cartItem.getQuantity()));
            subTotal = subTotal.add(listPrice.multiply(quantity));
        }
        return subTotal;
    }
}
