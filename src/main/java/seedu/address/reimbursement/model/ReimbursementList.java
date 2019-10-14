package seedu.address.reimbursement.model;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.comparators.SortByAmount;
import seedu.address.reimbursement.model.comparators.SortByDeadline;
import seedu.address.reimbursement.model.comparators.SortByName;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

/**
 * Reimbursement List class: Stores a list of reimbursements, allows accessing of their details and provides
 * functionality to sort them.
 */
public class ReimbursementList {
    private ArrayList<Reimbursement> list;

    public ReimbursementList() {
        list = new ArrayList<Reimbursement>();
    }

    public ReimbursementList(TransactionList transList) {
        list = new ArrayList<>();
        ArrayList<Transaction> pendingList = checkStatus(transList);
        for (Transaction trans : pendingList) {
            Reimbursement newRecord = new Reimbursement(trans);
            merge(newRecord);
        }
    }


    public ReimbursementList(ArrayList<Reimbursement> reimbursementList) {
        list = reimbursementList;
    }


    public ArrayList<Reimbursement> getList() {
        return list;
    }

    public Reimbursement get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    /**
     * Retrieves all the transactions whose status is pending reimbursement.
     *
     * @param transList the list of all transactions
     * @return the list of transactions whose status is pending reimbursements
     */
    private ArrayList<Transaction> checkStatus(TransactionList transList) {
        ArrayList<Transaction> pendingList = new ArrayList<>();
        for (int i = 0; i < transList.size(); i++) {
            Transaction trans = transList.get(i);
            if (trans.getStatus() == false) {
                pendingList.add(trans);
            }
        }
        return pendingList;
    }


    /**
     * Merges a new reimbursement record with an existing one if the person is already in the reimbursement list.
     *
     * @param newRecord The merged reimbursement record.
     */
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

    public Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException {
        for (Reimbursement reim : list) {
            if (person.isSamePerson(reim.getPerson())) {
                return reim;
            }
        }
        throw new NoSuchPersonReimbursementException();
    }

    public Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = findReimbursement(person);
        rmb.done();
        list.remove(rmb);
        return rmb;
    }

    public void addDeadline(Person person, String date) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = findReimbursement(person);
        rmb.addDeadline(date);
    }


    public void sortByName() {
        Collections.sort(list, new SortByName());
    }

    public void sortByAmount() {
        Collections.sort(list, new SortByAmount());
    }

    public void sortByDeadline() {
        Collections.sort(list, new SortByDeadline());
    }


    /**
     * @return a string representing the reimbursement.
     */
    public String toString() {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            Reimbursement reimbursement = list.get(i);
            output = output + reimbursement.toString();
        }
        return output;
    }

}





