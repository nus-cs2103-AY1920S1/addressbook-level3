package seedu.address.reimbursement.model.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.transaction.model.transaction.Transaction;

/**
 * Stores the description of a Reimbursement.
 */
public class Description {
    private String description;

    /**
     * Constructs Description with an empty string.
     */
    public Description() {
        description = "";
    }

    /**
     * Constructs description object with formatted aggregated descriptions of all transactions.
     *
     * @param transList Arraylist of the transactions.
     */
    public Description(ArrayList<Transaction> transList) {
        requireNonNull(transList);
        description = "";
        for (int i = 0; i < transList.size(); i++) {
            Transaction trans = transList.get(i);
            String transDes = trans.getDescription();
            if (i != transList.size() - 1) {
                description = description + Integer.toString(i + 1) + ". " + transDes + System.lineSeparator();
            } else {
                description = description + Integer.toString(i + 1) + ". " + transDes;
            }

        }
    }

    public String toString() {
        return description;
    }

}
