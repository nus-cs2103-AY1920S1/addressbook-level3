package seedu.address.reimbursement.model;

import java.time.LocalDate;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;

/**
 * Model interfaces. Defines the methods to be supported by the Model manager.
 */
public interface Model {
    //list operation
    ReimbursementList getReimbursementList();

    ReimbursementList getFilteredReimbursementList();

    void listReimbursement();

    void updateReimbursementList(ReimbursementList reimbursementList);

    void updateReimbursementList(Person editedPerson, Person personToEdit);

    //single reimbursement operation
    Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException;

    Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException;

    Reimbursement addDeadline(Person person, LocalDate deadline) throws NoSuchPersonReimbursementException;

    //sort operation
    void sortListByName();

    void sortListByAmount();

    void sortListByDeadline();

}
