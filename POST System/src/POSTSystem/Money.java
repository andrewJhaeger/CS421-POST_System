package POSTSystem;

import java.math.BigDecimal;
import java.text.*;

public class Money {
    private BigDecimal value;
    
    public Money(String inValue) {
        value = new BigDecimal(inValue);
    }
    
    public void add(Money value2) {
        value = value.add(value2.bigDecValue());
    }
    
    public void subtract(Money value2) {
        value = value.subtract(value2.bigDecValue());
    }
    
    public void multiply(Money value2) {
        value = value.multiply(value2.bigDecValue());
    }
    
    public void divide(Money value2) {
        value = value.divide(value2.bigDecValue());
    }
    
    public BigDecimal bigDecValue() {
        return value;
    }
    
    public String stringValue() {
        return NumberFormat.getCurrencyInstance().format(value);
    }
}
