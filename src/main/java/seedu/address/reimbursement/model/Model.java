package seedu.address.reimbursement.model;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.util.TransactionList;

/**
 * Model interfaces. Defines the methods to be supported by the Model manager.
 */
public interface Model {
    //list operation
    ReimbursementList getReimbursementList();

    ReimbursementList getFilteredReimbursementList();

    void listReimbursement();

    void updateReimbursementList(TransactionList transList);

    //single reimbursement operation
    Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException;

    Reimbursement addDeadline(Person person, String date) throws Exception;

    Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException;

    //sort operation
    void sortListByName();

    void sortListByAmount();

    void sortListByDeadline();

}
