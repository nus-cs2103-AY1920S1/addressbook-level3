package seedu.address.reimbursement;

import java.util.ArrayList;

public class Reimbursement {
    private ReimbursementList pending;
    private ReimbursementList completed;

    /**
     * Constructs a reimbursement object based on transactions details and creates 2 separate lists for pending and
     * completed.
     *
     */
    public Reimbursement(TransactionList transList) {
        parseTrans(transList);
    }

    private void parseTrans(TransactionList transList) {
        ArrayList<ReimbursementRecord> pendingList = new ArrayList<>();
        ArrayList<ReimbursementRecord> completedList = new ArrayList<>();
        for(Transaction item:transList) {
            ReimbursementRecord newRecord = new ReimbursementRecord(item);

        }
        pending = new ReimbursementList(pendingList);
        completed = new ReimbursementList(completedList);
    }

    public ReimbursementList getPending() {
        return pending;
    }

    public ReimbursementList getCompleted() {
        return completed;
    }




}
