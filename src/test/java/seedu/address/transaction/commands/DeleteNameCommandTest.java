package seedu.address.transaction.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_DELETE_BY_PERSON;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;

import org.junit.jupiter.api.Test;

class DeleteNameCommandTest {
    
    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_unFilteredList_successful() {
        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.deleteAllTransactionOfPerson(TypicalPersons.ALICE);
        assertCommandSuccess(deleteNameCommand, model, message,
                expectedModel, personModel);
    }

    @Test
    void execute_filteredList_successful() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());

        DeleteNameCommand deleteNameCommand = new DeleteNameCommand(TypicalPersons.ALICE);
        String message = String.format(String.format(MESSAGE_DELETE_BY_PERSON, TypicalPersons.ALICE));
        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        assertCommandSuccess(deleteNameCommand, model, message, expectedModel, personModel);
    }
}
