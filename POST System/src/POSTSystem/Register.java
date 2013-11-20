package POSTSystem;

import java.util.*;
import java.text.*;
import java.io.*;

/**
 * This class serves as a controller between the GUI and the processing
 * side of the POST system.
 */
public class Register {
    private Sale sale;
    private ProductCatalog catalog;
    private List<String> receipt;
    private String receiptItemFormat;
    private String receiptBottomFormat;
    
    /**
     * Defines format strings for receipt output and accepts a given 
     * product catalog.
     * @param inCatalog 
     */
    public Register(ProductCatalog inCatalog) {
        receiptItemFormat = "%-10s %-15s %-10s %-10s";
        receiptBottomFormat = "%45s";
        catalog = inCatalog;
    }
    
    /**
     * Initializes a new Sale object and receipt for a new sale to be processed. 
     * The receipt is an ArrayList of Strings and is updated in real time 
     * so it can be displayed to the user while being processed.
     */
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
    
     /**
     * Initializes a Sale object to be used as a ledger for the total items
     * and cost of items sold over a certain day.
     * @param date 
     */
    public void makeDailyReport(String date) {
        sale = new Sale();
        receipt = new ArrayList<>();

        receipt.add(String.format("%-28s %s\n", "Daily Report", date));
        receipt.add(String.format(receiptItemFormat, 
                "Item ID", "Description", "Quantity", "Cost"));
        receipt.add(String.format(receiptItemFormat, 
                "-------", "-----------", "--------", "----"));
    }
    
    /**
     * Used to retrieve the date and time of the active sale.
     * @return The date of the active sale.
     */
    public Calendar getDate() {
        return sale.getDate();
    }
    
    /**
     * Used to enter an item into the sale. First retrieves the product
     * specification of the desired item, then adds a line item to the sale.
     * Also checks for already existing items in the sale, and adds to their
     * quantity instead of adding a duplicate line item.
     * @param itemID
     * @param quantity 
     */
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
    
    /**
     * Removes an item from the sale by itemID. Also removes its line from 
     * the receipt.
     * @param itemID 
     */
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
    
    /**
     * Ends the the sale after all items have been entered. Calculates the 
     * subtotal, tax, and total, then adds them to the receipt. 
     */
    public void endSale() {
        receipt.add("---------------------------------------------");
        receipt.add(String.format(receiptBottomFormat, "Subtotal: " + sale.getTotal().stringValue()));
        Money money = new Money(sale.getTotal());
        money.multiply(0.06);
        receipt.add(String.format(receiptBottomFormat, "Tax (6%): " + money.stringValue()));
        money.add(sale.getTotal());
        receipt.add(String.format(receiptBottomFormat, "Total: " + money.stringValue()));
    }
    
    /**
     * Creates a payment from the tendered amount and completes the sale. 
     * Then adds final lines to receipt and writes the receipt to an external file.
     * @param tenderedAmt 
     */
    public void makePayment(Money tenderedAmt) {
        sale.makePayment(tenderedAmt);
        sale.becomeComplete();
        receipt.add(String.format(receiptBottomFormat, "Tendered Amount: " + tenderedAmt.stringValue()));
        receipt.add(String.format(receiptBottomFormat, "Change: " + sale.getChange().stringValue()));
        receipt.add(String.format(receiptBottomFormat, "Thank You!"));
        writeReceiptToFile();
    }
    
    /**
     * Returns the receipt.
     * @return 
     */
    public List<String> getReceipt() {
        return receipt;
    }
    
    /**
     * Writes the receipt to a text file  in a directory according to the date
     * the file was created. The file is named by its receipt number.
     */
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
    
    /**
     * Checks to make sure date is in correct format for daily report
     * 
     * @param date
     * @return boolean
     */
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
    /**
     * Looks for and prints out the daily report for a given day
     * 
     * @param date
     * @throws IOException 
     */
    public void printSalesReport(String date) throws IOException {
        makeDailyReport(date);
        File dir = new File ("receipts/" + date + "/");
        if (dir.isDirectory()) {            
           for (File receiptFile : dir.listFiles()) {
               readItemsFromReceipt(receiptFile);
           }
        }
        endSale();
    }
    
    /**
     * Reads in the data from a receipt to create a new sale item to add all
     * the items into one sale, completing he daily report
     * 
     * @param receipt
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void readItemsFromReceipt(File receiptFile) throws FileNotFoundException, IOException {
        Scanner fileScanner = new Scanner(new BufferedReader(new FileReader(receiptFile))).useDelimiter("\\s{2,}");
        int itemNum;
        int quantity;
        
        for (int i = 0; i < 4; i++) {
            fileScanner.nextLine();
        }
        
        while (!fileScanner.hasNext("---------------------------------------------")) {
          itemNum = Integer.parseInt(fileScanner.next());
          fileScanner.next();           
          quantity = fileScanner.nextInt();
          enterItem(itemNum, quantity);
          fileScanner.next();
        }
    }
}
