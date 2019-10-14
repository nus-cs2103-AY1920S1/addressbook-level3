package seedu.address.reimbursement.model;

import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.util.TransactionList;

/**
 * ModelManager. Manages the models for Reimbursements.
 */
public class ModelManager implements Model {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private ReimbursementList reimbursementList;
    //for partial reimbursement list display
    private ReimbursementList filteredList;

    public ModelManager(ReimbursementList newReimbursementList) {
        this.reimbursementList = newReimbursementList;
        this.filteredList = reimbursementList;
    }

    @Override
    public ReimbursementList getReimbursementList() {
        return reimbursementList;
    }

    @Override
    public ReimbursementList getFilteredReimbursementList() {
        return filteredList;
    }

    @Override
    public void listReimbursement() {
        filteredList = reimbursementList;
    }

    @Override
    public void updateReimbursementList(TransactionList transList) {
        reimbursementList = new ReimbursementList(transList);
        filteredList = reimbursementList;
    }

    @Override
    public Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException {
        Reimbursement reim = reimbursementList.findReimbursement(person);
        ArrayList<Reimbursement> newList = new ArrayList<>();
        newList.add(reim);
        filteredList = new ReimbursementList(newList);
        return reim;
    }

    @Override
    public void sortListByName() {
        filteredList = reimbursementList;
        filteredList.sortByName();
    }

    @Override
    public void sortListByAmount() {
        filteredList = reimbursementList;
        filteredList.sortByAmount();
    }

    @Override
    public void sortListByDeadline() {
        filteredList = reimbursementList;
        filteredList.sortByDeadline();
    }

    @Override
    public Reimbursement addDeadline(Person person, String date) throws Exception {
        if(date.length() != 8) {
            throw new InvalidDeadlineException();
        }else {
            reimbursementList.addDeadline(person, date);
        }
        filteredList = reimbursementList;
        return findReimbursement(person);
    }

    @Override
    public Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = reimbursementList.doneReimbursement(person);
        filteredList = reimbursementList;
        return rmb;
    }


}
