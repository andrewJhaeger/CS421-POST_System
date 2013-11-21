package POSTSystem;

import java.math.BigDecimal;
import java.text.*;

/**
 * Class to represent monetary values. Uses BigDecimal as a base value, the most
 * reliable for money from what I've read.
 */
public class Money {

  private BigDecimal value;

  public Money(String inValue) {
    value = new BigDecimal(inValue);
  }

  public Money(Money inMoney) {
    value = inMoney.bigDecValue();
  }

  public void add(Money value2) {
    value = value.add(value2.bigDecValue());
  }

  public void subtract(Money value2) {
    value = value.subtract(value2.bigDecValue());
  }

  public void multiply(double multiple) {
    BigDecimal bdMultiple = new BigDecimal(Double.toString(multiple));
    value = value.multiply(bdMultiple);
  }

  public BigDecimal bigDecValue() {
    return value;
  }

  public String stringValue() {
    return NumberFormat.getCurrencyInstance().format(value);
  }
}
