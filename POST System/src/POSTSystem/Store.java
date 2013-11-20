package POSTSystem;

public class Store {
    private ProductCatalog catalog = new ProductCatalog();
    private Register register = new Register(catalog);

    public Store() {
        catalog.addItemsFromFile("items.txt");
    }
    
    public Register getRegister() { 
        return register; 
    }
    
}
