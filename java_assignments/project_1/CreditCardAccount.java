/**
 * this class keeps track of one's credit card bill
 */

public class CreditCardAccount extends Object {

  // stores maximum amount that may be borrowed
  private int creditLimit = 0;
  // stores interest rate, a percentage one has to pay if payment late
  private double interestRate = 0.0;
  // minimum payment one must pay to avoid penalty
  private int minMonthlyPayment = 0;
  // penalty charged on a late payment
  private int latePaymentPenalty = 0;
  // stores total balance till day (including last month if not paid yet)
  private double currentBalance = 0;
  // previous month's balance that hasn't been paid yet
  private double payBalance = 0.0;
  // checks to see if total payment was paid
  private boolean paidInFullFlag = true;
  // stores interest earned until now
  private double interestThusFar = 0.0;
  // stores total amount that was paid
  private double totalPaid = 0.0;

  // Generic constructor
  public CreditCardAccount() {
    // All the fields are initialzed during declaration
  }

  /*
   * Initialize credit limit, interest rate, minimum payment and late payment
   * penalty
   */
  public CreditCardAccount(int creditLimit, double interestRate, int minMonthlyPayment, int latePaymentPenalty) {
    this.creditLimit = creditLimit;
    this.interestRate = interestRate;
    this.minMonthlyPayment = minMonthlyPayment;
    this.latePaymentPenalty = latePaymentPenalty;
  }

  // returns credit limit
  public int getCreditLimit() {
    return creditLimit;
  }

  // sets credit limit
  public void setCreditLimit(int creditLimit) {
    this.creditLimit = creditLimit;
  }

  // gets interest rate
  public double getInterestRate() {
    return interestRate;
  }

  // sets interest rate
  public void setInterestRate(double interestRate) {
    this.interestRate = interestRate;
  }

  // gets minimum payment allowed to avoid late fee
  public int getMinMonthlyPayment() {
    return minMonthlyPayment;
  }

  // sets minimum payment allowed to avoid late fee
  public void setMinMonthlyPayment(int minMonthlyPayment) {
    this.minMonthlyPayment = minMonthlyPayment;
  }

  // gets fee for late payment
  public int getLatePaymentPenalty() {
    return latePaymentPenalty;
  }

  // sets fee for late payment
  public void setLatePaymentPenalty(int latePaymentPenalty) {
    this.latePaymentPenalty = latePaymentPenalty;
  }

  // gets current balance
  public double getBalance() {
    return currentBalance;
  }

  // gets previous months balance
  public double getMonthlyPayment() {
    return payBalance;
  }

  /*
   * allows one to make a purchase as long as that and the current balance
   * aren't over the credit limit
   */
  public boolean charge(double charge) {
    if ((currentBalance + charge) <= creditLimit) {
      currentBalance += charge;
      return true;
    } else
      return false;
  }

  // makes a payment and adds it to total paid
  public void payment(double payment) {
    currentBalance -= payment;
    totalPaid += payment;
  }

  /*
   * Calculates daily interest if previous payment wasn't paid. Only does this
   * is full payment wasn't received
   */
  public void incrementDay() {
    if (paidInFullFlag == false) {
      double accountBalance;

      accountBalance = currentBalance + interestThusFar;
      interestThusFar += (accountBalance * (interestRate / 365.0));
    }
  }

  /*
   * Checks to see if payment paid Resets interest if applicable Applies fees if
   * late
   */
  public void incrementMonth() {
    currentBalance += interestThusFar;
    interestThusFar = 0.0;
    if (totalPaid >= payBalance)
      paidInFullFlag = true;
    else
      paidInFullFlag = false;
    if (minMonthlyPayment < payBalance && totalPaid < minMonthlyPayment)
      currentBalance += latePaymentPenalty;
    totalPaid = 0.0;
    payBalance = currentBalance;
  }
}
