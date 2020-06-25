package application.dao;

import application.model.UserCredits;

public interface UserCreditsDAO {
    UserCredits save(UserCredits userCredits);
    UserCredits findById(int id);
}
