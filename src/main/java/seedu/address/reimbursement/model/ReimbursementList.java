package seedu.address.reimbursement.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.comparators.SortByAmount;
import seedu.address.reimbursement.model.comparators.SortByDeadline;
import seedu.address.reimbursement.model.comparators.SortByName;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;

/**
 * Reimbursement List class: Stores a list of reimbursements, allows accessing of their details and provides
 * functionality to sort them.
 */
public class ReimbursementList {

    private ArrayList<Reimbursement> list;

    public ReimbursementList() {
        list = new ArrayList<>();
    }

    public ReimbursementList(TransactionList transList) {
        list = new ArrayList<>();
        ArrayList<Transaction> pendingList = checkStatus(transList);
        for (Transaction trans : pendingList) {
            if (trans.getAmount() < 0) {
                Reimbursement newRecord = new Reimbursement(trans);
                merge(newRecord);
            }

        }
    }

    public ReimbursementList(ArrayList<Reimbursement> reimbursementList) {
        assert checkNoSamePerson(reimbursementList) == true : "The reimbursements can't contain the same person.";
        list = reimbursementList;
    }

    /**
     * Checks whether the arraylist of reimbursements contain the same person to fulfill the requirement for using
     * constructor.
     *
     * @param reimbursementArrayList list of reimbursements.
     * @return returns true if the list doesn't contain the same person. Otherwise, false.
     */
    private boolean checkNoSamePerson(ArrayList<Reimbursement> reimbursementArrayList) {
        HashSet<Person> set = new HashSet<>();
        for (Reimbursement rmb : reimbursementArrayList) {
            if (!set.contains(rmb.getPerson())) {
                set.add(rmb.getPerson());
            } else {
                return false;
            }
        }
        return true;
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

    /**
     * Updates all reimbursements of a person to another person.
     *
     * @param editedPerson The person we need to change to.
     * @param personToEdit The person that should be changed.
     */
    public void updatePerson(Person editedPerson, Person personToEdit) {
        for (Reimbursement rmb : list) {
            if (rmb.getPerson().isSamePerson(personToEdit)) {
                rmb.setPerson(editedPerson);
            }
        }
    }

    /**
     * finds a reimbursement in the list for the person
     *
     * @param person
     * @return
     * @throws NoSuchPersonReimbursementException
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
     * Marks a reimbursement as done.
     *
     * @param person the person this reimbursement is owed to.
     * @return the new reimbursement object.
     * @throws NoSuchPersonReimbursementException if no such reimbursement is owed to this person.
     */
    public Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = findReimbursement(person);
        rmb.done();
        list.remove(rmb);
        return rmb;
    }

    /**
     * Adds deadline to the reimbursement with person's name in the reimbursement list.
     *
     * @param person   person object for reimbursement.
     * @param deadline deadline date to be added to reimbursement.
     * @throws NoSuchPersonReimbursementException reimbursement for the person cannot be found in the list.
     */
    public void addDeadline(Person person, LocalDate deadline) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = findReimbursement(person);
        rmb.addDeadline(deadline);
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
            if (i != 0) {
                output = output + System.lineSeparator();
            }
            output = output + reimbursement.toString();

        }
        return output;
    }

}





