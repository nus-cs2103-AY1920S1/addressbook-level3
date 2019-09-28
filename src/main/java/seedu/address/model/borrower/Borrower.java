package seedu.address.model.borrower;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
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
