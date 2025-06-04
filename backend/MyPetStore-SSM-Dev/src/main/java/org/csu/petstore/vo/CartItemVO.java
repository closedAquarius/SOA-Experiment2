package org.csu.petstore.vo;

import lombok.Data;
import org.csu.petstore.entity.Item;

import java.math.BigDecimal;

@Data
public class CartItemVO {
    private ItemVO item;
    private int quantity;
    private boolean inStock;
    private BigDecimal total;

    public void incrementQuantity() {
        quantity++;
        calculateTotal();
    }

    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }

    public BigDecimal getTotal() {
        calculateTotal();
        return total;
    }
}
