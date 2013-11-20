package POSTSystem;

import java.util.*;
import java.io.*;

/**
 * This class is used to manage a collection of 
 * product specifications for the POST system
 */
public class ProductCatalog {
    private List<ProductSpecification> catalog;
    
    /**
     * Initializes a list of ProductSpecification objects 
     */
    public ProductCatalog() {
        catalog = new ArrayList<>();
    }
    
    /**
     * Populates the collection according to data in a text file
     * with an item's id, description, and price separated by a comma.
     * @param file 
     */
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
    
    /**
     * Returns a ProductSpecification for a given ID and throws an exception
     * if one is not found.
     * @param itemID
     * @return       The product specification with given ID
     */
    public ProductSpecification getProductSpec(int itemID) {
        for(ProductSpecification spec : catalog) {
            if(spec.getID() == itemID) {
                return spec;
            }
        }
        throw new IllegalArgumentException(String.format("Item with id %d not found in catalog", itemID));
    }
}
