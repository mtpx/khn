package application.service;

import application.dao.CreditDAO;
import application.dao.UserCreditsDAO;
import application.dto.CreditDTO;
import application.model.Credit;
import application.model.UserCredits;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("creditService")
public class CreditServiceImpl implements CreditService {

    final static Logger LOGGER = Logger.getLogger(CreditServiceImpl.class.getName());
    private final CreditDAO creditDAO;
    private final UserService userService;
    private final FinanceService financeService;
    private final UserCreditsDAO userCreditsDAO;

    public CreditServiceImpl(CreditDAO creditDAO, UserService userService, FinanceService financeService, UserCreditsDAO userCreditsDAO) {
        this.creditDAO=creditDAO;
        this.userService = userService;
        this.financeService = financeService;
        this.userCreditsDAO = userCreditsDAO;
    }

    public void add(CreditDTO creditDTO){
        Credit credit = saveCreditObject(creditDTO);
        saveUserCreditsObject(credit);
        updateUserFinance(userService.getLoggedUserId(),creditDTO.getCreditAmount());
    }

    private void updateUserFinance(int loggedUserId, Integer creditAmount) {
        financeService.addAmountToFinance(loggedUserId,creditAmount);
    }

    private Credit saveCreditObject(CreditDTO creditDTO){
        Credit credit = new Credit();
        credit.setInstallment(creditDTO.getInstallment());
        credit.setName(creditDTO.getName());
        return creditDAO.save(credit);
    }

    private UserCredits saveUserCreditsObject(Credit credit){
        UserCredits userCredits = new UserCredits();
        userCredits.setCredits(credit);
        userCredits.setUser(userService.findById(userService.getLoggedUserId()));
        return userCreditsDAO.save(userCredits);
    }
}
