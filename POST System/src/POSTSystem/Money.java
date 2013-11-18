package POSTSystem;

import java.math.BigDecimal;
import java.text.*;

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
    
    public void multiply(int multiple) {
        System.out.println(stringValue());
        BigDecimal bdMultiple = new BigDecimal(Integer.toString(multiple));
        value = value.multiply(bdMultiple);
        System.out.println(stringValue());
    }
    
    public void divide(int divisor) {
        BigDecimal bdDivisor = new BigDecimal(Integer.toString(divisor));
        value = value.divide(bdDivisor);
    }
    
    public BigDecimal bigDecValue() {
        return value;
    }
    
    public String stringValue() {
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
