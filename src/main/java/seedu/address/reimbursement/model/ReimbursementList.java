package seedu.address.reimbursement.model;

import java.util.ArrayList;

import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.util.TransactionList;

public class ReimbursementList {
    private ArrayList<Reimbursement> list;
    private static String INVALIDINDEX = "Index is out of bound. Please key in a valid index.";

    public ReimbursementList() {
        list = new ArrayList<Reimbursement>();
    }

    public ReimbursementList(TransactionList transList) {
        ArrayList<Transaction> pendingList = checkStatus(transList);
        for (Transaction trans : pendingList) {
            Reimbursement newRecord = new Reimbursement(trans);
            merge(newRecord);
        }
    }

    private ArrayList<Transaction> checkStatus(TransactionList transList) {
        //gets all the transactions whose status is pending reimbursement.
        ArrayList<Transaction> pendingList = new ArrayList<>();
        for (int i = 0; i < transList.size(); i++) {
            try {
                Transaction trans = transList.get(i);
                if (trans.getStatus() == false) {
                    pendingList.add(trans);
                }
            } catch (NoSuchIndexException e) {
                break;
            }
        }
        return pendingList;
    }

    private void merge(Reimbursement newRecord) {
        boolean canMerge = false;
        for (Reimbursement record : list) {
            if (newRecord.comparePerson(record)) {
                record.merge(newRecord);
                canMerge = true;
                break;
            }
        }
        if (canMerge == false) {
            list.add(newRecord);
        }
    }

    public void addDeadline(int number, String date) throws NoSuchIndexException, InvalidDeadlineException {
        int index = number - 1;
        if(index >= list.size()){
            throw new NoSuchIndexException(INVALIDINDEX);
        } else {
            Reimbursement reimbursement = list.get(index);
            reimbursement.addDeadline(date);
        }

    }

    public String toString() {
        String output = "";
        for(int i = 0; i < list.size(); i++) {
            Reimbursement reimbursement = list.get(i);
            output = output + reimbursement.toString();
        }
        return output;
    }
}
