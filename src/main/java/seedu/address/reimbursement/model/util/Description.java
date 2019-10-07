package seedu.address.reimbursement.model.util;

import java.util.ArrayList;

import seedu.address.transaction.model.Transaction;

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
        description = "";
        for (int i = 0; i < transList.size(); i++) {
            Transaction trans = transList.get(i);
            String transDes = trans.getDescription();
            description = description + Integer.toString(i) + ". " + transDes + System.lineSeparator();
        }
    }

    public String toString() {
        return description;
    }

}
