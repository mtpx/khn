package application.dto;

import lombok.Data;

@Data
public class UserRegisterDTO {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}

