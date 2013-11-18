package POSTSystem;

public class SalesLineItem {
    private ProductSpecification spec;
    private int quantity;
    private Money subTotal;
    
    public SalesLineItem(ProductSpecification inSpec, int inQuantity) {
        spec = inSpec;
        quantity = inQuantity;
        subTotal = new Money(spec.getPrice());
        subTotal.multiply(quantity);
    }
    
    public ProductSpecification getSpec() {
        return spec;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Money getSubTotal() {
        return subTotal;
    }
}
