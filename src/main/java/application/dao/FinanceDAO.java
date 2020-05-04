package application.dao;

import application.model.Finance;

public interface FinanceDAO {
    Finance save(Finance finance);
    Finance findByUserId(int userId);
}
