package application.service;

import application.model.Finance;
import application.model.User;

public interface FinanceService {
    void addFinanceRecordToUser(User user);
    void changeFinanceAfterTransaction(int customerId, int sellerId, int propertyPrice);
    void subtractAmountFromFinance(int userId, int amount);
    void addAmountToFinance(int userId, int amount);
    Finance getFinance(int userId);
}
