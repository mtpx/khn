package application.dto;

import lombok.Data;

@Data
public class UserChangePasswordDTO {
    private String email;
    private String newPassword;
    private String oldPassword;
}

