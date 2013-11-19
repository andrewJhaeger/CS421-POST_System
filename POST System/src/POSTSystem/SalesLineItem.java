package POSTSystem;

public class SalesLineItem {
    private ProductSpecification spec;
    private int quantity;
    private Money subTotal;
    
    public SalesLineItem(ProductSpecification inSpec, int inQuantity) {
        spec = inSpec;
        quantity = inQuantity;
        subTotal = new Money(spec.getPrice());
        subTotal.multiply((double)quantity);
    }
    
    public void addQuantity(int inQuantity) {
        quantity += inQuantity;
        subTotal = new Money(spec.getPrice());
        subTotal.multiply((double)quantity);
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
