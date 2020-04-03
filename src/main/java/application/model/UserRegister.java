package application.model;

import lombok.Data;

@Data
public class UserRegister {
    private String email;
    private String firstname;
    private String lastname;
    private String password;
}

