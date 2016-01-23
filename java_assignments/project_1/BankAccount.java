/**
 * this class keeps track of how much money is currently in one's bank account
 */

public class BankAccount extends Object {

  // stores balance currently in account
  private double currentBalance = 0.0d;
  // interest rate
  private double interestRate = 0.0d;
  // minimum balance one must have in account
  private int minimumBalance = 0;
  // standard fee charged each time one withdraws from ATM
  private double atmFee = 0.0d;
  /*
   * standard fee imposed when current balance at the end of the day is less
   * than minimum balance
   */
  private double overdraftFee = 0.0d;
  /*
   * standard fee imposed when one attempts to remove more money than in account
   */
  private double bouncedCheckFee = 0.0d;
  /*
   * charged each time one withdraws money after exceeding maximum number of
   * monthly withdraws
   */
  private double withdrawFee = 0.0d;
  // maximum number of free withdraws per month
  private int withdrawLimit = 0;
  // number of times one withdrew money this month
  private int numberOfWithdraws = 0;
  // Interest for this month to be added at the end of month
  private double interestThisMonth = 0.0;
  // Keep track for overdraft fee in a month
  private boolean overdraftFlag = false;

  // Generic constructor
  public BankAccount() {
    // All the fields are initialzed during declaration
  }

  /*
   * Initialize interest rate, min balance, overdraft fee, ATM fee and bounced
   * check fee
   */
  public BankAccount(double interestRate, int minimumBalance, double overdraftFee, double atmFee,
      double bouncedCheckFee) {
    this.interestRate = interestRate;
    this.minimumBalance = minimumBalance;
    this.overdraftFee = overdraftFee;
    this.atmFee = atmFee;
    this.bouncedCheckFee = bouncedCheckFee;
  }

  // returns current balance
  public double getBalance() {
    return currentBalance;
  }

  // returns interest rate
  public double getInterestRate() {
    return interestRate;
  }

  // sets interest rate
  public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
  }

  // returns minimum balance
  public int getMinimumBalance() {
    return minimumBalance;
  }

  // sets minimum balance
  public void setMinimumBalance(int minimumBalance) {
    this.minimumBalance = minimumBalance;
  }

  // returns ATM fee
  public double getATMFee() {
    return atmFee;
  }

  // sets ATM fee
  public void setATMFee(double atmFee) {
    this.atmFee = atmFee;
  }

  // returns overdraft fee
  public double getOverdraftFee() {
    return overdraftFee;
  }

  // sets overdraft fee
  public void setOverdraftFee(double overdraftFee) {
    this.overdraftFee = overdraftFee;
  }

  // returns bounced check fee
  public double getBouncedCheckFee() {
    return bouncedCheckFee;
  }

  // sets bounced check fee
  public void setBouncedCheckFee(double bouncedCheckFee) {
    this.bouncedCheckFee = bouncedCheckFee;
  }

  // returns withdraw fee
  public double getWithdrawFee() {
    return withdrawFee;
  }

  // sets withdraw fee
  public void setWithdrawFee(double withdrawFee) {
    this.withdrawFee = withdrawFee;
  }

  // returns withdraw limit
  public int getWithdrawLimit() {
    return withdrawLimit;
  }

  // sets withdraw limit
  public void setWithdrawLimit(int withdrawLimit) {
    this.withdrawLimit = withdrawLimit;
  }

  // adds deposit to current balance
  public void deposit(double deposit) {
    currentBalance += deposit;
  }

  /*
   * Makes sure amount withdrawn is less than or equal to amount in account,
   * increments number of withdraws, stores new account balance and applies
   * withdraw fee (if needed); if current balance is less than amount withdrawn
   * then returns false
   */
  public boolean withdraw(double amount) {
    if (currentBalance >= amount) {
      currentBalance -= amount;
      numberOfWithdraws++;
      // makes sure one hasn't gone over allowed withdraws
      if (withdrawLimit != 0 && (numberOfWithdraws > withdrawLimit)) {
        currentBalance -= withdrawFee;
      }
      return true;
    } else
      return false;
  }

  /*
   * Makes sure amount withdrawn is less than or equal to amount in account,
   * increments number of withdraws, stores new account balance and applies
   * withdraw fee (if needed); if current balance is less than amount withdrawn
   * then bounced check fee applied
   */
  public boolean withdrawDraft(double withdraw) {
    if (currentBalance >= withdraw) {
      currentBalance -= withdraw;
      numberOfWithdraws++;
      /* makes sure one hasn't gone over number of withdraws allowed */
      if (withdrawLimit != 0 && (numberOfWithdraws > withdrawLimit)) {
        currentBalance -= withdrawFee;
      }
      return true;
    } else {
      // Apply bounced check fee
      currentBalance -= bouncedCheckFee;
      return false;
    }
  }

  // Withdraw using ATM, atmFee will be applied
  public boolean withdrawATM(double amount) {
    // Same processing as withdraw
    return withdraw(amount + atmFee);
  }

  /*
   * Calculates daily interest. Also takes care of overdraft fee per month, so
   * that it's not applied multiple times in a month. No interest if there is
   * any overdraft fee
   */
  public void incrementDay() {
    if (currentBalance < minimumBalance && overdraftFlag == false) {
      currentBalance -= overdraftFee;
      overdraftFlag = true;
      interestThisMonth = 0.0;
    } else if (currentBalance < minimumBalance && overdraftFlag == true) {
      interestThisMonth = 0.0;
    } else {
      double interestToday;

      interestToday = (currentBalance + interestThisMonth) * interestRate / 365.0;
      interestThisMonth += interestToday;
    }
  }

  // Applies monthly interest to current balance and resets overdraft flag
  public void incrementMonth() {
    currentBalance += interestThisMonth;
    interestThisMonth = 0.0;
    overdraftFlag = false;
    numberOfWithdraws = 0;
  }
}
