package POSTSystem;

public class SalesLineItem {
    private ProductSpecification spec;
    private int quantity;
    private Money subTotal;
    
    public SalesLineItem(ProductSpecification inSpec, int inQuantity) {
        spec = inSpec;
        quantity = inQuantity;
    }
    
    public Money getSubTotal() {
        return subTotal;
    }
}
