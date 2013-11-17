package POSTSystem;

import java.util.*;
import java.io.*;

public class ProductCatalog {
    public List<ProductSpecification> catalog;
    
    public ProductCatalog() {
        catalog = new ArrayList<>();
    }
    
    public void addItemsFromFile(String file) {
        String line, itemDesc;
        String[] lineItems;
        int itemID;
        Money itemPrice;
        
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            while((line = br.readLine()) != null) {
                lineItems = line.split(",");
                itemID = Integer.parseInt(lineItems[0]);
                itemDesc = lineItems[1];
                itemPrice = new Money(lineItems[2]); 
                catalog.add(new ProductSpecification(itemID, itemDesc, itemPrice));
            }
        }
        catch(Exception ex) {
            System.out.println("An error occured populating the product catalog");
            ex.printStackTrace(System.out);
        }
    }
    
    public ProductSpecification getProductSpec(int itemID) {
        for(ProductSpecification spec : catalog) {
            if(spec.getID() == itemID) {
                return spec;
            }
        }
        throw new IllegalArgumentException(String.format("Item with id %d not found in catalog", itemID));
    }
    
    public void print() {
        for(ProductSpecification spec : catalog) {
            System.out.printf("ID: %d Description: %s Price: %s\n", spec.getID(), spec.getDescription(), spec.getPrice().stringValue());
        }
    }
}
