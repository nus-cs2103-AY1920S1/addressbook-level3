package seedu.address.model.borrower;

import java.util.Objects;

import seedu.address.model.BorrowerIdGenerator;

/**
 * Represents a Borrower.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Borrower {

    private Name name;
    private Phone phone;
    private Email email;
    private final BorrowerId borrowerId;

    public Borrower(Name name, Phone phone, Email email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowerId = BorrowerIdGenerator.generateBorrowerId();
    }

    /**
     * only for test
     * @param name name of borrower
     * @param phone phone of borrower
     * @param email email of borrower
     * @param borrowerId is manually input for testing purpose.
     */
    public Borrower(Name name, Phone phone, Email email, BorrowerId borrowerId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowerId = borrowerId;
    }

    public Name getName() {
        return name;
    }

    public BorrowerId getBorrowerId() {
        return borrowerId;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both borrowers have the same borrower_id.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameBorrower(Borrower otherBorrower) {
        if (otherBorrower == this) {
            return true;
        }

        return otherBorrower != null
                && otherBorrower.getBorrowerId().equals(getBorrowerId())
                && otherBorrower.getName().equals(getName())
                && (otherBorrower.getPhone().equals(getPhone())
                    || otherBorrower.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Borrower)) {
            return false;
        }

        Borrower otherBorrower = (Borrower) other;
        return otherBorrower.getName().equals(getName())
                && otherBorrower.getPhone().equals(getPhone())
                && otherBorrower.getEmail().equals(getEmail())
                && otherBorrower.getBorrowerId().equals(getBorrowerId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, borrowerId);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Borrower Id: ")
                .append(getBorrowerId());
        return builder.toString();
    }

}
