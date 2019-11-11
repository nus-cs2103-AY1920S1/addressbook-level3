package seedu.address.model.loan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * A list of {@code Loan} objects that represents the loans a borrower currently has.
 */
public class LoanList implements Iterable<Loan> {
    private final ArrayList<Loan> loanList;

    public LoanList() {
        loanList = new ArrayList<>();
    }

    private LoanList(ArrayList<Loan> newList) {
        this.loanList = newList;
    }

    /**
     * Adds a {@code Loan} object into the list.
     *
     * @param loan {@code Loan} object to be added.
     */
    public void add(Loan loan) {
        loanList.add(loan);
        Collections.sort(loanList);
    }

    /**
     * Adds a {@code Loan} object into a copy of list.
     *
     * @param loan {@code Loan} object to be added.
     * @return A new LoanList with the Loan object added.
     */
    public LoanList addToNewCopy(Loan loan) {
        ArrayList<Loan> newList = new ArrayList<>(this.loanList);
        newList.add(loan);
        Collections.sort(newList);
        return new LoanList(newList);
    }

    /**
     * Returns the number of {@code Loan} objects in the {@code LoanList}.
     */
    public int size() {
        return loanList.size();
    }

    /**
     * Returns true if the {@code LoanList} is empty.
     */
    public boolean isEmpty() {
        return loanList.isEmpty();
    }

    public boolean contains(Loan loan) {
        return loanList.contains(loan);
    }

    /**
     * Removes a {@code Loan} object from a copy of the list.
     *
     * @param loan {@code Loan} object to be removed.
     * @return A new LoanList with the Loan object removed.
     */
    public LoanList removeFromNewCopy(Loan loan) {
        ArrayList<Loan> newList = new ArrayList<>(this.loanList);
        newList.remove(loan);
        Collections.sort(newList);
        return new LoanList(newList);

    }

    /**
     * Replaces a {@code Loan} object in a copy of the list.
     * {@code currentLoanList} should contain {@code loanToBeReplaced}.
     *
     * @param loanToBeReplaced {@code Loan} object to be removed.
     * @param replacingLoan Replacing {@code Loan} object.
     * @return A new LoanList with the Loan object replaced.
     */
    public LoanList replaceInNewCopy(Loan loanToBeReplaced, Loan replacingLoan) {
        assert loanList.contains(loanToBeReplaced) : "loanList does not contain loanToBeReplaced!";

        ArrayList<Loan> newList = new ArrayList<>(this.loanList);
        newList.set(newList.indexOf(loanToBeReplaced), replacingLoan);
        Collections.sort(newList);
        return new LoanList(newList);
    }

    /**
     * Adds up all the remaining fine of the loans.
     *
     * @return Total remaining fine amount in cents.
     */
    public int calculateOutstandingFineAmount() {
        int total = 0;
        for (Loan loan : loanList) {
            total += loan.getRemainingFineAmount();
        }
        return total;
    }

    @Override
    public Iterator<Loan> iterator() {
        return loanList.iterator();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof LoanList)) {
            return false;
        }

        LoanList otherLoanList = (LoanList) o;
        return this.loanList.equals(otherLoanList.loanList);
    }
}
