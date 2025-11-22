package org.csu.petstore.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.csu.petstore.entity.LineItem;
import org.csu.petstore.entity.OrderStatus;
import org.csu.petstore.entity.Orders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Data
public class OrderVO {
    private int orderId;
    private String username;
    private Date orderDate;
    private String shipAddress1;
    private String shipAddress2;
    private String shipCity;
    private String shipState;
    private String shipZip;
    private String shipCountry;
    private String billAddress1;
    private String billAddress2;
    private String billCity;
    private String billState;
    private String billZip;
    private String billCountry;
    private String courier;
    private BigDecimal totalPrice;
    private String billToFirstName;
    private String billToLastName;
    private String shipToFirstName;
    private String shipToLastName;
    private String creditCard;
    private String expiryDate;
    private String cardType;
    private String locale;
    private String status;
    private List<LineItemVO> lineItems = new ArrayList<LineItemVO>();

    public void setOrders(Orders orders) {
        this.orderId = orders.getOrderId();
        this.username = orders.getUsername();
        this.orderDate = orders.getOrderDate();
        this.shipAddress1 = orders.getShipAddress1();
        this.shipAddress2 = orders.getShipAddress2();
        this.shipCity = orders.getShipCity();
        this.shipState = orders.getShipState();
        this.shipZip = orders.getShipZip();
        this.shipCountry = orders.getShipCountry();
        this.billAddress1 = orders.getBillAddress1();
        this.billAddress2 = orders.getBillAddress2();
        this.billCity = orders.getBillCity();
        this.billState = orders.getBillState();
        this.billZip = orders.getBillZip();
        this.billCountry = orders.getBillCountry();
        this.courier = orders.getCourier();
        this.totalPrice = orders.getTotalPrice();
        this.billToFirstName = orders.getBillToFirstName();
        this.billToLastName = orders.getBillToLastName();
        this.shipToFirstName = orders.getShipToFirstName();
        this.shipToLastName = orders.getShipToLastName();
        this.creditCard = orders.getCreditCard();
        this.expiryDate = orders.getExpiryDate();
        this.cardType = orders.getCardType();
        this.locale = orders.getLocale();
    }
    @JsonIgnore
    public Orders getOrders() {
        Orders orders = new Orders();
        orders.setOrderId(this.orderId);
        orders.setUsername(this.username);
        orders.setOrderDate(this.orderDate);
        orders.setShipAddress1(this.shipAddress1);
        orders.setShipAddress2(this.shipAddress2);
        orders.setShipCity(this.shipCity);
        orders.setShipState(this.shipState);
        orders.setShipZip(this.shipZip);
        orders.setShipCountry(this.shipCountry);
        orders.setBillAddress1(this.billAddress1);
        orders.setBillAddress2(this.billAddress2);
        orders.setBillCity(this.billCity);
        orders.setBillState(this.billState);
        orders.setBillZip(this.billZip);
        orders.setBillCountry(this.billCountry);
        orders.setCourier(this.courier);
        orders.setTotalPrice(this.totalPrice);
        orders.setBillToFirstName(this.billToFirstName);
        orders.setBillToLastName(this.billToLastName);
        orders.setShipToFirstName(this.shipToFirstName);
        orders.setShipToLastName(this.shipToLastName);
        orders.setCreditCard(this.creditCard);
        orders.setExpiryDate(this.expiryDate);
        orders.setCardType(this.cardType);
        orders.setLocale(this.locale);
        return orders;
    }
    @JsonIgnore
    public OrderStatus getOrderStatus() {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(this.orderId);
        orderStatus.setStatus(this.status);
        orderStatus.setTimeStamp(this.orderDate);
        return orderStatus;
    }

    public void initOrder(AccountVO account, CartVO cart) {

        username = account.getUsername();
        orderDate = new Date();

        shipToFirstName = account.getFirstName();
        shipToLastName = account.getLastName();
        shipAddress1 = account.getAddress1();
        shipAddress2 = account.getAddress2();
        shipCity = account.getCity();
        shipState = account.getState();
        shipZip = account.getZip();
        shipCountry = account.getCountry();

        billToFirstName = account.getFirstName();
        billToLastName = account.getLastName();
        billAddress1 = account.getAddress1();
        billAddress2 = account.getAddress2();
        billCity = account.getCity();
        billState = account.getState();
        billZip = account.getZip();
        billCountry = account.getCountry();

        totalPrice = cart.getSubTotal();

        creditCard = "999 9999 9999 9999";
        expiryDate = "12/03";
        cardType = "Visa";
        courier = "UPS";
        locale = "CA";
        status = "P";

        Iterator<CartItemVO> i = cart.getCartItems();
        while (i.hasNext()) {
            CartItemVO cartItem = (CartItemVO) i.next();
            addLineItem(cartItem);
        }
    }
    public void addLineItem(CartItemVO cartItem) {
        LineItemVO lineItem = new LineItemVO(lineItems.size() + 1, cartItem);
        addLineItem(lineItem);
    }

    public void addLineItem(LineItemVO LineItemVO) {
        lineItems.add(LineItemVO);
    }
}
