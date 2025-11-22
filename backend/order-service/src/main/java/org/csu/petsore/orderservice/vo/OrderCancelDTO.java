package org.csu.petstore.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OrderCancelDTO {
    private String orderId;
    private String reason;
    private String description;
    private MultipartFile image;
}
