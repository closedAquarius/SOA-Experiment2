package org.csu.petstore.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemVO {
    private String itemId;

    private String productId;
    private String productName;
    private BigDecimal listPrice;

    public String description;
    private String attribute1;
    private String attribute2;
    private String attribute3;
    private String attribute4;
    private String attribute5;

    private Integer quantity;
}
