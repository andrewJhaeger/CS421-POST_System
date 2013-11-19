package POSTSystem;

public class Payment {
    private Money amount;
    
    public Payment(Money tenderedAmt) {
        amount = tenderedAmt;
    }
    
    public Money getAmount() {
        return amount;
    }
}
