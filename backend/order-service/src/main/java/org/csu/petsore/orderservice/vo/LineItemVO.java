package org.csu.petstore.vo;

import lombok.Data;
import org.csu.petstore.entity.LineItem;

import java.math.BigDecimal;

@Data
public class LineItemVO {
    private int orderId;
    private int lineNumber;
    private int quantity;
    private String itemId;
    private BigDecimal unitPrice;
    private ItemVO item;
    private BigDecimal total;

    public LineItemVO() {}
    public LineItemVO(int lineNumber, CartItemVO cartItem) {
        this.lineNumber = lineNumber;
        this.quantity = cartItem.getQuantity();
        this.itemId = cartItem.getItem().getItemId();
        this.unitPrice = cartItem.getItem().getListPrice();
        this.item = cartItem.getItem();
        calculateTotal();
    }

    public void setLineItem(LineItem lineItem) {
        this.orderId = lineItem.getOrderId();
        this.lineNumber = lineItem.getLineNumber();
        this.quantity = lineItem.getQuantity();
        this.itemId = lineItem.getItemId();
        this.unitPrice = lineItem.getUnitPrice();
        calculateTotal();
    }
    public LineItem getLineItem() {
        LineItem lineItem = new LineItem();
        lineItem.setOrderId(orderId);
        lineItem.setLineNumber(lineNumber);
        lineItem.setQuantity(quantity);
        lineItem.setItemId(itemId);
        lineItem.setUnitPrice(unitPrice);
        return lineItem;
    }
    private void calculateTotal() {
        if (item != null && item.getListPrice() != null) {
            total = item.getListPrice().multiply(new BigDecimal(quantity));
        } else {
            total = null;
        }
    }
}
