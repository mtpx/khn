package application.service;

import application.dao.*;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FinanceServiceImpl implements FinanceService {
    final static Logger LOGGER = Logger.getLogger(FinanceServiceImpl.class.getName());
    private final FinanceDAO financeDAO;

    public FinanceServiceImpl(FinanceDAO financeDAO) {
        this.financeDAO = financeDAO;
    }

    @Override
    public Finance addFinanceRecordToUserAfterRegister(User user) {
        Finance finance = new Finance();
        finance.setCurrency("PLN");
        finance.setAmount(1000000); //milion jako prezent za rejestrację :) po to żeby nie grzebać ręcznie w tabeli z finansami
        finance.setUser(user);
        return financeDAO.save(finance);
    }

    @Override
    //odejmowanie kwoty za nieruchomość od stanu konta kupującego
    public Finance changeCustomerFinance(int customerId, int propertyPrice) {
        Finance finance = financeDAO.findByUserId(customerId);
        finance.setAmount(finance.getAmount()-propertyPrice);
        return financeDAO.save(finance);
    }

    @Override
    //dodawanie kwoty za nieruchomość do stanu konta sprzedającego
    public Finance changeSellerFinance(int sellerId, int propertyPrice) {
        Finance finance = financeDAO.findByUserId(sellerId);
        finance.setAmount(finance.getAmount()+propertyPrice);
        return financeDAO.save(finance);
    }

    @Override
    public Finance getFinance(int userId) {
        return financeDAO.findByUserId(userId);
    }
}
