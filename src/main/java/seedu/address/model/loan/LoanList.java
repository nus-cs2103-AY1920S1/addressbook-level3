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

    private LoanList(ArrayList<Loan> newList) {
        this.loanList = newList;
    }

    public void add(Loan loan) {
        loanList.add(loan);
    }

    /**
     * Adds a {@code Loan} object into the list.
     *
     * @param loan {@code Loan} object to be added.
     * @return A new LoanList with the Loan object added.
     */
    public LoanList addToNewCopy(Loan loan) {
        ArrayList<Loan> newList = new ArrayList<>(this.loanList);
        newList.add(loan);
        return new LoanList(newList);
    }

    /**
     * Returns the number of {@code Loan} objects in the {@code LoanList}.
     */
    public int size() {
        return loanList.size();
    }

    public boolean contains(Loan loan) {
        return loanList.contains(loan);
    }

    /**
     * Removes a {@code Loan} object from the list.
     *
     * @param loan {@code Loan} object to be removed.
     * @return A new LoanList with the Loan object removed.
     */
    public LoanList removeFromNewCopy(Loan loan) {
        ArrayList<Loan> newList = new ArrayList<>(this.loanList);
        newList.remove(loan);
        return new LoanList(newList);
    }

    @Override
    public Iterator<Loan> iterator() {
        return loanList.iterator();
    }
}
