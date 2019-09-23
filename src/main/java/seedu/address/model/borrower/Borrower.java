package seedu.address.model.borrower;

public class Borrower {
    
    private String userName = "Adam Smith";

    public Borrower() {}

    public Borrower(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}

