package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.logic.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NEGATIVE_TRANSACTION_EDITED;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_NO_SUCH_TRANSACTION;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_POSITIVE_TRANSACTION_EDITED;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.person.model.person.Person;
import seedu.address.testutil.EditTransactionDescriptorBuilder;
import seedu.address.testutil.TransactionBuilder;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.transaction.Transaction;

class EditCommandTest {
    private seedu.address.transaction.model.ModelManager model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private CheckAndGetPersonByNameModel personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person validPerson = TypicalPersons.DANIEL;
        Transaction editedTransaction = new TransactionBuilder(validPerson).withDate("19-Apr-2019").withAmount(20)
                .withCategory("logistics").withDescription("workshop material").build();
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction)
                .build();
        EditCommand editCommand = new EditCommand(1, descriptor);

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(model.getFilteredList().get(0), editedTransaction);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        int filterListSize = model.getFilteredList().size();
        Transaction lastTransaction = model.getFilteredList().get(filterListSize - 1);
        Transaction editedTransaction = new TransactionBuilder(lastTransaction.getPerson())
                .withAmount(80.0).withDate("01-Jan-2019")
                .build();

        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder(editedTransaction)
                .build();
        EditCommand editCommand = new EditCommand(filterListSize, descriptor);

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.setTransaction(lastTransaction, editedTransaction);;

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(1, new EditCommand.EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredList().get(0);

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredListNegativeTransaction_success() {
        Model model =
                new seedu.address.transaction.model.ModelManager(
                        new TypicalTransactions().getTransactionListWithReimbursementNeeded());
        EditCommand editCommand = new EditCommand(1, new EditCommand.EditTransactionDescriptor());
        Transaction editedTransaction = model.getFilteredList().get(0);

        String expectedMessage = String.format(MESSAGE_NEGATIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(new TypicalTransactions()
                .getTransactionListWithReimbursementNeeded());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_allFieldsSpecifiedFilteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        Transaction transactionInFilteredList = model.getFilteredList().get(0);
        Transaction editedTransaction = new TransactionBuilder(TypicalPersons.DANIEL)
                .withDate("19-Apr-2019").withAmount(20)
                .withCategory("logistics").withDescription("workshop material").build();
        EditCommand editCommand = new EditCommand(1,
                new EditTransactionDescriptorBuilder(editedTransaction).build());

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.BENSON.getName().toString());
        expectedModel.setTransaction(transactionInFilteredList, editedTransaction);


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_someFieldsSpecifiedFilteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        Transaction transactionInFilteredList = model.getFilteredList().get(0);
        Transaction editedTransaction = new TransactionBuilder(TypicalPersons.BENSON)
                .withDate("01-Jan-2019").build();
        EditCommand editCommand = new EditCommand(1,
                new EditTransactionDescriptorBuilder(editedTransaction).build());

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.BENSON.getName().toString());
        expectedModel.setTransaction(transactionInFilteredList, editedTransaction);


        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_noFieldSpecifiedFilteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        Transaction editedTransaction = model.getFilteredList().get(0);
        EditCommand editCommand = new EditCommand(1,
                new EditTransactionDescriptorBuilder().build());

        String expectedMessage = String.format(MESSAGE_POSITIVE_TRANSACTION_EDITED, editedTransaction);

        seedu.address.transaction.model.ModelManager expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.BENSON.getName().toString());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel, personModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        int outOfBoundIndex = model.getFilteredList().size() + 1;
        EditCommand.EditTransactionDescriptor descriptor = new EditTransactionDescriptorBuilder()
                .withAmount(99.0).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, MESSAGE_NO_SUCH_TRANSACTION, personModel);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showTransactionsOfPerson(model, TypicalPersons.BENSON.getName().toString());
        int outOfBoundIndex = 2;
        // ensures that outOfBoundIndex is still in bounds of transaction list
        assertTrue(outOfBoundIndex < model.getTransactionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditTransactionDescriptorBuilder().withAmount(88.0).build());

        assertCommandFailure(editCommand, model, MESSAGE_NO_SUCH_TRANSACTION, personModel);
    }

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditTransactionDescriptor copyDescriptor = new EditCommand.EditTransactionDescriptor();
        EditCommand.EditTransactionDescriptor descriptor = new EditCommand.EditTransactionDescriptor();
        EditCommand.EditTransactionDescriptor anotherDescriptor = new EditCommand.EditTransactionDescriptor();
        anotherDescriptor.setDescription("dummy description");
        final EditCommand standardCommand = new EditCommand(1, descriptor);
        EditCommand commandWithSameValues = new EditCommand(1, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));


        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new BackCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(2, descriptor)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(1, anotherDescriptor)));
    }
}
