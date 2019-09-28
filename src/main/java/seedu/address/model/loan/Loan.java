package seedu.address.model.loan;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Loan {

    private String id = "13245";

    public Loan (String id) {
        this.id = id;
    }

    public Loan() {

    }

    public String getId() {
        return id;
    }

}
