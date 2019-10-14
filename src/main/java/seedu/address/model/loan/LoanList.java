package seedu.address.model.loan;

import java.util.ArrayList;
import java.util.Iterator;

// TODO add more methods
// will implement remove, etc when i implement my return command
/**
 * A list of {@code Loan} objects that represents the loans a borrower currently has.
 */
public class LoanList implements Iterable<Loan> {
    private ArrayList<Loan> loanList;

    public LoanList() {
        loanList = new ArrayList<>();
    }

    /**
     * Adds a {@code Loan} object into the list.
     *
     * @param loan {@code Loan} object to be added.
     */
    public void add(Loan loan) {
        loanList.add(loan);
    }

    /**
     * Returns true if the list is empty.
     */
    public boolean isEmpty() {
        return loanList.isEmpty();
    }

    @Override
    public Iterator<Loan> iterator() {
        return loanList.iterator();
    }
}
