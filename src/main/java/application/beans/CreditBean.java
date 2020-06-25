package application.beans;

import application.dto.CreditDTO;
import application.service.CreditService;
import lombok.Getter;
import lombok.Setter;
import javax.faces.bean.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Setter
@Getter
@ManagedBean
@ViewScoped
public class CreditBean implements Serializable {

    //credit calc input data
    private double creditInterestRate = 9;
    @Min(2) private int numberOfInstallments;
    @Min(100) private int creditAmount;
    private int monthlyIncome;
    private int monthlyExpenses;

    //credit calc results
    private long amountToRepay, monthlyInstallment;
    private String repaymentDate;

    //credit application
    @Size(min = 2) private String lastName;
    @Size(min = 2) private String firstName;

    //form visibility variables

    private boolean creditCalculationResultVisibility=false;
    private boolean creditApplicationVisibility=false;

    private CreditDTO creditDTO;

    @ManagedProperty("#{creditService}")
    private CreditService creditService;

    @PostConstruct
    public void init() {
    }

    public void calculateCredit(){
        if(monthlyIncome>monthlyExpenses)
            positiveCreditDecision();
        else
            negativeCreditDecision();
    }

    public void applyForCredit() {
        creditApplicationVisibility = true;
        creditService.add(creditDTO);
    }

    private void positiveCreditDecision() {
        creditApplicationVisibility=false;
        creditCalculationResultVisibility = true;
        double y = 1+((creditInterestRate/100)/12);
        monthlyInstallment = Math.round(creditAmount*Math.pow(y,numberOfInstallments)*((y-1)/(Math.pow(y,numberOfInstallments)-1)));
        amountToRepay = monthlyInstallment*numberOfInstallments;
        calculateRepaymentDate(numberOfInstallments);
    }

    private void negativeCreditDecision() {
        creditCalculationResultVisibility = false;
        creditApplicationVisibility = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error", "Your expenses are equal/greater than your income. You cannot apply for a credit"));
    }

    private void calculateRepaymentDate(int numberOfInstallments) {
        Calendar repaymentDateCalendar = Calendar.getInstance();
        repaymentDateCalendar.setTime(new Date());
        repaymentDateCalendar.add(Calendar.MONTH, +numberOfInstallments);
        repaymentDate =  new SimpleDateFormat("yyyy-MM-dd").format(repaymentDateCalendar.getTime());
    }
}
