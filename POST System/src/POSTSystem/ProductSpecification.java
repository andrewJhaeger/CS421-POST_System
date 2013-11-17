package POSTSystem;

public class ProductSpecification {
    public int itemID;
    private String description;
    private Money price;
    
    public ProductSpecification(int inId, String inDesc, Money inPrice) {
        itemID = inId;
        description = inDesc;
        price = inPrice;
    }
    
    public int getID() {
        return itemID;
    }
    
    public String getDescription() {
        return description;
    }
    
    public Money getPrice() {
        return price;
    }
}
