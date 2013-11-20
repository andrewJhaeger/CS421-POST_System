package POSTSystem;

/**
 * Represents a purchase of a specific item in a sale.
 */
public class SalesLineItem {
    private ProductSpecification spec;
    private int quantity;
    private Money subTotal;
    
    /**
     * Accepts a product specification and a quantity for the line item.
     * @param inSpec
     * @param inQuantity 
     */
    public SalesLineItem(ProductSpecification inSpec, int inQuantity) {
        spec = inSpec;
        quantity = inQuantity;
        subTotal = new Money(spec.getPrice());
        subTotal.multiply((double)quantity);
    }
    
    /**
     * Returns the ProductSpecification for this line item.
     * @return the ProductSpecification for this line item
     */
    public ProductSpecification getSpec() {
        return spec;
    }
    
    /**
     * Returns the quantity for the line item.
     * @return The quantity for the line item
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Returns the subtotal for the line item.
     * @return The subtotal for the line item
     */
    public Money getSubTotal() {
        return subTotal;
    }
}
