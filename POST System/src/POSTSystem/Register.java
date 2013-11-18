package POSTSystem;

import java.util.*;

public class Register {
    private Sale sale;
    private ProductCatalog catalog;
    
    public void makeNewSale() {
        sale = new Sale();
        catalog = new ProductCatalog();
        catalog.addItemsFromFile("items.txt");
    }
    
    public Calendar getDate() {
        return sale.getDate();
    }
    
    public void enterItem(int itemID, int quantity) {
        ProductSpecification spec = catalog.getProductSpec(itemID);
        sale.addLineItem(spec, quantity);
    }
    
    public String getLineItemDisplay() {
        SalesLineItem lineItem = sale.getLastItem();
        return String.format("%-10d %-20s %-10d %-10s\n", 
                            lineItem.getSpec().getID(),
                            lineItem.getSpec().getDescription(),
                            lineItem.getQuantity(),
                            lineItem.getSubTotal().stringValue());
    }
}
