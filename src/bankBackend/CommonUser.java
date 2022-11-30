package bankBackend;

import Utils.Result;

public class CommonUser extends User{
    private SavingAccount savingAccount;
    private CheckingAccount checkingAccount;
    private SecurityAccount securityAccount;
    private LoanAccount loanAccount;

    public void setCheckingAccount(CheckingAccount checkingAccount) {
        this.checkingAccount = checkingAccount;
    }

    public CheckingAccount getCheckingAccount() {
        return checkingAccount;
    }

    public SavingAccount getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(SavingAccount savingAccount) {
        this.savingAccount = savingAccount;
    }

    public void setSecurityAccount(SecurityAccount securityAccount) {
        this.securityAccount = securityAccount;
    }

    public SecurityAccount getSecurityAccount() {
        return securityAccount;
    }

    public void setLoanAccount(LoanAccount loanAccount) {
        this.loanAccount = loanAccount;
    }

    public LoanAccount getLoanAccount() {
        return loanAccount;
    }

    public void showBalance(){}

    public Result<Void> saveMoney(double value,CurrencyType currencyType){
        return null;
    }

    public Result<Void> withdraw(double value,CurrencyType currencyType){
        return null;
    }

    public Result<Void> Transfer(double value,CurrencyType currencyType,String username){
        return null;
    }

    public Result<Void> requestLoan(double value,CurrencyType currencyType){
        return null;
    }

    public Result<Void> viewTransaction(){
        return null;
    }

    public Result<Void> buyStock(){
        return null;
    }

    public Result<Void> setStock(){
        return null;
    }

    public Result<Void> viewPosition(){
        return null;
    }

    public Result<Void> viewRealized(){
        return null;
    }

    public Result<Void> viewUnrealized(){
        return null;
    }



}
