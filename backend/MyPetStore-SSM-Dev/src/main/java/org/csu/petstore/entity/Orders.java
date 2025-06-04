package org.csu.petstore.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("orders")
public class Orders {
    @TableId(value = "orderid")
    private int orderId;
    @TableField(value = "userid")
    private String username;
    @TableField(value = "orderdate")
    private Date orderDate;
    @TableField(value = "shipaddr1")
    private String shipAddress1;
    @TableField(value = "shipaddr2")
    private String shipAddress2;
    @TableField(value = "shipcity")
    private String shipCity;
    @TableField(value = "shipstate")
    private String shipState;
    @TableField(value = "shipzip")
    private String shipZip;
    @TableField(value = "shipcountry")
    private String shipCountry;
    @TableField(value = "billaddr1")
    private String billAddress1;
    @TableField(value = "billaddr2")
    private String billAddress2;
    @TableField(value = "billcity")
    private String billCity;
    @TableField(value = "billstate")
    private String billState;
    @TableField(value = "billzip")
    private String billZip;
    @TableField(value = "billcountry")
    private String billCountry;
    private String courier;
    @TableField(value = "totalprice")
    private BigDecimal totalPrice;
    @TableField(value = "billtofirstname")
    private String billToFirstName;
    @TableField(value = "billtolastname")
    private String billToLastName;
    @TableField(value = "shiptofirstname")
    private String shipToFirstName;
    @TableField(value = "shiptolastname")
    private String shipToLastName;
    @TableField(value = "creditcard")
    private String creditCard;
    @TableField(value = "exprdate")
    private String expiryDate;
    @TableField(value = "cardtype")
    private String cardType;
    @TableField(value = "locale")
    private String locale;
}
