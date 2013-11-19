package POSTSystem;

import java.util.*;
import java.io.*;

public class Sale {
    private int receiptNumber;
    private List<SalesLineItem> lineItems;
    private Calendar calendar;
    private boolean isComplete;
    private Payment payment;
    
    public Sale() {
        lineItems = new ArrayList<>();
        calendar = Calendar.getInstance();
        isComplete = false;
        
        File receiptNums = new File("receiptNumbers.txt");
        String line;
        String[] numbers;
        int lastNumber;
        
        try(BufferedReader br = new BufferedReader(new FileReader(receiptNums))) {
            line = br.readLine();
            numbers = line.split(",");
            lastNumber = Integer.parseInt(numbers[numbers.length - 1]);    
            receiptNumber = lastNumber + 1;
        }
        catch(Exception ex) {
            System.out.println("An error occured while assigning a receipt number.");
            ex.printStackTrace(System.out);
        }       
    }
    
    public void addLineItem(ProductSpecification spec, int quantity) {
        lineItems.add(new SalesLineItem(spec, quantity));
    }
    
    public void becomeComplete() {
        isComplete = true;
        File receiptNums = new File("receiptNumbers.txt");
        
        try(FileWriter writer = new FileWriter(receiptNums, true)) {
            writer.write("," + receiptNumber);
        }
        catch(IOException ex) {
            System.out.println("An error occured recording the sale");
            ex.printStackTrace(System.out);
        }
    }

    public void makePayment(Money tenderedAmt) {
        Money totalWithTax = new Money(getTotal());
        totalWithTax.add(getTax()); 
        
        if(tenderedAmt.bigDecValue().compareTo(totalWithTax.bigDecValue()) == -1) {
            throw new IllegalArgumentException();
        }
        payment = new Payment(tenderedAmt);
    }
    
    public Calendar getDate() {
        return calendar;
    }
    
    public int getReceiptNumber() {
        return receiptNumber;
    }
    
    public Money getTotal() {
        Money total = new Money("0.00");
        for(SalesLineItem item : lineItems) {
            total.add(item.getSubTotal());
        }
        return total;
    }
    
    public Money getTax() {
        Money tax = new Money(getTotal());
        tax.multiply(0.06);
        return tax;
    }
    
    public Money getChange() {
        Money change = new Money(payment.getAmount());
        Money totalWithTax = new Money(getTotal());
        totalWithTax.add(getTax());
        change.subtract(totalWithTax);
        return change;
    }
    
    public List<SalesLineItem> getLineItems() {
        return lineItems;
    }
}
