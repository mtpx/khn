package application.service;

import application.dao.UserDAO;
import application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User addCustomer(User user) {
        return userDAO.addCustomer(user);
    }
}
