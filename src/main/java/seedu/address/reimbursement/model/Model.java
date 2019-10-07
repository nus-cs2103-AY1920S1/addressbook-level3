package seedu.address.reimbursement.model;

import seedu.address.person.model.person.Person;
import seedu.address.transaction.model.exception.NoSuchPersonException;


public interface Model {

    Reimbursement findReimbursement(Person person) throws NoSuchPersonException;

    void sortReimbursementList(String attribute);

    void addDeadline(int num, String date);

    void updateIndexes();
}
