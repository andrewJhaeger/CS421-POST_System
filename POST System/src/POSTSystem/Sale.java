package POSTSystem;

import java.util.*;
import java.io.*;

/**
 * Represents an individual sale in the POST system.
 */
public class Sale {

  private int receiptNumber;
  private List<SalesLineItem> lineItems;
  private Calendar calendar;
  private boolean isComplete;
  private Payment payment;
  private boolean isDailyReport;

  /**
   * Initializes a collection of SalesLineItems, along with the date of the
   * sale. Also reads a text file containing the used receipt numbers in order
   * to assign a unique number to the sale.
   */
  public Sale() {
    lineItems = new ArrayList<>();
    calendar = Calendar.getInstance();
    isComplete = false;
    isDailyReport = false;
    File receiptNums = new File("receiptNumbers.txt");
    String line;
    String[] numbers;
    int lastNumber;

    try (BufferedReader br = new BufferedReader(new FileReader(receiptNums))) {
      line = br.readLine();
      numbers = line.split(",");
      lastNumber = Integer.parseInt(numbers[numbers.length - 1]);
      receiptNumber = lastNumber + 1;
    } catch (Exception ex) {
      System.out.println("An error occured while assigning a receipt number.");
      ex.printStackTrace(System.out);
    }
  }

  /**
   * Adds a SalesLineItem to the collection
   *
   * @param spec
   * @param quantity
   */
  public void addLineItem(ProductSpecification spec, int quantity) {
    lineItems.add(new SalesLineItem(spec, quantity));
  }

  /**
   * Completes the record of a sale by writing the completed receipt number to
   * the text file.
   */
  public void becomeComplete() {
    isComplete = true;
    File receiptNums = new File("receiptNumbers.txt");

    try (FileWriter writer = new FileWriter(receiptNums, true)) {
      writer.write("," + receiptNumber);
    } catch (IOException ex) {
      System.out.println("An error occured recording the sale");
      ex.printStackTrace(System.out);
    }
  }

  /**
   * Creates a new Payment for the sale and throws an exception if the tendered
   * amount is not enough to cover the total.
   *
   * @param tenderedAmt
   */
  public void makePayment(Money tenderedAmt) {
    Money totalWithTax = new Money(getTotal());
    totalWithTax.add(getTax());

    if (tenderedAmt.bigDecValue().compareTo(totalWithTax.bigDecValue()) == -1) {
      throw new IllegalArgumentException();
    }
    payment = new Payment(tenderedAmt);
  }

  /**
   * Returns the Calendar object for the sale, containing the date.
   *
   * @return The Calendar object for the sale
   */
  public Calendar getDate() {
    return calendar;
  }

  /**
   * Returns the receipt number of the sale.
   *
   * @return The receipt number of the sale
   */
  public int getReceiptNumber() {
    return receiptNumber;
  }

  public void setReceiptNumber(int receiptNum) {
    receiptNumber = receiptNum;
  }

  /**
   * Returns the total cost of the purchased items in the sale.
   *
   * @return The total cost of the purchased items in the sale.
   */
  public Money getTotal() {
    Money total = new Money("0.00");
    for (SalesLineItem item : lineItems) {
      total.add(item.getSubTotal());
    }
    return total;
  }

  /**
   * Returns the tax for the sale.
   *
   * @return The tax for the sale
   */
  public Money getTax() {
    Money tax = new Money(getTotal());
    tax.multiply(0.06);
    return tax;
  }

  /**
   * Returns the necessary change for the customer.
   *
   * @return The change for the customer.
   */
  public Money getChange() {
    Money change = new Money(payment.getAmount());
    Money totalWithTax = new Money(getTotal());
    totalWithTax.add(getTax());
    change.subtract(totalWithTax);
    return change;
  }

  /**
   * Returns the list of SalesLineItems
   *
   * @return The list of SalesLineItems
   */
  public List<SalesLineItem> getLineItems() {
    return lineItems;
  }
}
