package org.csu.petstore.vo;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String oldPassword;
    private String newPassword;
}
