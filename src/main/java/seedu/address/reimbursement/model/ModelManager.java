package seedu.address.reimbursement.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;

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
    public void updateReimbursementList(ReimbursementList reimbursementList) {
        this.reimbursementList = reimbursementList;
        filteredList = this.reimbursementList;
    }

    @Override
    public void updateReimbursementList(Person editedPerson, Person personToEdit) {
        reimbursementList.updatePerson(editedPerson, personToEdit);
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
    public Reimbursement addDeadline(Person person, LocalDate deadline) throws NoSuchPersonReimbursementException {
        reimbursementList.addDeadline(person, deadline);
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
