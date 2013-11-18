package POSTSystem;

import java.util.*;

public class Sale {
    private List<SalesLineItem> lineItems;
    private Money total;
    private Calendar calendar;
    private boolean isComplete;
    
    public Sale() {
        lineItems = new ArrayList<>();
        calendar = Calendar.getInstance();
        isComplete = false;
    }
    
    public void addLineItem(ProductSpecification spec, int quantity) {
        lineItems.add(new SalesLineItem(spec, quantity));
    }
    
    public void becomeComplete() {
        isComplete = true;
    }

    public void makePayment() {
    }
    
    public Calendar getDate() {
        return calendar;
    }
    
    public Money getTotal() {
        return total;
    }
    
    public SalesLineItem getLastItem() {
        return lineItems.get(lineItems.size() - 1);
    }
}
