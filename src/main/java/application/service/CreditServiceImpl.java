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
    private final UserCreditsDAO userCreditsDAO;

    public CreditServiceImpl(CreditDAO creditDAO, UserCreditsDAO userCreditsDAO) {
        this.creditDAO=creditDAO;
        this.userCreditsDAO=userCreditsDAO;
    }

    public void add (CreditDTO creditDTO){
        creditDAO.save(createCreditObject(creditDTO));
        userCreditsDAO.save(createUserCreditObject(creditDTO));
    }

    private UserCredits createUserCreditObject(CreditDTO creditDTO) {
        UserCredits userCredits = new UserCredits();
        userCredits.setAmountOfInstallment(creditDTO.getAmountOfInstallments());
        userCredits.setQuantity(creditDTO.getQuantity());


        return userCredits;
    }

    private Credit createCreditObject(CreditDTO creditDTO){
        Credit credit = new Credit();
        credit.setInstallment(creditDTO.getInstallment());
        credit.setName(creditDTO.getFirstName()+" "+creditDTO.getLastName());
        return credit;
    }
}
