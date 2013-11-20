package POSTSystem;

/**
 * Represents a store using the POST system. 
 * @author Ryan
 */
public class Store {
    private ProductCatalog catalog = new ProductCatalog();
    private Register register = new Register(catalog);

    /**
     * Initializes the product catalog for the store.
     */
    public Store() {
        catalog.addItemsFromFile("items.txt");
    }
     
    /**
     * Returns the register for the store.
     * @return The register for the store
     */
    public Register getRegister() { 
        return register; 
    }
    
}
