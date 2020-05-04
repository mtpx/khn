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
    public Finance addFinanceToUser(User user) {
        Finance finance = new Finance();
        finance.setCurrency("PLN");
        finance.setAmount(0);
        finance.setUser(user);
        return financeDAO.save(finance);
    }

    @Override
    public Finance changeCustomerFinance(int customerId, int propertyPrice) {
        Finance finance = financeDAO.findByUserId(customerId);
        int oldValue = finance.getAmount();
        int newValue = oldValue-propertyPrice;
        finance.setAmount(newValue);
        return financeDAO.save(finance);
    }

    @Override
    public Finance changeSellerFinance(int sellerId, int propertyPrice) {
        Finance finance = financeDAO.findByUserId(sellerId);
        int oldValue = finance.getAmount();
        int newValue = oldValue+propertyPrice;
        finance.setAmount(newValue);
        return financeDAO.save(finance);
    }

    @Override
    public Finance getFinance(int userId) {
        return financeDAO.findByUserId(userId);
    }
}
