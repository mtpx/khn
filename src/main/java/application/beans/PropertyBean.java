package application.beans;

import application.model.views.PropertyView;
import application.service.PropertyViewService;
import application.service.UserService;
import lombok.Getter;
import lombok.Setter;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ManagedBean
//@Named
@ViewScoped
public class PropertyBean implements Serializable {

    private double plnEurExchangeRate = 0.26;
    private double plnUsdExchangeRate = 0.23;

    private List<PropertyView> propertiesForSale=null;
    private List<PropertyView> propertiesSold=null;

    private PropertyViewService propertyViewService;
    private UserService userService;

    public PropertyBean(PropertyViewService propertyViewService,UserService userService) {
        this.propertyViewService=propertyViewService;
        this.userService=userService;
    }


    @PostConstruct
    public void init() {
//        userProperty = auctionViewService.findByEmail(userService.getLoggedUser());
    }

    public List<PropertyView> getPropertiesForSale() {
        //wiersze trzymamy w zmiennej w beanie, jeśli zwracamy dane bezpośrednio z serwisu - sortowanie tabeli na froncie nie działa
        //https://stackoverflow.com/questions/5020725
        if (propertiesForSale==null)
            propertiesForSale=propertyViewService.findByEmailForSale(userService.getLoggedUser());
        return propertiesForSale;
    }

    public List<PropertyView> getPropertiesSold() {
        if(propertiesSold==null)
            propertiesSold = propertyViewService.findByEmailSold(userService.getLoggedUser());
        return propertiesSold;
    }

    public int getSellerBalance() {
        return calculateBalance(getPropertiesForSale());
    }

    public int getCustomerBalance() {
        return -calculateBalance(getPropertiesSold());
    }

    private int calculateBalance(List<PropertyView> userProperty){
        int balance = 0;
        for (PropertyView propertyView : userProperty) {
            balance+=propertyView.getPrice();
        }
        return balance;
    }
}
