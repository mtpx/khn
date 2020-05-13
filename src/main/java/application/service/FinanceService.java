package application.service;

import application.model.Finance;
import application.model.User;

public interface FinanceService {
    //3 poniższe metody chyba moga być typu void, osytatecznie zapisujemy obiekt, nic nie zwracamy
    Finance addFinanceRecordToUserAfterRegister(User user);
    Finance changeCustomerFinance(int customerId, int propertyPrice);
    Finance changeSellerFinance(int sellerId, int propertyPrice);
    Finance getFinance(int userId);
}
