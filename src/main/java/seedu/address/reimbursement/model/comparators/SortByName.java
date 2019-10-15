package seedu.address.reimbursement.model.comparators;

import java.util.Comparator;

import seedu.address.reimbursement.model.Reimbursement;

/**
 * Used to sort the Reimbursement list in ascending order of name.
 */
public class SortByName implements Comparator<Reimbursement> {
    public int compare(Reimbursement a, Reimbursement b) {
        return a.getPerson().getName().compareTo(b.getPerson().getName());
    }
}
