package POSTSystem;

public class POSTSystem {
    public static void main(String[] args) {
        Store store = new Store();
        Register register = store.getRegister();
        InitialDisplay frame = new InitialDisplay(register);
    }
}
