public class Main {
    public static void main(String[] args) {
        DatabaseConnection.connect();
        new LoanApplication().setVisible(true);
    }
}
