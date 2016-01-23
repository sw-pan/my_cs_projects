/**
 * this class consists of bank accounts and credit card accounts
 */

public class Account extends Object {

  // stores first name
  private String firstName = "";
  // stores last name
  private String lastName = "";
  // stores address
  private String streetAddress = "";
  // stores zipcode
  private String zipCode = "";
  // creates savings account of type BankAccount
  private BankAccount savingsAccount = null;
  // creates checking account of type BankAccount
  private BankAccount checkingAccount = null;
  // creates money market account of type BankAccount
  private BankAccount moneyMarketAccount = null;
  // creates credit account of type CreditCardAccount
  private CreditCardAccount creditAccount = null;
  // gets date from Date class
  private Date date = null;

  /*
   * Initialize first name, last name and date
   */
  public Account(String firstName, String lastName, Date date) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.date = date;
  }

  // gets first name
  public String getFirstName() {
    return firstName;
  }

  // sets first name
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  // gets last name
  public String getLastName() {
    return lastName;
  }

  // sets last name
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  // gets street address
  public String getStreetAddress() {
    return streetAddress;
  }

  // sets street address
  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  // get zipcode
  public String getZipCode() {
    return zipCode;
  }

  // set zipcode
  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  // gets savings account
  public BankAccount getSavingsAccount() {
    return savingsAccount;
  }

  // sets up savings account
  public void setSavingsAccount(BankAccount account) {
    savingsAccount = account;
  }

  // gets checking account
  public BankAccount getCheckingAccount() {
    return checkingAccount;
  }

  // sets up checking account
  public void setCheckingAccount(BankAccount account) {
    checkingAccount = account;
  }

  // gets money market account
  public BankAccount getMoneyMarketAccount() {
    return moneyMarketAccount;
  }

  // sets up money market account
  public void setMoneyMarketAccount(BankAccount account) {
    moneyMarketAccount = account;
  }

  // gets credit account
  public CreditCardAccount getCreditCardAccount() {
    return creditAccount;
  }

  // sets up credit account
  public void setCreditCardAccount(CreditCardAccount account) {
    creditAccount = account;
  }

  // gets date
  public Date getDate() {
    return date;
  }

  // sets date
  public void setDate(Date date) {
    this.date = date;
  }

  // calls increment day from other classes; change date, calculate interest,
  // makes sure account exists
  public void incrementDay() {
    int previousmonth = date.getMonth();

    date.incrementDay();
    int month = date.getMonth();

    if (savingsAccount != null) {
      savingsAccount.incrementDay();
      if (previousmonth != month)
        savingsAccount.incrementMonth();
    }

    if (checkingAccount != null) {
      checkingAccount.incrementDay();
      if (previousmonth != month)
        checkingAccount.incrementMonth();
    }

    if (moneyMarketAccount != null) {
      moneyMarketAccount.incrementDay();
      if (previousmonth != month)
        moneyMarketAccount.incrementMonth();
    }

    if (creditAccount != null) {
      creditAccount.incrementDay();
      if (previousmonth != month)
        creditAccount.incrementMonth();
    }
  }
}
