package application.service;

import application.dao.*;
import application.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("financeService")
public class FinanceServiceImpl implements FinanceService {
    final static Logger LOGGER = Logger.getLogger(FinanceServiceImpl.class.getName());
    private final FinanceDAO financeDAO;

    public FinanceServiceImpl(FinanceDAO financeDAO) {
        this.financeDAO = financeDAO;
    }

    @Override
    public void addFinanceRecordToUser(User user) {
        financeDAO.save(fillFinanceRecord(user));
    }

    @Override
    public void changeFinanceAfterTransaction(int customerId, int sellerId, int propertyPrice) {
        subtractAmountFromFinance(customerId,propertyPrice);
        addAmountToFinance(sellerId,propertyPrice);
    }

    @Override  //odejmowanie kwoty od stanu konta użytkownika
    public void subtractAmountFromFinance(int userId, int amount) {
        Finance finance = financeDAO.findByUserId(userId);

        finance.setAmount(finance.getAmount()-amount);
        financeDAO.save(finance);
    }

    @Override  //odejmowanie kwoty od stanu konta użytkownika
    public void addAmountToFinance(int userId, int amount) {
        Finance finance = financeDAO.findByUserId(userId);

        finance.setAmount(finance.getAmount()+amount);
        financeDAO.save(finance);
    }

    @Override
    public Finance getFinance(int userId) {
        return financeDAO.findByUserId(userId);
    }

    private Finance fillFinanceRecord(User user) {
        Finance finance = new Finance();
        finance.setCurrency("PLN");
        finance.setAmount(1000000); //milion jako prezent za rejestrację :) po to żeby nie grzebać ręcznie w tabeli z finansami
        finance.setUser(user);
        return finance;
    }
}
