package seedu.address.reimbursement.model.comparators;

import java.util.Comparator;

import seedu.address.reimbursement.model.Reimbursement;

/**
 * Used to sort the Reimbursement list in ascending order of deadline.
 */
public class SortByDeadline implements Comparator<Reimbursement> {
    public int compare(Reimbursement a, Reimbursement b) {
        return a.getDeadline().compareTo(b.getDeadline());
    }
}
