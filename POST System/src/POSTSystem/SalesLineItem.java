package POSTSystem;

public class SalesLineItem {
    private ProductSpecification spec;
    private int quantity;
    private Money subTotal;
    
    public SalesLineItem(ProductSpecification inSpec, int inQuantity) {
        spec = inSpec;
        quantity = inQuantity;
        subTotal = spec.getPrice();
        System.out.println(spec.getPrice().stringValue());
        System.out.println(subTotal.stringValue());
        subTotal.multiply(quantity);
        System.out.println(subTotal.stringValue());
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
