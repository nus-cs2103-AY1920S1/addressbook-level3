package seedu.address.reimbursement.model;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.util.TransactionList;

/**
 * Model interfaces. Defines the methods to be supported by the Model manager.
 */
public interface Model {
    ReimbursementList getReimbursementList();

    ReimbursementList getFilteredReimbursementList();

    Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException;

    void sortListByName();

    void sortListByAmount();

    void sortListByDeadline();

    Reimbursement addDeadline(Person person, String date) throws Exception;

    Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException;

    void writeInReimbursementFile() throws Exception;

    void updateReimbursementList(TransactionList transList);

}
