let creditCalculateResults, creditInterestRate, numberOfInstallments,creditAmount,monthlyIncome,monthlyExpenses, errors;
let amountToRepayOutput, monthlyInstallmentOutput, repaymentDateOutput;
let creditApplicationData;
let creditApplicationAmountToRepayOutput, creditApplicationMonthlyInstallmentOutput, creditApplicationRepaymentDateOutput;
let creditApplicationMonthlyIncome,creditApplicationMonthlyExpenses;
$(document).ready(function() {
    creditCalculateResults = $("#creditCalculateResults");
    creditInterestRate = $("#creditInterestRate");
    numberOfInstallments = $("#numberOfInstallments");
    creditAmount = $("#creditAmount");
    monthlyIncome = $("#monthlyIncome");
    monthlyExpenses = $("#monthlyExpenses");

    amountToRepayOutput = $("#amountToRepay");
    monthlyInstallmentOutput = $("#monthlyInstallment");
    repaymentDateOutput = $("#repaymentDate");

    creditApplicationData=$('#creditApplicationData');
    creditApplicationAmountToRepayOutput = $("#creditApplication_amountToRepay");
    creditApplicationMonthlyInstallmentOutput = $("#creditApplication_monthlyInstallment");
    creditApplicationRepaymentDateOutput = $("#creditApplication_repaymentDate");
    creditApplicationMonthlyIncome = $("#creditApplication_monthlyIncome");
    creditApplicationMonthlyExpenses = $("#creditApplication_monthlyExpenses");

    creditApplicationData.hide();
    creditCalculateResults.hide();
});

function validateInputFields() {
    errors =[];
    if (creditInterestRate.val() === '')
        errors.push("Credit interest rate should not be empty");
    if (numberOfInstallments.val() === '')
        errors.push("Number of intallments should not be empty");
    if (creditAmount.val() === '')
        errors.push("Credit amount should not be empty");
    if (monthlyIncome.val() === '')
        errors.push("Monthly income should not be empty");
    if (monthlyExpenses.val() === '')
        errors.push("Monthly expenses should not be empty");

    if(errors.length === 0){
        validateIncomeAndExpenses()
    }else{
        alert("Form errors: "+errors)
    }
}
function validateIncomeAndExpenses(){
    if(monthlyExpenses.val()>monthlyIncome.val())
        alert("Your expenses are greater than your income. You cannot apply for a credit");
    else
        calculateCredit()
}
function calculateCredit() {
    let n = parseInt(numberOfInstallments.val());
    let creditInterestRateDecimal=creditInterestRate.val()/100;
    let y= 1+(creditInterestRateDecimal/12);
    let installment=(creditAmount.val()*Math.pow(y,n)*((y-1)/(Math.pow(y,n)-1))).toFixed(2);
    let amountToRepay=(installment*n).toFixed(2);

    showCreditCalcuationData(amountToRepay,installment,calculateRepaymentDate(n))
}

function calculateRepaymentDate(n){
    let repaymentDate =  new Date();
    repaymentDate.setMonth(repaymentDate.getMonth()+n+1);
    return repaymentDate.getMonth()+"/"+repaymentDate.getFullYear();
}

function showCreditCalcuationData(amountToRepay,installment,repaymentDate) {
    amountToRepayOutput.val(amountToRepay);
    monthlyInstallmentOutput.val(installment);
    repaymentDateOutput.val(repaymentDate);
    creditCalculateResults.show()
}

function applyForCredit() {
    creditApplicationAmountToRepayOutput.val(amountToRepayOutput.val());
    creditApplicationMonthlyInstallmentOutput.val(monthlyInstallmentOutput.val());
    creditApplicationRepaymentDateOutput.val(repaymentDateOutput.val());
    creditApplicationMonthlyExpenses.val(monthlyExpenses.val());
    creditApplicationMonthlyIncome.val(monthlyIncome.val());
    creditApplicationRepaymentDateOutput.val(repaymentDateOutput.val());
    creditApplicationData.show();
}

function submitCreditApplication() {
}

