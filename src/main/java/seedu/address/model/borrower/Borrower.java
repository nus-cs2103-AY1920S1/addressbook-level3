package seedu.address.model.borrower;

import java.util.Objects;

import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanList;

/**
 * Represents a Borrower.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Borrower {

    private final Name name;
    private final Phone phone;
    private final Email email;
    private final BorrowerId borrowerId;
    private final LoanList currentLoanList;
    private final LoanList returnedLoanList;

    public Borrower(Name name, Phone phone, Email email) {
        this(name, phone, email, BorrowerIdGenerator.generateBorrowerId());
    }

    /**
     * only for test
     * @param name name of borrower
     * @param phone phone of borrower
     * @param email email of borrower
     * @param borrowerId is manually input for testing purpose.
     */
    public Borrower(Name name, Phone phone, Email email, BorrowerId borrowerId) {
        this(name, phone, email, borrowerId, new LoanList(), new LoanList());
    }

    public Borrower(Name name, Phone phone, Email email, BorrowerId borrowerId,
                    LoanList currentLoanList, LoanList returnedLoanList) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowerId = borrowerId;
        this.currentLoanList = currentLoanList;
        this.returnedLoanList = returnedLoanList;
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

    public LoanList getCurrentLoanList() {
        return currentLoanList;
    }

    public LoanList getReturnedLoanList() {
        return returnedLoanList;
    }

    /**
     * Adds a new Loan object to a new copy of the currentLoanList.
     *
     * @param newLoan New {@code Loan} object to be added to currentLoanList.
     * @return A new copy of currentLoanList with new Loan object added in to it.
     */
    public LoanList getAddedCurrentLoanList(Loan newLoan) {
        return currentLoanList.addToNewCopy(newLoan);
    }

    /**
     * Removes a Loan object from a new copy of the currentLoanList.
     *
     * @param returnedLoan {@code Loan} object to be removed from the currentLoanList.
     * @return A new copy of the currentLoanList with the returned Loan object removed from it.
     */
    public LoanList getRemovedCurrentLoanList(Loan returnedLoan) {
        return currentLoanList.removeFromNewCopy(returnedLoan);
    }

    /**
     * Replaces a a Loan object in a new copy of the currentLoanList.
     *
     * @param loanToBeRenewed {@code Loan} object to be removed from the currentLoanList.
     * @param renewedLoan A updated {@code Loan} object replacing the removed one.
     * @return A new copy of the currentLoanList with the Loan object replaced.
     */
    public LoanList getReplacedCurrentLoanList(Loan loanToBeRenewed, Loan renewedLoan) {
        return currentLoanList.replaceInNewCopy(loanToBeRenewed, renewedLoan);
    }

    /**
     * Adds a returned Loan object to a new copy of the returnedLoanList.
     *
     * @param returnedLoan {@code Loan} object to be added to returnedLoanList.
     * @return A new copy of currentLoanList with returned Loan object added in to it.
     */
    public LoanList getAddedReturnedLoanList(Loan returnedLoan) {
        return returnedLoanList.addToNewCopy(returnedLoan);
    }

    /**
     * Removes a returned Loan object to a new copy of the returnedLoanList.
     *
     * @param returnedLoan {@code Loan} object to be removed to returnedLoanList.
     * @return A new copy of currentLoanList with returned Loan object removed it.
     */
    public LoanList getRemovedReturnedLoanList(Loan returnedLoan) {
        return returnedLoanList.removeFromNewCopy(returnedLoan);
    }

    /**
     * Returns true if Borrower currently loans a Book represented by the given Loan object.
     */
    public boolean hasCurrentLoan(Loan loan) {
        return currentLoanList.contains(loan);
    }

    /**
     * Gets the total outstanding fine amount from Borrower's returnedLoanList.
     *
     * @return Total remaining fine amount in cents/
     */
    public int getOutstandingFineAmount() {
        return returnedLoanList.calculateOutstandingFineAmount();
    }

    /**
     * Returns true if Borrower has unpaid fines.
     */
    public boolean hasOutstandingFine() {
        return getOutstandingFineAmount() > 0;
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
                && otherBorrower.getBorrowerId().equals(getBorrowerId());
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
        return Objects.hash(name, phone, email, borrowerId, currentLoanList, returnedLoanList);
    }

    /**
     * Method to return the full string representation of the borrower, if required.
     *
     * @return Full string representation of borrower.
     */
    public String toFullString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(", Phone: ")
                .append(getPhone())
                .append(", Email: ")
                .append(getEmail())
                .append(", Borrower ID: ")
                .append(getBorrowerId());
        return builder.toString();
    }

    /**
     * Returns display string of Borrower.
     *
     * @return display string of borrower.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("[" + getBorrowerId() + "] ")
                .append(getName());
        return builder.toString();
    }

}
