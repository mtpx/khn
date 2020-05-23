package application.service;

import application.dao.UserDAO;
import application.dto.UserChangePasswordDTO;
import application.dto.UserRegisterDTO;
import application.model.User;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service

public class UserEditServiceImpl implements UserEditService {
    final static Logger LOGGER = Logger.getLogger(UserEditServiceImpl.class.getName());

    private final UserDAO userDAO;

    public UserEditServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public ResponseEntity<String> changePassword(UserChangePasswordDTO userChangePasswordDTO) {
        User myUser = userDAO.findByEmail(userChangePasswordDTO.getEmail());
        if (myUser == null)
            return new ResponseEntity<>("User not exists", HttpStatus.BAD_REQUEST);

        String oldPasswordFromDb = myUser.getPassword();

        if (userChangePasswordDTO.getOldPassword().equals(userChangePasswordDTO.getNewPassword())){
            return new ResponseEntity<>("New and old passwords should be different", HttpStatus.BAD_REQUEST);
        }else if (!oldPasswordFromDb.equals(userChangePasswordDTO.getOldPassword())){
            return new ResponseEntity<>("Invalid old password", HttpStatus.BAD_REQUEST);
        }else {
            userDAO.changePassword(myUser, userChangePasswordDTO.getOldPassword(), userChangePasswordDTO.getNewPassword());
            return new ResponseEntity<>("Your password has been changed", HttpStatus.OK);
        }
    }


    @Override
    public ResponseEntity<Object> editUserData(UserRegisterDTO userRegisterDTO, int id) {
        if(userDAO.findByEmail(userRegisterDTO.getEmail())!=null && !userDAO.findById(id).getEmail().equals(userRegisterDTO.getEmail()))
            return new ResponseEntity<>("User with following email exists",HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userDAO.save(prepareUserData(userRegisterDTO,userDAO.findById(id))), HttpStatus.OK);
    }

    @Override
    public User prepareUserData(UserRegisterDTO userRegisterDTO, User user){
        user.setFirstname(userRegisterDTO.getFirstname());
        user.setLastname(userRegisterDTO.getLastname());
        user.setEmail(userRegisterDTO.getEmail());
        user.setPassword(userRegisterDTO.getPassword());
        return user;
    }

}
