package POSTSystem;

import java.util.*;
import java.text.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Register {
    private Sale sale;
    private ProductCatalog catalog;
    private List<String> receipt;
    private String receiptItemFormat;
    private String receiptBottomFormat;
    
    public Register(ProductCatalog inCatalog) {
        receiptItemFormat = "%-10s %-15s %-10s %-10s";
        receiptBottomFormat = "%45s";
        catalog = inCatalog;
    }
    
    public void makeNewSale() {
        sale = new Sale();
        receipt = new ArrayList<>();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");

        receipt.add(String.format("%-28s %s\n", "Receipt No. " + sale.getReceiptNumber(), dateFormat.format(getDate().getTime())));
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
        receipt.add("---------------------------------------------");
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
                writer.write(System.getProperty("line.separator"));
            }
        }
        catch(IOException ex) {
            System.out.println("An error occured recording the sale");
            ex.printStackTrace(System.out);
        }
    }
    
    public boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
        
        Date testDate;
        try {
            testDate = sdf.parse(date);
        }
        catch (ParseException e) {
            return false;
        }
        
        if (!sdf.format(testDate).equals(date))
        {
            return false;
        }
        return true;
    }
    
    public void printSalesReport(String date) throws IOException {
        makeDailyReport(date);
        File dir = new File ("receipts/" + date + "/");
        if (dir.isDirectory()) {            
           for (File receipts : dir.listFiles()) {
               readFromReceipt(receipts);
           }
        }
        endSale();
    }
    
    public void readFromReceipt(File receipt) throws FileNotFoundException, IOException {
        Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(receipt)));
        int itemNum;
        int quantity;
        
        for (int i = 0; i < 4; i++) {
            fileScanner.nextLine();
        }
        
        while (!fileScanner.hasNext("---------------------------------------------")) {
          itemNum = Integer.parseInt(fileScanner.next());
          fileScanner.next();
          while (fileScanner.hasNextInt() != true)
            fileScanner.next();           
          quantity = fileScanner.nextInt();
          enterItem(itemNum, quantity);
          fileScanner.next();
        }
    }
    
    public void makeDailyReport(String date) {
        sale = new Sale();
        receipt = new ArrayList<>();

        receipt.add(String.format("%-28s %s\n", "Daily Report", date));
        receipt.add(String.format(receiptItemFormat, 
                "Item ID", "Description", "Quantity", "Cost"));
        receipt.add(String.format(receiptItemFormat, 
                "-------", "-----------", "--------", "----"));
    }
}
