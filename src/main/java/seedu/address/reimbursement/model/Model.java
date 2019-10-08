package seedu.address.reimbursement.model;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.util.TransactionList;


public interface Model {
    ReimbursementList getReimbursementList();

    Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException;

    void sortReimbursementListByName();

    void sortReimbursementListByAmount();

    void sortReimbursementListByDeadline();

    Reimbursement addDeadline(Person person, String date) throws Exception;

    Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException;

    void writeInReimbursementFile() throws Exception;

    void updateReimbursementList(TransactionList transList);

}
