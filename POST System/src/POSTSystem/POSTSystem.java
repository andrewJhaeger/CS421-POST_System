package POSTSystem;

/**
 * Main class.
 */
public class POSTSystem {
    /**
     * Entry point for the POST system. Creates a new Store object and
     * initializes the initial display GUI.
     * @param args 
     */
    public static void main(String[] args) {
        Store store = new Store();
        Register register = store.getRegister();
        InitialDisplay frame = new InitialDisplay(register);
    }
}
