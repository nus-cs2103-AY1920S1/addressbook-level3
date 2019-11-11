package seedu.address.reimbursement.model.comparators;

import java.util.Comparator;

import seedu.address.reimbursement.model.Reimbursement;

/**
 * Used to sort the Reimbursement list in ascending order of deadline.
 */
public class SortByDeadline implements Comparator<Reimbursement> {
    /**
     * Compares two reimbursements based on deadline.
     *
     * @param a reimbursement
     * @param b another reimbursement a is comapred to
     * @return 1 if a is null or a's deadline date is greater than b's deadline. Otherwise, return -1.
     */
    public int compare(Reimbursement a, Reimbursement b) {
        if (a.getDeadline() == null) {
            return 1;
        }
        if (b.getDeadline() == null) {
            return -1;
        }
        return a.getDeadline().compareTo(b.getDeadline());
    }
}
