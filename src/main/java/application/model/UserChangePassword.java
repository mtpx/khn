package application.model;

import lombok.Data;

@Data
public class UserChangePassword {
    private String email;
    private String newPassword;
    private String oldPassword;
}

