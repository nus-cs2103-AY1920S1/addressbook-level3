package seedu.address.reimbursement.model.comparators;

import java.util.Comparator;

import seedu.address.reimbursement.model.Reimbursement;

/**
 * Used to sort the Reimbursement list in ascending order of amount.
 */
public class SortByAmount implements Comparator<Reimbursement> {
    /**
     * Compares two Reimbursements to see which has a bigger amount
     * @param a the first Reimbursement to be compared
     * @param b the second Reimbursement to be compared
     * @return 1 if the first is bigger than the second, 0 if they are the same, -1 otherwise.
     */
    public int compare(Reimbursement a, Reimbursement b) {
        if (a.getAmount() < b.getAmount()) {
            return 1;
        } else if (a.getAmount() == b.getAmount()) {
            return 0;
        } else {
            return -1;
        }
    }
}
