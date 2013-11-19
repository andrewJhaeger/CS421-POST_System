package POSTSystem;

import java.util.*;
import java.text.*;
import java.io.*;

public class Register {
    private Sale sale;
    private ProductCatalog catalog;
    private List<String> receipt;
    private String receiptItemFormat;
    private String receiptBottomFormat;
    
    public Register(ProductCatalog inCatalog) {
        receiptItemFormat = "%-10s %-15s %-10s %-10s\n";
        receiptBottomFormat = "%45s\n";
        catalog = inCatalog;
    }
    
    public void makeNewSale() {
        sale = new Sale();
        catalog = new ProductCatalog();
        catalog.addItemsFromFile("items.txt");
        receipt = new ArrayList<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");

        receipt.add(String.format("%-28s %s\n\n", "Receipt No. " + sale.getReceiptNumber(), dateFormat.format(getDate().getTime())));
        receipt.add(String.format(receiptItemFormat, 
                "Item ID", "Description", "Quantity", "Cost"));
        receipt.add(String.format(receiptItemFormat, 
                "-------", "-----------", "--------", "----"));
    }
    
    public Calendar getDate() {
        return sale.getDate();
    }
    
    public void enterItem(int itemID, int quantity) {
        ProductSpecification spec = catalog.getProductSpec(itemID);        
        List<SalesLineItem> lineItems = sale.getLineItems();
        
        int itemIndex = -1;
        int receiptIndex = -1;
        boolean itemFound = false;
        
        for(SalesLineItem item : lineItems) {
            if(item.getSpec().getID() == itemID) {
                itemFound = true;
                quantity += item.getQuantity();
                itemIndex = lineItems.indexOf(item);
                for(String line : receipt) {
                    if(line.startsWith(Integer.toString(itemID))) {
                        receiptIndex = receipt.indexOf(line);
                    }
                }
            }
        }
        
        if(itemFound) {
            lineItems.remove(itemIndex);
            receipt.remove(receiptIndex);
        }
        sale.addLineItem(spec, quantity);
        
        SalesLineItem lineItem = lineItems.get(lineItems.size() - 1);
        receipt.add(String.format(receiptItemFormat, 
                            lineItem.getSpec().getID(),
                            lineItem.getSpec().getDescription(),
                            lineItem.getQuantity(),
                            lineItem.getSubTotal().stringValue()));
    }
    
    public void removeItem(int itemID) {
        List<SalesLineItem> lineItems = sale.getLineItems();
        int itemIndex = -1;
        int receiptIndex = -1;
        boolean itemFound = false;
        
        for(SalesLineItem item : lineItems) {
            if(item.getSpec().getID() == itemID) {
                itemFound = true;
                itemIndex = lineItems.indexOf(item);
                for(String line : receipt) {
                    if(line.startsWith(Integer.toString(itemID))) {
                        receiptIndex = receipt.indexOf(line);
                    }
                }
            }
        }
        
        if(!itemFound) {
            throw new IllegalArgumentException();
        }
        
        lineItems.remove(itemIndex);
        receipt.remove(receiptIndex);
    }
    
    public void makePayment(Money tenderedAmt) {
        sale.makePayment(tenderedAmt);
        sale.becomeComplete();
        receipt.add(String.format(receiptBottomFormat, "Tendered Amount: " + tenderedAmt.stringValue()));
        receipt.add(String.format(receiptBottomFormat, "Change: " + sale.getChange().stringValue()));
        receipt.add(String.format(receiptBottomFormat, "Thank You!"));
        writeReceiptToFile();
    }
    
    public void endSale() {
        receipt.add("---------------------------------------------\n");
        receipt.add(String.format(receiptBottomFormat, "Subtotal: " + sale.getTotal().stringValue()));
        Money money = new Money(sale.getTotal());
        money.multiply(0.06);
        receipt.add(String.format(receiptBottomFormat, "Tax (6%): " + money.stringValue()));
        money.add(sale.getTotal());
        receipt.add(String.format(receiptBottomFormat, "Total: " + money.stringValue()));
    }
    
    public List<String> getReceipt() {
        return receipt;
    }
    
    private void writeReceiptToFile() {         
        SimpleDateFormat folderDateFormat = new SimpleDateFormat("MM-dd-yy");
        File dir = new File("receipts/" + folderDateFormat.format(getDate().getTime()));
        
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File receiptFile = new File(dir, sale.getReceiptNumber() + ".txt");
    
        try(FileWriter writer = new FileWriter(receiptFile, true)) {
            for(String line : receipt) {
                writer.write(line);
            }
        }
        catch(IOException ex) {
            System.out.println("An error occured recording the sale");
            ex.printStackTrace(System.out);
        }
    }
}
