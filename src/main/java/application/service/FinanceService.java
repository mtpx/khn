package application.service;

import application.model.Finance;
import application.model.User;

public interface FinanceService {
    Finance addFinanceToUser(User user);
    Finance changeCustomerFinance(int customerId, int propertyPrice);
    Finance changeSellerFinance(int sellerId, int propertyPrice);
    Finance getFinance(int userId);
}
