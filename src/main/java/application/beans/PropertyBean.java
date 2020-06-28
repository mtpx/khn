package application.beans;

import application.model.views.PropertyView;
import application.service.FinanceService;
import application.service.PropertyViewService;
import application.service.UserService;
import lombok.Getter;
import lombok.Setter;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ManagedBean
@ViewScoped
public class PropertyBean implements Serializable {

    BigDecimal plnEurExchangeRate = new BigDecimal("0.26");
    BigDecimal plnUsdExchangeRate = new BigDecimal("0.23");

    private List<PropertyView> propertiesForSale;
    private List<PropertyView> propertiesSold;

    @ManagedProperty("#{userService}")
    private UserService userService;

    @ManagedProperty("#{financeService}")
    private FinanceService financeService;

    @ManagedProperty("#{propertyViewService}")
    private PropertyViewService propertyViewService;

    @PostConstruct
    public void init() {
        propertiesForSale = propertyViewService.findByEmailForSale(userService.getLoggedUserMail());
        propertiesSold = propertyViewService.findByEmailSold(userService.getLoggedUserMail());
    }

    public double getBalance(){
        return financeService.getFinance(userService.getLoggedUserId()).getAmount();
    }

    public BigDecimal getBalanceEUR(){
        return new BigDecimal(financeService.getFinance(userService.getLoggedUserId()).getAmount()).multiply(plnEurExchangeRate);
    }

    public BigDecimal getBalanceUSD(){
        return new BigDecimal(financeService.getFinance(userService.getLoggedUserId()).getAmount()).multiply(plnUsdExchangeRate);
    }
}
