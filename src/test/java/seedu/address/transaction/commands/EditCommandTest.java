package seedu.address.transaction.commands;

import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_TRANSACTION_EDITED;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;
import seedu.address.transaction.model.Transaction;

import org.junit.jupiter.api.Test;

class EditCommandTest {
    private ModelManager model = new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_filteredList_allFieldsSpecified_successful() {
        Person validPerson = new PersonBuilder().build();
        Transaction editedTransaction = new TransactionBuilder(validPerson).build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction)
                .build();
        EditCommand editCommand = new EditCommand(1, descriptor);

        String expectedMessage = String.format(MESSAGE_TRANSACTION_EDITED, editedTransaction);

        ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(model.getFilteredList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }
}