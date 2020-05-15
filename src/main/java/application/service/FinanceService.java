package application.service;

import application.model.Finance;
import application.model.User;

public interface FinanceService {
    void addFinanceRecordToUser(User user);
    void changeFinanceAfterTransaction(int customerId, int sellerId, int propertyPrice);
    void changeCustomerFinance(int customerId, int propertyPrice);
    void changeSellerFinance(int sellerId, int propertyPrice);
    Finance getFinance(int userId);
}
