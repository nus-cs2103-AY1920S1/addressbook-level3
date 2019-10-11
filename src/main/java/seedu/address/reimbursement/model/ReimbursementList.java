package seedu.address.reimbursement.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.comparators.SortByAmount;
import seedu.address.reimbursement.model.comparators.SortByDeadline;
import seedu.address.reimbursement.model.comparators.SortByName;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.util.TransactionList;

/**
 * Reimbursement List class: Stores a list of reimbursements, allows accessing of their details and provides
 * functionality to sort them.
 */
public class ReimbursementList {
    private static String invalidIndex = "Index is out of bound. Please key in a valid index.";
    private ArrayList<Reimbursement> list;

    /**
     * Creates a new ReimbursementList with a blank ArrayList of Reimbursements.
     */
    public ReimbursementList() {
        list = new ArrayList<Reimbursement>();
    }

    /**
     * Creates a new ReimbursementList using an existing TransactionList.
     * @param transList the existing transactionList to use.
     */
    public ReimbursementList(TransactionList transList) {
        list = new ArrayList<>();
        ArrayList<Transaction> pendingList = checkStatus(transList);
        for (Transaction trans : pendingList) {
            Reimbursement newRecord = new Reimbursement(trans);
            merge(newRecord);
        }
    }

    /**
     * Creates a new ReimbursementList with an existing ArrayList of Reimbursements.
     * @param reimbursementList the existing ArrayList of Reimbursements.
     */
    public ReimbursementList(ArrayList<Reimbursement> reimbursementList) {
        list = reimbursementList;
    }

    /**
     * Retrieves all the transactions whose status is pending reimbursement.
     * @param transList the list of all transactions
     * @return the list of transactions whose status is pending reimbursements
     */
    private ArrayList<Transaction> checkStatus(TransactionList transList) {
        ArrayList<Transaction> pendingList = new ArrayList<>(); //throws null pointer here
        for (int i = 0; i < transList.size(); i++) {
            //try {
            Transaction trans = transList.get(i);
            if (trans.getStatus() == false) {
                pendingList.add(trans);
            }
            //} catch (NoSuchIndexException e) {
            //break;
            //}
        }
        return pendingList;
    }

    public ArrayList<Reimbursement> getList() {
        return list;
    }

    /**
     * Merges a new reimbursement record with an existing one if they are the same person.
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

    public Reimbursement get(int index) throws NoSuchIndexException {
        if (index >= list.size()) {
            throw new NoSuchIndexException(invalidIndex);
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

    /**
     * Finds a reimbursement by person.
     * @param person The person to find the reimbursements for.
     * @return The reimbursement for that person.
     * @throws NoSuchPersonReimbursementException If no such reimbursement for that person is found.
     */
    public Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException {
        for (Reimbursement reim : list) {
            if (person.isSamePerson(reim.getPerson())) {
                return reim;
            }
        }
        throw new NoSuchPersonReimbursementException();
    }

    /**
     * @return the size of the ReimbursementList.
     */
    public int size() {
        return list.size();
    }

    /**
     * Adds a deadline to pay a person.
     * @param person the person to pay to.
     * @param date the date to pay by.
     * @throws Exception If that person has no reimbursements.
     */
    public void addDeadline(Person person, String date) throws Exception {
        Reimbursement rmb = findReimbursement(person);
        rmb.addDeadline(date);
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





