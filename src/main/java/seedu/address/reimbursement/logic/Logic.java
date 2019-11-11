package seedu.address.reimbursement.logic;

import java.io.IOException;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadException;
import seedu.address.util.CommandResult;

/**
 * Logic interface. Defines the methods for a logic manager.
 */
public interface Logic {
    //command execution
    CommandResult execute(String commandText)
            throws ParseException, NoSuchPersonReimbursementException, InvalidDeadlineException, IOException;

    //get list
    ReimbursementList getFilteredList();

    void updateReimbursementModelAndStorage(TransactionList transactionList) throws FileReadException, IOException;

    void updateReimbursementFromPerson(Person editedPerson, Person personToEdit) throws IOException;

}
