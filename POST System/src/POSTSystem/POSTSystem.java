package POSTSystem;

public class POSTSystem {
    public static void main(String[] args) {
        ProductCatalog catalog = new ProductCatalog();
        catalog.addItemsFromFile("items.txt");
        catalog.print();
    }
}
