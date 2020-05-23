package application.service;

import application.dto.UserChangePasswordDTO;
import application.dto.UserRegisterDTO;
import application.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserEditService {
    ResponseEntity<String> changePassword(UserChangePasswordDTO userChangePasswordDTO);
    ResponseEntity<Object> editUserData(UserRegisterDTO userRegisterDTO, int id);

    User prepareUserData(UserRegisterDTO userRegisterDTO, User user);
}
