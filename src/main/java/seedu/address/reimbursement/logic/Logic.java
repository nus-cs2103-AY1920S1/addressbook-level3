package seedu.address.reimbursement.logic;

import java.io.IOException;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.logic.commands.CommandResult;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadException;

/**
 * Logic interface. Defines the methods for a logic manager.
 */
public interface Logic {
    //command execution
    CommandResult execute(String commandText) throws Exception;

    //get list
    ReimbursementList getFilteredList();

    //void updateReimbursementFromTransaction() throws IOException;

    void updateReimbursementModelAndStorage(TransactionList transactionList) throws FileReadException, IOException;

    void updateReimbursementFromPerson(Person editedPerson, Person personToEdit) throws IOException;

}
