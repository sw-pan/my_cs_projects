/**
 * This class stores and increments the date
 */

public class Date extends Object {

  // stores the day of the month
  public int day = 1;
  // stores the month in it's numerical form
  public int month = 1;

  // initializes day and month
  public Date(int day, int month) {
    this.day = day;
    this.month = month;
  }

  // gets day
  public int getDay() {
    return day;
  }

  // gets month
  public int getMonth() {
    return month;
  }

  // appropriately increments by one day
  public void incrementDay() {
    day++;
    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10) {
      if (day > 31) {
        day = 1;
        month++;
      }
    } else if (month == 4 || month == 6 || month == 9 || month == 11) {
      if (day > 30) {
        day = 1;
        month++;
      }
    } else if (month == 12 && day > 31) {
      month = 1;
      day = 1;
    } else if (month == 2) {
      if (day > 28) {
        day = 1;
        month = 3;
      }
    }
  }
}
