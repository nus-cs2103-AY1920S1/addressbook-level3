package seedu.address.reimbursement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.util.TransactionList;

public class ReimbursementList {
    private static String INVALIDINDEX = "Index is out of bound. Please key in a valid index.";
    private ArrayList<Reimbursement> list;

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

    public ReimbursementList(ArrayList<Reimbursement> reimbursementList) {
        list = reimbursementList;
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

    public Reimbursement get(int index) throws NoSuchIndexException {
        if (index >= list.size()) {
            throw new NoSuchIndexException(INVALIDINDEX);
        }
        return list.get(index);
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

    public Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException {
        for (Reimbursement reim : list) {
            if (person.isSamePerson(reim.getPerson())) {
                return reim;
            }
        }
        throw new NoSuchPersonReimbursementException();
    }

    public int size() {
        return list.size();
    }

    public void addDeadline(Person person, String date) throws Exception {
        Reimbursement rmb = findReimbursement(person);
        rmb.addDeadline(date);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < list.size(); i++) {
            Reimbursement reimbursement = list.get(i);
            output = output + reimbursement.toString();
        }
        return output;
    }
}

class SortByName implements Comparator<Reimbursement> {
    // Used for sorting in ascending order
    public int compare(Reimbursement a, Reimbursement b) {
        return a.getPerson().getName().compareTo(b.getPerson().getName());
    }
}

class SortByAmount implements Comparator<Reimbursement> {
    // Used for sorting in ascending order
    public int compare(Reimbursement a, Reimbursement b) {
        if (a.getAmount() > b.getAmount()) {
            return 1;
        } else if (a.getAmount() == b.getAmount()) {
            return 0;
        } else {
            return -1;
        }
    }
}

class SortByDeadline implements Comparator<Reimbursement> {
    // Used for sorting in ascending order
    public int compare(Reimbursement a, Reimbursement b) {
        return a.getDeadline().compareTo(b.getDeadline());
    }
}